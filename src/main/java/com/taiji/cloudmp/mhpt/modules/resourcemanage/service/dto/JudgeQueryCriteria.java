package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto;

import lombok.Data;
import com.taiji.cloudmp.pager.BaseModel;



/**
* @author xiao
* @date 2019-10-22
*/
@Data
public class JudgeQueryCriteria extends BaseModel{
		// 编号
		private String id;
		
		// 内容
		private String  name;
		
		// 序号
		private Integer serialNumber;
}