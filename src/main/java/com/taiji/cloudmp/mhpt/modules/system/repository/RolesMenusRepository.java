package com.taiji.cloudmp.mhpt.modules.system.repository;

import com.taiji.cloudmp.mhpt.modules.system.domain.Dict;
import com.taiji.cloudmp.mhpt.modules.system.domain.RolesMenus;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.DictQueryCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Repository
public interface RolesMenusRepository {


    List<RolesMenus> findById(Long roleId);

    void deleteById(Long id);

    void save(RolesMenus rolesMenus);

}