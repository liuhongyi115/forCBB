package com.taiji.cloudmp.mhpt.modules.processnodeconfig.service.dto;

import com.taiji.cloudmp.pager.BaseModel;

import lombok.Data;



/**
* @author lwy
* @date 2019-10-31
*/
@Data
public class ProcessnodeConfigQueryCriteria extends BaseModel{
	private String id;
	private String procdefId;
	private String processKey;
	private String nodeId;
	
}