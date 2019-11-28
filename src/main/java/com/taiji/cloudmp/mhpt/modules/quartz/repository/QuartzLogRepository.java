package com.taiji.cloudmp.mhpt.modules.quartz.repository;

import java.util.List;

import com.taiji.cloudmp.mhpt.modules.quartz.domain.QuartzLog;
import com.taiji.cloudmp.mhpt.modules.quartz.service.dto.QuartzJobQueryCriteria;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
public interface QuartzLogRepository {
	
	void add(QuartzLog log);
	
	List<QuartzLog> findAll(QuartzJobQueryCriteria criteria);

	Long findTotalCount(QuartzJobQueryCriteria criteria);
}
