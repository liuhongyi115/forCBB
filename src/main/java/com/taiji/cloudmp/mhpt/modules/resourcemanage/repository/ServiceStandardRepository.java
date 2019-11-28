package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.ServiceStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServiceStandardQueryCriteria;;

/**
* @author xiao
* @date 2019-10-22
*/
@Mapper
public interface ServiceStandardRepository {

	void add(ServiceStandard servicestandard);
	
	void delete(String[] id);
	
	void update(ServiceStandard servicestandard);
	
	ServiceStandard findById(String id);
	
	List<ServiceStandard> findAll(ServiceStandardQueryCriteria criteria);
	
	List<ServiceStandard> findContent(ServiceStandardQueryCriteria criteria);
	
	Long findTotalCount(ServiceStandardQueryCriteria criteria);
}