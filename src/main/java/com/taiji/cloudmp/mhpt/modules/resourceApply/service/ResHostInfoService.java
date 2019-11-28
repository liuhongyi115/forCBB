package com.taiji.cloudmp.mhpt.modules.resourceApply.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResHostInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoQueryCriteria;

/**
* @author lhy
* @date 2019-11-14
*/
public interface ResHostInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ResHostInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ResHostInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    ResHostInfoDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    ResHostInfoDTO create(ResHostInfo resources);

    /**
     * update
     * @param resources
     */
    void update(ResHostInfo resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
}