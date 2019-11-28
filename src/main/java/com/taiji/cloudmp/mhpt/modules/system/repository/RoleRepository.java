package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.net.URL;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.system.domain.Role;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.RoleQueryCriteria;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Repository
public interface RoleRepository  {

    /**
     * findByName
     * @param name
     * @return
     */
    Role findByName(String name);

    Set<Role> findByUsersId(Long id);

    Set<Role> findByMenusId(Long id);

    List<Role> findAll(RoleQueryCriteria criteria);

    Role findById(Long id);

    void save(Role role);

    void deleteById(Long id);


    void update(Role role);

    List<Role> findAllOfPage(Pageable pageable);

    //删除前先校验是否有绑定用户
    Long findUserByRoleId(Long roleId);

    //获取总条数
    Long getCount(RoleQueryCriteria criteria);
}
