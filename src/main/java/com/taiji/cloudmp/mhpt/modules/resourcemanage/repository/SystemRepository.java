package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.System;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.SystemQueryCriteria;;

/**
* @author xiao
* @date 2019-10-23
*/
@Repository
public interface SystemRepository {

	void add(System system);
	
	void delete(String[] id);
	
	void update(System system);
	
	System findById(String id);
	
	List<System> findAll(SystemQueryCriteria criteria);
	
	Long findTotalCount(SystemQueryCriteria criteria);
}