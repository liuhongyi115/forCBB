package com.taiji.cloudmp.mhpt.modules.workOrder.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderTrans;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderTransQueryCriteria;;

/**
* @author lhy
* @date 2019-11-26
*/
@Repository
public interface OrderTransRepository {

	
	void delete(String id);
	
	void update(OrderTrans OrderTrans);
	
	OrderTrans findById(String id);
	
	List<OrderTrans> findAll(OrderTransQueryCriteria criteria);
	
	Long findTotalCount(OrderTransQueryCriteria criteria);
	
	
	///////
	void add(OrderTrans OrderTrans);
	
	void deleteByBaseInfoId(String baseInfoId);
	
	OrderTrans findByBaseInfoId(String baseInfoId);
}