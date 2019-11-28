package com.taiji.cloudmp.mhpt.modules.activiti.repository;

import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.activiti.domain.WaitHandler;

/**
* @author lwy
* @date 2019-10-23
*/
@Repository
public interface WaitHandlerRepository {
	
	void save(WaitHandler resources);
	void deleteWaitHandlerByTaskId(String taskId);
}