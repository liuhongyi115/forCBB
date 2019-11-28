package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.System;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemQueryCriteria;

/**
* @author xiao
* @date 2019-10-23
*/
public interface SystemService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(SystemQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(SystemQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    SystemDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    SystemDTO create(System resources);

    /**
     * update
     * @param resources
     */
    void update(System resources);

    /**
     * delete
     * @param id
     */
    void delete(String[] id);
}