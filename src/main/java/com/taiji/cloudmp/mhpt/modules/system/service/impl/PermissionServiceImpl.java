package com.taiji.cloudmp.mhpt.modules.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.cloudmp.exception.BadRequestException;
import com.taiji.cloudmp.exception.EntityExistException;
import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import com.taiji.cloudmp.mhpt.modules.system.domain.RolesPermissons;
import com.taiji.cloudmp.mhpt.modules.system.repository.PermissionRepository;
import com.taiji.cloudmp.mhpt.modules.system.repository.RolesPermissionsRepository;
import com.taiji.cloudmp.mhpt.modules.system.service.PermissionService;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.mapper.PermissionMapper;
import com.taiji.cloudmp.utils.ValidationUtil;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolesPermissionsRepository rolesPermissionsRepository;
    

    @Override
    public List<PermissionDTO> queryAll(PermissionQueryCriteria criteria) {
        return permissionMapper.toDto(permissionRepository.findAll(criteria));
    }

    @Override
    public PermissionDTO findById(long id) {
    	Optional<Permission> optionalPermission = Optional.ofNullable(permissionRepository.findById(id));
        ValidationUtil.isNull(optionalPermission,"Permission","id",id);
        Permission permission = optionalPermission.get();
        return permissionMapper.toDto(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionDTO create(Permission resources) throws Exception {
        if(permissionRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }
		resources.setCreateTime(new Date());
        permissionRepository.add(resources);
        return permissionMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Permission resources) {
    	if(resources.getId().equals(resources.getPid())) {
    		throw new BadRequestException("上级不能为自己");
    	}
        Optional<Permission> optionalPermission = Optional.ofNullable(permissionRepository.findById(resources.getId()));
        ValidationUtil.isNull(optionalPermission,"Permission","id",resources.getId());
        Permission permission = optionalPermission.get();

        Permission permission1 = permissionRepository.findByName(resources.getName());

        if(permission1 != null && !permission1.getId().equals(permission.getId())){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }
        //递归查询出自己所有的子孙节点id
        Set<Long> childrenPermissionSet = this.getAllChildrenById(resources.getId());
        
        if(childrenPermissionSet.contains(resources.getPid())){
        	throw new BadRequestException("不可以选择自己及子孙类目做上级!");
        }
        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
        permissionRepository.update(permission);
    }

    //递归查询出自己所有的子孙节点id
    private Set<Long> getAllChildrenById(Long id){
    	Set<Long> idSet = new HashSet<Long>();
    	//将自身传进去 查出来自己的子
    	List<Permission> list = permissionRepository.findByPid(id);
    	for(Permission permission: list){
    		//先加自己
    		idSet.add(permission.getId());
    		//递归找自己的子孙 加进去
    		Set<Long> menuids = getAllChildrenById(permission.getId());
    		idSet.addAll(menuids);
    	}
    	return idSet;
    }

	//递归删除
    @Override
    public void cascadeDelete(Long id) {
    	//查出下一级节点
    	List<Permission> permissionList = permissionRepository.findByPid(id);
    	if(permissionList != null && permissionList.size() > 0) {
    		for (Permission permission : permissionList) {
    			List<RolesPermissons> rolesPermissionslist = rolesPermissionsRepository.findByPermissionId(permission.getId());
    			if(rolesPermissionslist != null && rolesPermissionslist.size() > 0) {
    				throw new BadRequestException("该节点或者下级节点存在关联角色，请先取消关联！");
    			}
    			this.cascadeDelete(permission.getId());
    			permissionRepository.deleteById(id);
    		}
    	}else {
    		//删除自己
    		List<RolesPermissons> rolesPermissionslist = rolesPermissionsRepository.findByPermissionId(id);
			if(rolesPermissionslist != null && rolesPermissionslist.size() > 0) {
				throw new BadRequestException("该节点或者下级节点存在关联角色，请先取消关联！");
			}
    		permissionRepository.deleteById(id);
    	}
	}

	@Override
    public Object getPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
                    if (permission!=null){
                        List<Permission> permissionList = permissionRepository.findByPid(permission.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",permission.getId());
                        map.put("label",permission.getAlias());
                        if(permissionList!=null && permissionList.size()!=0){
                            map.put("children",getPermissionTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Permission> findByPid(long pid) {
        return permissionRepository.findByPid(pid);
    }

    @Override
    public Object buildTree(List<PermissionDTO> permissionDTOS) {

        List<PermissionDTO> trees = new ArrayList<PermissionDTO>();

        for (PermissionDTO permissionDTO : permissionDTOS) {

            if ("0".equals(permissionDTO.getPid().toString())) {
                trees.add(permissionDTO);
            }

            for (PermissionDTO it : permissionDTOS) {
                if (it.getPid().equals(permissionDTO.getId())) {
                    if (permissionDTO.getChildren() == null) {
                        permissionDTO.setChildren(new ArrayList<PermissionDTO>());
                    }
                    permissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = permissionDTOS!=null?permissionDTOS.size():0;

        Map map = new HashMap();
        map.put("content",trees.size() == 0?permissionDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}
}
