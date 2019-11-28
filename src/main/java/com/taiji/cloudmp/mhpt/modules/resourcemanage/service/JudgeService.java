package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Judge;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeQueryCriteria;

/**
* @author xiao
* @date 2019-10-22
*/
public interface JudgeService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(JudgeQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(JudgeQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    JudgeDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    JudgeDTO create(Judge resources);

    /**
     * update
     * @param resources
     */
    void update(Judge resources);

    /**
     * delete
     * @param id的数组
     */
    void delete(String[] id);
    
    boolean isExist(Judge resources);
}