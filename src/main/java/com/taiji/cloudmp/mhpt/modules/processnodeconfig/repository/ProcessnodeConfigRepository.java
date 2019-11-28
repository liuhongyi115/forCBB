package com.taiji.cloudmp.mhpt.modules.processnodeconfig.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.processnodeconfig.domain.ProcessnodeConfig;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;

/**
* @author lwy
* @date 2019-10-31
*/
@Repository
public interface ProcessnodeConfigRepository {

	void add(ProcessnodeConfig processnodeConfig);
	
	void delete(String id);
	
	void update(ProcessnodeConfig processnodeConfig);
	
	ProcessnodeConfig findById(String id);
	
	List<ProcessnodeConfig> findAll(ProcessnodeConfigQueryCriteria criteria);
	
	Long findTotalCount(ProcessnodeConfigQueryCriteria criteria);

	void addByList(List<ProcessnodeConfig> list);
	
	List<User> findUserByConditions(@Param("deptId") String deptId, @Param("roleId") String roleId, @Param("userIds") String userIds);
	
}