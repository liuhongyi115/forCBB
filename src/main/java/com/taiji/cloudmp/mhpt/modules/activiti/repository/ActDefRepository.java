package com.taiji.cloudmp.mhpt.modules.activiti.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.activiti.service.dto.ActDefQueryCriteria;;

/**
* @author lwy
* @date 2019-10-23
*/
@Repository
public interface ActDefRepository {

	List<Map> findAll(ActDefQueryCriteria criteria);
	
	Long findTotalCount(ActDefQueryCriteria criteria);
	
	//根据processKey查询版本号最大的流程定义
	List<Map> findLatestByKey(String processKey);
}