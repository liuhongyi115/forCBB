package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.JudgeFullscore;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreDTO;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreQueryCriteria;

/**
* @author xiao
* @date 2019-11-14
*/
public interface JudgeFullscoreService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(JudgeFullscoreQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(JudgeFullscoreQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    JudgeFullscoreDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    JudgeFullscoreDTO create(JudgeFullscore resources);

    /**
     * update
     * @param resources
     */
    void update(JudgeFullscore resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
}