package com.taiji.cloudmp.mhpt.modules.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.repository.PermissionRepository;
import com.taiji.cloudmp.mhpt.modules.system.repository.RoleRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.UserDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.PermissionMapper;

@Service
@CacheConfig(cacheNames = "role")
public class JwtPermissionService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     * @param user
     * @return
     */
    @Cacheable(key = "'loadPermissionByUser:' + #p0.username")
    public Collection<GrantedAuthority> mapToGrantedAuthorities(UserDTO user) {

        System.out.println("--------------------loadPermissionByUser:" + user.getUsername() + "---------------------");

        Set<Role> roles = roleRepository.findByUsersId(user.getId());
        for (Role role : roles) {
        	Set<Permission> persmissions = permissionRepository.findByRoleId(role.getId());
        	role.setPermissions(persmissions);
		}
        return roles.stream().flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }
}
