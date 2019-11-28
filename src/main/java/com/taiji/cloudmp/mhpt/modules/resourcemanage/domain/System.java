package com.taiji.cloudmp.mhpt.modules.resourcemanage.domain;

import lombok.Data;
import java.io.Serializable;

/**
* @author xiao
* @date 2019-10-23
*/
@Data
public class System implements Serializable {
	private String id;
    //服务标准id（对应t_mhpt_servicestandard中的id）
    private String servicestandardId;
    //服务子类（对应t_mhpt_servicestandard中的service_name）
    private String serviceName;

    private Integer serviceOrder;
    //服务项（对应t_mhpt_servicestandard中的service_explain）
    private String serviceExplain;
    //创建时间
    private String resourceCtime;
    //最近更新时间
    private String resourceUtime;
    //云厂商
    private String cloudVendor;
    //云服务种类  0政务云服务目录  1专享云服务目录
    private String cloudServicesKind;
    //中间件类型
    private String type;
    //当前版本
    private String corrVersion;
    //资源分类
    private String serviceKind;
    //服务项分类
    private String hostResource;
    
    //是否删除
    private String status;
}