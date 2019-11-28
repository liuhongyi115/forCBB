package com.taiji.cloudmp.mhpt.constant;
/**
 * 
 * @author larry
 *
 */
public interface WorkOrderState {
	/**
	 * 工单状态-0 待申请  (未提交状态)
	 */
	public static final String DRAFT = "0";
	/**
	 * 工单状态-1 待审核  (提交审核状态)
	 */
	public static final String WAITAUDIT = "1";
	/**
	 * 工单状态-2 办理中  (只需要一步审核到运维平台 办理中)
	 */
	public static final String HANDLING = "2";
	/**
	 * 工单状态-3办理中(需要两步步或多部审核到运维平台 办理中)
	 */
	public static final String HANDLING_FOR_TWO_AUDIT = "3";
	
	/**
	 * 工单状态-4运维反馈  (运维反馈，门户租户未确认)
	 */
	public static final String NOT_ACK = "4";
	
	
	/**
	 * 工单状态-5分配完成 (门户已确认资源)
	 */
	public static final String COMPLETE = "5";
	
	/**
	 * 工单状态-6驳回到上一节点 
	 */
	public static final String REJECT_TO_LAST = "6";
	
	/**
	 * 工单状态-7驳回开始节点
	 */
	public static final String REJECT_TO_START = "7";
	
     
	/**
	 * 工单状态-8申述中(申诉状态)
	 */
	public static final String IN_COMPLAINT = "8";
	
	
	
	
}
