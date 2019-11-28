package com.taiji.cloudmp.mhpt.modules.workOrder.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author lhy
* @date 2019-11-26
*/
@Data
public class OrderAvailDTO implements Serializable {
	
	private String id;
	
	// 基本信息id或主体id
	private String baseInfoId;
	
	// 状态编号
	private String state;
	
	// 状态描述
	private String stateDesc;
	
	// 工单类型
	private String workOrderType;
}