package com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto;

import lombok.Data;
import com.taiji.cloudmp.pager.BaseModel;



/**
* @author lhy
* @date 2019-11-13
*/
@Data
public class ResApplyBaseInfoQueryCriteria extends BaseModel{
	private String id;
	private String applyCompany;
	private String windowName;
	private String banjianCode;
	private String isAgreement;
	
	private String state;
	private String managerId;
}