package com.taiji.cloudmp.mhpt.modules.quartz.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.quartz.domain.QuartzJob;
import com.taiji.cloudmp.mhpt.modules.quartz.service.dto.QuartzJobQueryCriteria;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
public interface QuartzJobService {

    /**
     * queryAll quartzJob
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(QuartzJobQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll quartzLog
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAllLog(QuartzJobQueryCriteria criteria, Pageable pageable);

    /**
     * create
     * @param resources
     * @return
     */
    QuartzJob create(QuartzJob resources);

    /**
     * update
     * @param resources
     * @return
     */
    void update(QuartzJob resources);

    /**
     * del
     * @param quartzJob
     */
    void delete(QuartzJob quartzJob);

    /**
     * findById
     * @param id
     * @return
     */
    QuartzJob findById(Long id);

    /**
     * 更改定时任务状态
     * @param quartzJob
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     * @param quartzJob
     */
    void execution(QuartzJob quartzJob);
}
