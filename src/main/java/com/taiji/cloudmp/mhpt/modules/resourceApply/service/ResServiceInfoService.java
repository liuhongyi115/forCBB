package com.taiji.cloudmp.mhpt.modules.resourceApply.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResServiceInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoQueryCriteria;

/**
* @author lhy
* @date 2019-11-15
*/
public interface ResServiceInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ResServiceInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ResServiceInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    ResServiceInfoDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    ResServiceInfoDTO create(ResServiceInfo resources);

    /**
     * update
     * @param resources
     */
    void update(ResServiceInfo resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
}