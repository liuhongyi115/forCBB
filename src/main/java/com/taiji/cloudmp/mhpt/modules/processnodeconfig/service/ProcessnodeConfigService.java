package com.taiji.cloudmp.mhpt.modules.processnodeconfig.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.processnodeconfig.domain.ProcessnodeConfig;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigDTO;
import com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto.ProcessnodeConfigQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.system.domain.User;

/**
* @author lwy
* @date 2019-10-31
*/
public interface ProcessnodeConfigService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ProcessnodeConfigQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ProcessnodeConfigQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    ProcessnodeConfigDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    ProcessnodeConfigDTO create(ProcessnodeConfig resources);

    /**
     * update
     * @param resources
     */
    void update(ProcessnodeConfig resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
    
    //根据processKey新增，并返回新增后的集合列表
	List<ProcessnodeConfigDTO> addByProcessKey(String processKey);
	
	//根据deptid,roleid,userid查询流程执行人，三表连查取交集
	List<User> getProcessExecutor(String deptId, String roleId, String userId);
	
}