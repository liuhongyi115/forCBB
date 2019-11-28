package com.taiji.cloudmp.mhpt.modules.system.service;

import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleSmallDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface RoleService {

    /**
     * get
     * @param id
     * @return
     */
    RoleDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    RoleDTO create(Role resources);

    /**
     * update
     * @param resources
     */
    void update(Role resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     * findByUsers_Id
     * @param id
     * @return
     */
    List<RoleSmallDTO> findByUsers_Id(Long id);

    Integer findByRoles(Set<Role> roles);

    /**
     * updatePermission
     * @param resources
     * @param roleDTO
     */
    void updatePermission(Role resources, RoleDTO roleDTO);

    /**
     * updateMenu
     * @param resources
     * @param roleDTO
     */
    void updateMenu(Role resources, RoleDTO roleDTO);

    void untiedMenu(Menu menu);

    /**
     * queryAll
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable);

    /**
     * queryAll
     * @param pageable
     * @param criteria
     * @return
     */
    Object queryAll(RoleQueryCriteria criteria, Pageable pageable);
}
