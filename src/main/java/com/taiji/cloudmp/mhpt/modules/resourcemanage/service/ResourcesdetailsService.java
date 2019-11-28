package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Resourcesdetails;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsQueryCriteria;

/**
* @author xiao
* @date 2019-11-19
*/
public interface ResourcesdetailsService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ResourcesdetailsQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ResourcesdetailsQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    ResourcesdetailsDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    ResourcesdetailsDTO create(Resourcesdetails resources);

    /**
     * update
     * @param resources
     */
    void update(Resourcesdetails resources);

    /**
     * delete
     * @param id
     */
    void delete(String[] id);
}