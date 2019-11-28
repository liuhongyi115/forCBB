//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto;

import java.io.Serializable;

import lombok.Data;
@Data
public class HostStandardDTO implements Serializable {
	//id
    private String id;
    //主机标准名
    private String hostName;
    //cpu默认数
    private String cpuDefaultNum;
    //排序暂未使用
    private Integer hostOrder;
    //创建时间
    private String hostCreateTime;
    //修改时间
    private String hostUpdateTime;
    //cpu标准id
    private String cpuStandardId;
    //cpu服务项
    private String cpuServiceItems;
    //云厂商
    private String cloudVendor;
    //内存标准ID
    private String memoryStandardId;
    //内存服务项
    private String memoryServiceItems;
    //内存默认数量
    private String memoryDefaultSize;
    //数据盘标准Id
    private String diskStandardId;
    //数据盘服务项
    private String diskServiceItems;
    //数据盘默认数量
    private String diskDefaultSize;
    //云服务种类
    private String cloudServicesKind;
    //Gpu标准id
    private String gpuStandardId;
    //Gpu服务项
    private String gpuServiceItems;
    //Gpu默认数量
    private String gpuDefaultNum;
    //云主机服务种类
    private  String cloudHostName;
    //数据盘单位
    private String diskUnit;
    //操作系统标准id
    private String osStandardId;
    //操作系统服务项
    private String osServiceItems;
    //系统盘标准id
    private String systemDiskId;
    //系统盘服务项
    private String systemDiskItems;
    //系统盘默认数量
    private String systemDiskSize;
    //系统盘单位
    private String systemDiskUnit;
    //操作系统版本
    private String osVersion;
    //是否删除
    public String status;
}
