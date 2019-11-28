package com.taiji.cloudmp.mhpt.modules.activiti.domain;

import lombok.Data;

@Data
public class ProcessHiExtraInfo {
	private String id;
	private String procInstId;
	private String hiTaskId;
	private String suggestion;
}
