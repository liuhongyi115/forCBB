package com.taiji.cloudmp.mhpt.modules.quartz.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Data
public class QuartzLog implements Serializable {

	private static final long serialVersionUID = 8673176044445363143L;

	public static final String ID 			   = "id";
	public static final String JOBNAME 	       = "jobName";
	public static final String BEANNAME 	   = "beanName";
	public static final String METHODNAME 	   = "methodName";
	public static final String PARAMS 		   = "params";
	public static final String CRONEXPRESSION  = "cronExpression";
	public static final String ISSUCCESS 	   = "isSuccess";
	public static final String EXCEPTIONDETAIL = "exceptionDetail";
	public static final String TIME 		   = "time";
	public static final String CREATETIME 	   = "createTime";
	
	private Long id;

    // 任务名称
    private String jobName;

    //Bean名称
    private String beanName;

    //方法名称
    private String methodName;

    //参数
    private String params;

    //cron表达式
    private String cronExpression;

    //状态
    private Boolean isSuccess;

    //异常详细
    private String exceptionDetail;

    //耗时（毫秒）
    private Long time;

    //创建日期
    private Date createTime;
}
