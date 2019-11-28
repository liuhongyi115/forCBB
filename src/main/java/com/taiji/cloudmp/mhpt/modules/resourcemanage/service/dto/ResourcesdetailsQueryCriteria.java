package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto;

import lombok.Data;

import java.math.BigDecimal;

import com.taiji.cloudmp.pager.BaseModel;



/**
* @author xiao
* @date 2019-11-19
*/
@Data
public class ResourcesdetailsQueryCriteria extends BaseModel{
	// id
	private String id;
	
	// 资源名称
	private String resourcesName;
	
	// 资源数量
	private BigDecimal resourcesNum;
	
	// T\G\MB\核 等些单位
	private String resourceUnit;
	
	// 创建时间
	private String resourceCtime;
	
	// 更新时间
	private String resourceUtime;
	
	// 厂商
	private String resourceSupplier;
	
	// 类型
	private String type;
	
	// 排序
	private Integer resourceIndex;
}