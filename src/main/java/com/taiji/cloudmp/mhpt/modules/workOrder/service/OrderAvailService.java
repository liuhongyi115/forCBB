package com.taiji.cloudmp.mhpt.modules.workOrder.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderAvail;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailDTO;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailQueryCriteria;

/**
* @author lhy
* @date 2019-11-26
*/
public interface OrderAvailService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(OrderAvailQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(OrderAvailQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    OrderAvailDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    OrderAvailDTO create(OrderAvail resources);

    /**
     * update
     * @param resources
     */
    void update(OrderAvail resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
}