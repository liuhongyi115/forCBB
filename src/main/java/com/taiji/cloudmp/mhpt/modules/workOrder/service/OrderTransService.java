package com.taiji.cloudmp.mhpt.modules.workOrder.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderTrans;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransDTO;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransQueryCriteria;

/**
* @author lhy
* @date 2019-11-26
*/
public interface OrderTransService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(OrderTransQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(OrderTransQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    OrderTransDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    OrderTransDTO create(OrderTrans resources);

    /**
     * update
     * @param resources
     */
    void update(OrderTrans resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
    
    
    ////////////////////////////////////////////////
    /**
     * 写入 trans的通用方法
     * @param baseInfoId
     * @param userId
     * @param username
     * @param state
     * @param stateDesc
     * @param WorkOrderType
     * @return
     */
 public OrderTransDTO writeToTrans(String baseInfoId,String userId,String username,String state,String stateDesc,String WorkOrderType);
 
 /***
  * 根据基本信息id删除Trans
  * @param baseInfoId
  */
 public void deleteByBaseInfoId(String baseInfoId);
 
 /**
  * 根据 基本信息id 查询状态
  * @param baseInfoId
  * @return
  */
 public OrderTransDTO findByBaseInfoId(String baseInfoId);
    
}