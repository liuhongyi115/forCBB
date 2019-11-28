package com.taiji.cloudmp.mhpt.modules.resourceApply.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResApplyBaseInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoQueryCriteria;;

/**
* @author lhy
* @date 2019-11-13
*/
@Repository
public interface ResApplyBaseInfoRepository {

	void add(ResApplyBaseInfo ResApplyBaseInfo);
	
	void delete(String id);
	
	void update(ResApplyBaseInfo ResApplyBaseInfo);
	
	ResApplyBaseInfo findById(String id);
	
	List<ResApplyBaseInfo> findAll(ResApplyBaseInfoQueryCriteria criteria);
	
	Long findTotalCount(ResApplyBaseInfoQueryCriteria criteria);
	
	//////////////////// 
	List<Map<String,Object>> findWithTrans(ResApplyBaseInfoQueryCriteria criteria);
	
	Long findWithTransForCount(ResApplyBaseInfoQueryCriteria criteria);
	
}