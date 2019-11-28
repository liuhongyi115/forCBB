package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto;

import lombok.Data;
import com.taiji.cloudmp.pager.BaseModel;



/**
* @author xiao
* @date 2019-11-14
*/
@Data
public class JudgeFullscoreQueryCriteria extends BaseModel{
	private String id;
	private Integer fullScore;
}