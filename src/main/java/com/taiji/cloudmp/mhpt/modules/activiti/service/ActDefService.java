package com.taiji.cloudmp.mhpt.modules.activiti.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ActDefQueryCriteria;

/**
* @author lwy
* @date 2019-10-23
*/
public interface ActDefService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ActDefQueryCriteria criteria, Pageable pageable);

   
}