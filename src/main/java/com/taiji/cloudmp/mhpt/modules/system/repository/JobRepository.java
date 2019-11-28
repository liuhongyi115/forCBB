package com.taiji.cloudmp.mhpt.modules.system.repository;

import java.util.List;

import com.taiji.cloudmp.mhpt.modules.system.domain.Job;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.JobQueryCriteria;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
public interface JobRepository {
	
	void add(Job job);
	
	void deleteById(Long id);
	
	void update(Job job);
	
	Job findById(Long id);
	
	List<Job> findByPid(Long id);
	
	List<Job> findAll(JobQueryCriteria criteria);

	Long findTotalCount(JobQueryCriteria criteria);
	
	Long findDeptCountInJob(Long deptId);
}