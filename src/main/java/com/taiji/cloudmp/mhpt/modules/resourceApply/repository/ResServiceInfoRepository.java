package com.taiji.cloudmp.mhpt.modules.resourceApply.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResServiceInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResServiceInfoQueryCriteria;;

/**
* @author lhy
* @date 2019-11-15
*/
@Repository
public interface ResServiceInfoRepository {

	void add(ResServiceInfo ResServiceInfo);
	
	void delete(String id);
	
	void update(ResServiceInfo ResServiceInfo);
	
	ResServiceInfo findById(String id);
	
	List<ResServiceInfo> findAll(ResServiceInfoQueryCriteria criteria);
	
	Long findTotalCount(ResServiceInfoQueryCriteria criteria);
}