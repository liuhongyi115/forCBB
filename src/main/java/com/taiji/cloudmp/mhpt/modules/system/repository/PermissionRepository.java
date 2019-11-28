package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionQueryCriteria;
import org.springframework.stereotype.Repository;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Repository
public interface PermissionRepository {

	//通过角色查询权限
	Set<Permission> findPermissionByRole(Long id);

	void add(Permission permission);
	
	void update(Permission permission);
	
	List<Permission> findAll(PermissionQueryCriteria criteria);
	
	Permission findById(Long id);
	
    Permission findByName(@Param("name") String name);

    List<Permission> findByPid(Long pid);
    
    void delete(Permission permission);
    
    void deleteById(Long id);

	Set<Permission> findByRoleId(Long id);
}
