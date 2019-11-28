package com.taiji.cloudmp.mhpt.modules.quartz.repository;

import java.util.List;

import com.taiji.cloudmp.mhpt.modules.quartz.domain.QuartzJob;
import com.taiji.cloudmp.mhpt.modules.quartz.service.dto.QuartzJobQueryCriteria;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
public interface QuartzJobRepository {
	
	void add(QuartzJob quartzJob);
	
	void delete(QuartzJob quartzJob);
	
	void update(QuartzJob quartzJob);
	
	List<QuartzJob> findAll(QuartzJobQueryCriteria criteria);
	
	QuartzJob findById(Long id);
	
	Long findTotalCount(QuartzJobQueryCriteria criteria);
    /**
             * 查询启用的任务
     * @return
     */
    List<QuartzJob> findByIsPauseIsFalse();

}
