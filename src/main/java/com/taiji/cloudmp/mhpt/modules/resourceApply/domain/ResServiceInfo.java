package com.taiji.cloudmp.mhpt.modules.resourceApply.domain;

import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author lhy
* @date 2019-11-15
*/
@Data
public class ResServiceInfo implements Serializable {
	
    private String id;
	// 办件id
    private String banjianId;
	// 基础信息id
    private String baseInfoId;
	// 服务目录id
    private String serviceId;
	// 资源服务名称
    private String serviceName;
	// 资源分类（服务类别）
    private String serviceClassify;
	// 资源服务网络 政务网0、互联网1 、所有网络2
    private Integer serviceNet;
	// 资源服务类型  主机服务1 、系统服务2
    private Integer serviceType;
	// 资源服务数量(服务里的数量)
    private String serviceNum;
	// 资源服务单位(就是单位)
    private String serviceUnit;
	// 0系统盘  1数据盘  资源排序?
    private Integer serviceOrder;
	// 创建时间
    private String serviceCtime;
	// 更新时间
    private String serviceUtime;
	// 是否新增 0：是；1：否 用户判断资源变更是否新增主机，资源申请默认为1
    private Integer isAdd;
	// 云服务种类
    private String cloudServicesKind;
	// 服务目录种类
    private String servicecatalogKind;
	// 计价（元/月）
    private BigDecimal serviceMoney;
	// 服务总价
    private BigDecimal serviceSumMoney;
	// 服务项
    private String serviceComment;
	// 资源变更使用0修改,   1未修改，资源申请默认1
    private Integer isUpdate;
	// 主机对应id
    private String hostId;
	// 上级服务id,用于资源变更复制主机信息进行修改后对比
    private String prevServiceId;
	// 服务状态 0废弃
    private String state;
}