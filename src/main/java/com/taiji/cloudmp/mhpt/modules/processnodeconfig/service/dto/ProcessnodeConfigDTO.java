package com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
* @author lwy
* @date 2019-10-31
*/
@Data
public class ProcessnodeConfigDTO implements Serializable {
	
	private String id;
	
	// 流程定义表id
	private String procdefId;
	
	// 流程定义表name
	private String procdefName;
	
	// 节点名称
	private String nodeName;
	
	// id
	private String formFilePath;
	
	// 节点处理人id
	private String handlerId;
	
	// 节点处理人名称
	private String handlerName;
	
	// 节点处理人loginname
	private String handlerLoginname;
	
	// 流程定义表中的KEY_
	private String processKey;
	
	// node的id 从xml中解析得到
	private String nodeId;
	
	// 部门id
	private String deptId;
	
	// 角色id
	private String roleId;
	private String roleName;
}