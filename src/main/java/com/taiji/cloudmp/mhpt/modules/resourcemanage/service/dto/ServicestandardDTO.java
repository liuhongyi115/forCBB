package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author xiao
* @date 2019-10-22
*/
@Data
public class ServicestandardDTO implements Serializable {
	
	//id
		private String id;
	    //资源名称
		private String serviceName;
	    //资源分类
		private String serviceKind;
		//资源服务网络(政务网1 、互联网2 、所有网络0)
	    private Integer serviceNetwork;
	    //资源服务类型(1主机服务 2系统服务)
	    private Integer serviceType;
	    //存储服务是否是服务(1主机存储、2备份存储 存储服务使用特殊字段，区分主机存储还是备份存储)
	    private String serviceIsStore;
	    //资源服务单位
	    private String serviceUnit;
	    //资源排序
	    private Integer serviceOrder;
	    //描述
	    private String serviceExplain;
	    //删除状态(1删除 0正常使用)
	    private String State;
	    //创建时间
	    private String serviceCtime;
	    //最近修改时间
	    private String serviceUtime;
	    //描述1
	    private String advantage1;
	    //描述2
	    private String advantage2;
	    //描述3
	    private String advantage3;
	    //描述4
	    private String advantage4;
	    //是否收费（默认是）
	    private String isToll;
	    
	    private String serviceAttribute;
	    
	    private String serviceFtype;
	    
	    private String isFqfw;
	    //云厂商(存字典信息)
	    private String cloudVendor;
	    //云服务种类(存字典信息)
	    private String cloudServicesKind;
	    //服务目录种类(存字典信息)
	    private String servicecatalogKind;
	    //报价（元/月）
	    private String quotedPrice;
	    //
	    private String hostResource;
	    
	    private String operatingView;
}