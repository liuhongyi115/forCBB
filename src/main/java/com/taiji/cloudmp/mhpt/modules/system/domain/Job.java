package com.taiji.cloudmp.mhpt.modules.system.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Data
public class Job implements Serializable {

	private static final long serialVersionUID = -6290414309492429073L;

	public static final String ID 		  = "id";
	public static final String NAME 	  = "name";
	public static final String SORT 	  = "sort";
	public static final String ENABLED 	  = "enabled";
	public static final String DEPT 	  = "dept";
	public static final String DEPTID 	  = "deptId";
	public static final String CREATETIME = "createTime";
	
	//ID
    @NotNull(groups = Update.class)
    private Long id;

    //名称
    @NotBlank
    private String name;

    @NotNull
    private Long sort;

    //状态
    @NotNull
    private Boolean enabled;

    private Dept dept;
    
    private Long deptId;

    //创建日期
    private Date createTime;

    public @interface Update {}
}