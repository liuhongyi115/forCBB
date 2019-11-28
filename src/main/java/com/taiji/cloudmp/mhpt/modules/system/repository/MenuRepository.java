package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taiji.cloudmp.mhpt.modules.system.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

import com.taiji.cloudmp.mhpt.modules.system.domain.Menu;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuQueryCriteria;
import org.springframework.stereotype.Repository;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper
@Repository
public interface MenuRepository {

    /**
     * findByName
     * @param name
     * @return
     */
    Menu findByName(String name);
    
    List<Menu> findAll(MenuQueryCriteria criteria);
    

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Menu> findByPid(long pid);
    
    Menu findById(Long id);

    LinkedHashSet<Menu> findByRoles_IdOrderBySortAsc(Long id);
    
    
    Long findRoleCountByMenuId(Long id);
    
    void save(Menu m);
    
    void update(Menu m);
    
    void deleteById(Long id);

    //通过角色查询菜单
    Set<Menu> findMenuByRole(Long id);
}
