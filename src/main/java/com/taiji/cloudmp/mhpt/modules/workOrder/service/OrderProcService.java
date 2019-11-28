package com.taiji.cloudmp.mhpt.modules.workOrder.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderProc;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderProcDTO;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderProcQueryCriteria;

/**
* @author lhy
* @date 2019-11-26
*/
public interface OrderProcService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(OrderProcQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(OrderProcQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    OrderProcDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    OrderProcDTO create(OrderProc resources);

    /**
     * update
     * @param resources
     */
    void update(OrderProc resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
    
    /////////
    /**
     * 写入proc通用方法
     * @param baseInfoId
     * @param userId
     * @param username
     * @param state
     * @param stateDesc
     * @param WorkOrderType
     * @param suggestion
     * @return
     */
    public OrderProcDTO writeToProc(String baseInfoId,String userId,String username,String state,String stateDesc,String WorkOrderType,String suggestion);
    
    
    /***
     * 根据基本信息id删除proc
     * @param baseInfoId
     */
    public void deleteByBaseInfoId(String baseInfoId);
    
}