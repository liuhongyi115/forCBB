package com.taiji.cloudmp.mhpt.modules.demo.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class TestQingjiaDTO implements Serializable {
	private String id;
	private String name;
	private String date;
	private String yuanyin;
	private String suggestion;
	private String procInstId;
	private String isStart;
}