package com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author lhy
* @date 2019-11-13
*/
@Data
public class ResApplyBaseInfoDTO implements Serializable {
	
	private String id;
	
	// 申请个人单位
	private String applyCompany;
	
	// 单位地址
	private String companyAddr;
	
	// 网站/系统名称
	private String windowName;
	
	// 申请日期
	private String applyDate;
	
	// 申请业务联系人姓名
	private String applyerName;
	
	// 申请业务联系人传真
	private String applyerFax;
	
	// 申请业务联系人电话
	private String applyerPhone;
	
	// 申请业务联系人email
	private String applyerEmail;
	
	// 技术联系人姓名
	private String techName;
	
	// 技术联系人传真
	private String techFax;
	
	// 技术联系人邮箱
	private String techEmail;
	
	// 技术联系人电话
	private String teahPhone;
	
	// 计划部署时间
	private String planDeployTime;
	
	// 上线时间
	private String shangxianTime;
	
	// 办件id
	private String banjianId;
	
	// 创建时间
	private String createTime;
	
	// 创建人
	private String creater;
	
	// 更新时间
	private String updateTime;
	
	// 更新人
	private String updater;
	
	// 办件编码
	private String banjianCode;
	
	// 系统所在网络(业务所属区域)
	private String belongNetwork;
	
	// 是否签署协议
	private String isAgreement;
	
	// 是否历史记录
	private String isHistory;
	
	// 业务系统等级保护
	private String classifiedLevel;
	
	// 扩容状态
	private String dilatationState;
	
	// 扩容次数
	private Long dilatationCount;
	
	// 云厂商
	private String cloudVendor;
	
	// 云服务种类
	private String cloudServicesKind;
	
	// 服务周期(最多12个月)
	private Long serviceCycle;
	
	// Vpn数
	private Long vpnCount;
	
	// Ip数
	private Long ipCount;
	
	// 是否连接互联网
	private String isNet;
	
	// 期望带宽
	private String expectBandwidth;
	
	// 其他特殊需求
	private String otherNeeds;
}