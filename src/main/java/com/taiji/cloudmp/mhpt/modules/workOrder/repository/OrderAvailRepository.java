package com.taiji.cloudmp.mhpt.modules.workOrder.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.workOrder.domain.OrderAvail;
import com.taiji.cloudmp.mhpt.modules.workOrder.service.dto.OrderAvailQueryCriteria;;

/**
* @author lhy
* @date 2019-11-26
*/
@Repository
public interface OrderAvailRepository {

	void add(OrderAvail OrderAvail);
	
	void delete(String id);
	
	void update(OrderAvail OrderAvail);
	
	OrderAvail findById(String id);
	
	List<OrderAvail> findAll(OrderAvailQueryCriteria criteria);
	
	Long findTotalCount(OrderAvailQueryCriteria criteria);
}