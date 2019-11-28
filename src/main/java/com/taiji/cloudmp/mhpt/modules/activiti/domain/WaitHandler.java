package com.taiji.cloudmp.mhpt.modules.activiti.domain;

import lombok.Data;

@Data
public class WaitHandler {
	private String id;
	private String taskId;
	private String userId;
	private String procInstId;
}
