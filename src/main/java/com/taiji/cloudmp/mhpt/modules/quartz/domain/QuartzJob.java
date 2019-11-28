package com.taiji.cloudmp.mhpt.modules.quartz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Data
public class QuartzJob implements Serializable {
	
	private static final long serialVersionUID = -7692224436232809570L;

	public static final String JOB_KEY = "JOB_KEY";
	
	public static final String ID 			   = "id";
	public static final String JOBNAME 	       = "jobName";
	public static final String BEANNAME 	   = "beanName";
	public static final String METHODNAME 	   = "methodName";
	public static final String PARAMS 		   = "params";
	public static final String CRONEXPRESSION  = "cronExpression";
	public static final String ISPAUSE 	       = "isPause";
	public static final String REMARK          = "remark";
	public static final String UPDATETIME 	   = "updateTime";
	
    @NotNull(groups = {Update.class})
    private Long id;

    //定时器名称
    private String jobName;

    //Bean名称
    @NotBlank
    private String beanName;

    //方法名称
    @NotBlank
    private String methodName;

    //参数
    private String params;

    //cron表达式
    @NotBlank
    private String cronExpression;

    //状态
    private Boolean isPause = false;

    //备注
    @NotBlank
    private String remark;

    //创建日期
    private Date updateTime;

    public interface Update{}
}