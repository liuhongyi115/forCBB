package com.taiji.cloudmp.mhpt.modules.workOrder.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderProc;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderProcQueryCriteria;;

/**
* @author lhy
* @date 2019-11-26
*/
@Repository
public interface OrderProcRepository {

	void add(OrderProc OrderProc);
	
	void delete(String id);
	
	void update(OrderProc OrderProc);
	
	OrderProc findById(String id);
	
	List<OrderProc> findAll(OrderProcQueryCriteria criteria);
	
	Long findTotalCount(OrderProcQueryCriteria criteria);
	
	void deleteByBaseInfoId(String baseInfoId);
}