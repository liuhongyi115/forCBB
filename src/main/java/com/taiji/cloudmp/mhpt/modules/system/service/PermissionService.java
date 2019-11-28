package com.taiji.cloudmp.mhpt.modules.system.service;

import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-08
 */
public interface PermissionService {

    /**
     * get
     * @param id
     * @return
     */
    PermissionDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     * @throws Exception 
     */
    PermissionDTO create(Permission resources) throws Exception;

    /**
     * update
     * @param resources
     */
    void update(Permission resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * permission tree
     * @return
     */
    Object getPermissionTree(List<Permission> permissions);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Permission> findByPid(long pid);

    /**
     * build Tree
     * @param permissionDTOS
     * @return
     */
    Object buildTree(List<PermissionDTO> permissionDTOS);

    /**
     * queryAll
     * @param criteria
     * @return
     */
    List<PermissionDTO> queryAll(PermissionQueryCriteria criteria);

	void cascadeDelete(Long id);
}
