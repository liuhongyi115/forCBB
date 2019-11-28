package com.taiji.cloudmp.mhpt.modules.resourcemanage.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.JudgeFullscore;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.JudgeFullscoreQueryCriteria;;

/**
* @author xiao
* @date 2019-11-14
*/
@Repository
public interface JudgeFullscoreRepository {

	void add(JudgeFullscore judgeFullscore);
	
	void delete(String id);
	
	void update(JudgeFullscore judgeFullscore);
	
	JudgeFullscore findById(String id);
	
	List<JudgeFullscore> findAll(JudgeFullscoreQueryCriteria criteria);
	
	Long findTotalCount(JudgeFullscoreQueryCriteria criteria);
}