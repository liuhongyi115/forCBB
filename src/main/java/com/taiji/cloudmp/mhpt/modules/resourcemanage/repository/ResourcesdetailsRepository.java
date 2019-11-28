package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Resourcesdetails;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ResourcesdetailsQueryCriteria;;

/**
* @author xiao
* @date 2019-11-19
*/
@Repository
public interface ResourcesdetailsRepository {

	void add(Resourcesdetails resourcesdetails);
	
	void delete(String[] id);
	
	void update(Resourcesdetails resourcesdetails);
	
	Resourcesdetails findById(String id);
	
	List<Resourcesdetails> findAll(ResourcesdetailsQueryCriteria criteria);
	
	Long findTotalCount(ResourcesdetailsQueryCriteria criteria);
}