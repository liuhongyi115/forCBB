package com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author lhy
* @date 2019-11-14
*/
@Data
public class ResHostInfoDTO implements Serializable {
	
	private String id;
	
	// 办件id
	private String banjianId;
	
	// 基础信息id
	private String baseInfoId;
	
	// 云主机模板id，物理机 null，自定义 0
	private String hostId;
	
	// 主机名称
	private String hostName;
	
	// cpu 数量？
	private String hostCpu;
	
	// 内存 数量？
	private String hostMemory;
	
	// 主机硬盘 数量？
	private String hostDisk;
	
	// 主机类型 1云主机、2物理机
	private Integer hostType;
	
	// 更新时间
	private String bjHostUtime;
	
	// 资源创建时间
	private String bjHostCtime;
	
	// 操作系统
	private String hostOperating;
	
	// 物理机配置
	private String hostPhysical;
	
	// 网络标识 0，政务网1，互联网2所有网络
	private Integer serviceNet;
	
	// 是否新增,0：是；1：否用户判断资源变更是否新增主机，资源申请默认为1
	private Integer isAdd;
	
	// Cpu服务标准id  来自于资源服务标准表(服务目录)id
	private String cpuId;
	
	// 内存服务标准id  来自于资源服务标准表(服务目录)id
	private String memoryId;
	
	// 硬盘服务标准id  来自于资源服务标准表(服务目录)id
	private String diskId;
	
	// 暂未使用 （默认1台）
	private Long hostTaishu;
	
	// 资源变更使用 0修改 ;1未修改，资源申请默认1
	private Integer isUpdate;
	
	// 物理机服务标准id  来自于资源服务标准表(服务目录)id
	private String physicalId;
	
	// gpu数量
	private String hostGpu;
	
	// gpu服务标准id  来自于资源服务标准表(服务目录)id
	private String gpuId;
	
	// 上级主机id
	private String prevEquipId;
	
	// 云主机服务类型(云主机类型)
	private String yunHostType;
	
	// 数据盘单位
	private String diskUnit;
	
	// 系统盘服务标准id  来自于资源服务标准表(服务目录)id
	private String systemDiskId;
	
	// 系统盘数量
	private String systemDiskSize;
	
	// 系统盘单位
	private String systemDiskUnit;
	
	// 系统服务标准id  来自于资源服务标准表(服务目录)id
	private String operatingId;
	
	// 操作系统类型
	private String operaSystemType;
	
	// 操作系统版本
	private String oprSystemVersion;
	
	// 被copy 的主机id
	private String copyHostId;
	
	// 主机状态0废弃
	private String status;
}