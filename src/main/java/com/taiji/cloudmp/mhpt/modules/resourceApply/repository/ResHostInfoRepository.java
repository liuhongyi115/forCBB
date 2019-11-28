package com.taiji.cloudmp.mhpt.modules.resourceApply.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResHostInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResHostInfoQueryCriteria;;

/**
* @author lhy
* @date 2019-11-14
*/
@Repository
public interface ResHostInfoRepository {

	void add(ResHostInfo ResHostInfo);
	
	void delete(String id);
	
	void update(ResHostInfo ResHostInfo);
	
	ResHostInfo findById(String id);
	
	List<ResHostInfo> findAll(ResHostInfoQueryCriteria criteria);
	
	Long findTotalCount(ResHostInfoQueryCriteria criteria);
}