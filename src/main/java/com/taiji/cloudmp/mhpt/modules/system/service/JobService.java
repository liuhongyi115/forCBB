package com.taiji.cloudmp.mhpt.modules.system.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.system.domain.Job;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobQueryCriteria;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
public interface JobService {

    /**
     * findById
     * @param id
     * @return
     */
    JobDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    JobDTO create(Job resources);

    /**
     * update
     * @param resources
     */
    void update(Job resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);
}