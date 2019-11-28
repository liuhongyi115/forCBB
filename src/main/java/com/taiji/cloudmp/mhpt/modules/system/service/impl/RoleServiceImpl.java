package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.mhpt.modules.system.domain.*;
import com.taiji.cloudmp.mhpt.modules.system.repository.*;
import com.taiji.cloudmp.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.EntityExistException;
import com.taiji.cloudmp.mhpt.modules.system.service.RoleService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DeptDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleSmallDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.RoleMapper;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.RoleSmallMapper;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private RolesDeptsRepository rolesDeptsRepository;

    @Autowired
    private RolesPermissionsRepository rolesPermissionsRepository;

    @Autowired
    private RolesMenusRepository rolesMenusRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleSmallMapper roleSmallMapper;



    @Override
    public Object queryAll(Pageable pageable) {
        List<Role> allOfPage = roleRepository.findAllOfPage(pageable);
        //1.遍历role的id
        for (Role role:allOfPage) {
            Long id = role.getId();
            //2.查询关联中间表的菜单集合，然后给role实体set进去菜单集合
            Set<Menu> menu = menuRepository.findMenuByRole(id);
            role.setMenus(menu);
            //3.查询关联中间表的部门集合，然后给role实体set进去部门集合
            Set<Dept> dept  = deptRepository.findDeptByRole(id);
            role.setDepts(dept);
            //4.同样set进去权限
            Set<Permission> permissions = permissionRepository.findPermissionByRole(id);
            role.setPermissions(permissions);
        }
        Map map = new HashMap();
        map.put("content", allOfPage);
        map.put("totalElements", allOfPage.size());
        return map;
    }


    @Override
    public Object queryAll(RoleQueryCriteria criteria, Pageable pageable) {
        criteria.Pageable2BaseModel(pageable);
        Long roleCount  = roleRepository.getCount(criteria);
        criteria.getPager().setRowCount(roleCount);
        List<Role> list = roleRepository.findAll(criteria);
            //1.遍历role的id
        for (Role role:list) {
            Long id = role.getId();
            //2.查询关联中间表的菜单集合，然后给role实体set进去菜单集合
            Set<Menu> menu = menuRepository.findMenuByRole(id);
            role.setMenus(menu);
            //3.查询关联中间表的部门集合，然后给role实体set进去部门集合
            Set<Dept> dept  = deptRepository.findDeptByRole(id);
            role.setDepts(dept);
            //4.同样set进去权限
            Set<Permission> permissions = permissionRepository.findPermissionByRole(id);
            role.setPermissions(permissions);
        }
        Page<Role> page = new PageImpl<Role>(list,pageable,roleCount);
        return page;
    }

    @Override
    public RoleDTO findById(long id) {
        Role role = roleRepository.findById(id);
        //2.查询关联中间表的菜单集合，然后给role实体set进去菜单集合
        Set<Menu> menu = menuRepository.findMenuByRole(id);
        role.setMenus(menu);
        //3.查询关联中间表的部门集合，然后给role实体set进去部门集合
        Set<Dept> dept  = deptRepository.findDeptByRole(id);
        role.setDepts(dept);
        //4.同样set进去权限
        Set<Permission> permissions = permissionRepository.findPermissionByRole(id);
        role.setPermissions(permissions);
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(Role resources) {
        if(roleRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Role.class,"username",resources.getName());
        }
        resources.setCreateTime(new Date());
        roleRepository.save(resources);
        //添加部门
        Set<Dept> depts = resources.getDepts();
        if (depts.size()!=0){
            for (Dept dept : depts) {
                RolesDepts rolesDepts = new RolesDepts();
                rolesDepts.setRoleId(resources.getId());
                rolesDepts.setDeptId(dept.getId());
                rolesDeptsRepository.save(rolesDepts);
            }
        }
        return roleMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {

        Role role = roleRepository.findById(resources.getId());
        Role role1 = roleRepository.findByName(resources.getName());
        if(role1 != null && !role1.getId().equals(role.getId())){
            throw new EntityExistException(Role.class,"username",resources.getName());
        }
        role.setCreateTime(new Date());
        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        role.setLevel(resources.getLevel());
        roleRepository.update(role);
        //先删除部门
        rolesDeptsRepository.deleteById(role.getId());
        //新增部门
        Set<Dept> depts = resources.getDepts();
        for (Dept dept : depts) {
            RolesDepts rolesDepts = new RolesDepts();
            rolesDepts.setRoleId(role.getId());
            rolesDepts.setDeptId(dept.getId());
            rolesDeptsRepository.save(rolesDepts);
        }

    }

    @Override
    public void updatePermission(Role resources, RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        Long roleId = role.getId();
        //先删后增
        rolesPermissionsRepository.deleteById(roleId);
        Set<Permission> permissions = resources.getPermissions();
        for (Permission permission : permissions) {
            RolesPermissons rolesPermissons = new RolesPermissons();
            rolesPermissons.setPermissionId(permission.getId());
            rolesPermissons.setRoleId(role.getId());
            rolesPermissionsRepository.save(rolesPermissons);
        }

    }

    @Override
    public void updateMenu(Role resources, RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        Long roleId = role.getId();
        // List<RolesMenus> rolesMenusList = rolesMenusRepository.findById(roleId);
        //先删后增
        rolesMenusRepository.deleteById(roleId);
        Set<Menu> menus = resources.getMenus();
        for (Menu menuDTO : menus) {
            RolesMenus rolesMenus = new RolesMenus();
            rolesMenus.setMenuId(menuDTO.getId());
            rolesMenus.setRoleId(role.getId());
            rolesMenusRepository.save(rolesMenus);
        }
    }

    @Override
    public void untiedMenu(Menu menu) {
        Set<Role> roles = roleRepository.findByMenusId(menu.getId());
        for (Role role : roles) {
            //menu.getRoles().remove(role);
            role.getMenus().remove(menu);
            roleRepository.save(role);
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleId) {
        //删除前先校验是否有绑定用户
        Long count  = roleRepository.findUserByRoleId(roleId);
        if (count>0){
            throw new BadRequestException("该角色存在绑定用户, 不可以删除该角色");
        }
        //删除角色之前先删除关联表
        rolesMenusRepository.deleteById(roleId);
        rolesPermissionsRepository.deleteById(roleId);
        rolesDeptsRepository.deleteById(roleId);
        //再删主表
        roleRepository.deleteById(roleId);
    }

    @Override
    public List<RoleSmallDTO> findByUsers_Id(Long id) {
        return roleSmallMapper.toDto(roleRepository.findByUsersId(id).stream().collect(Collectors.toList()));
    }

    @Override
    public Integer findByRoles(Set<Role> roles) {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role role : roles) {
            roleDTOS.add(findById(role.getId()));
        }
        return Collections.min(roleDTOS.stream().map(RoleDTO::getLevel).collect(Collectors.toList()));
    }
}
