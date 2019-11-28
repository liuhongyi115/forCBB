package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.system.domain.RolesPermissons;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Repository
public interface RolesPermissionsRepository {

    List<RolesPermissons> findByRoleId(Long roleId);

    List<RolesPermissons> findByPermissionId(Long permissionId);
    
    void deleteById(Long roleId);

    void save(RolesPermissons rolesPermissons);

}