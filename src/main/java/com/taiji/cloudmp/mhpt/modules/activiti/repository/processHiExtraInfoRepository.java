package com.taiji.cloudmp.mhpt.modules.activiti.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.activiti.domain.ProcessHiExtraInfo;;

/**
* @author lwy
* @date 2019-10-23
*/
@Repository
public interface processHiExtraInfoRepository {
	void save(ProcessHiExtraInfo resources);
	List<Map<String,Object>> getHistoryOfProcess(String procInstId);
}