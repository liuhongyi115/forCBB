package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.Judge;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeQueryCriteria;;

/**
* @author xiao
* @date 2019-10-22
*/
@Repository
public interface JudgeRepository {

	void add(Judge judge);
	
	void delete(String[] juId);
	
	void update(Judge judge);
	
	Judge findById(String juId);
	
	List<Judge> findAll(JudgeQueryCriteria criteria);
	
	Long findTotalCount(JudgeQueryCriteria criteria);

	Judge findByName(String name);
}