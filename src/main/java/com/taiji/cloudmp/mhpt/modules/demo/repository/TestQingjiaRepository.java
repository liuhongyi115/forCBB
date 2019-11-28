package com.taiji.cloudmp.mhpt.modules.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.taiji.cloudmp.mhpt.modules.demo.domain.TestQingjia;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaCriteria;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper
@Repository
public interface TestQingjiaRepository {

    Long findAllDaiShenHeByCount(TestQingjiaCriteria criteria);
    
    List<TestQingjia> findAllDaiShenHe(TestQingjiaCriteria criteria);
    
    TestQingjia findQingJiaById(String id);
    /**
     * 只更新 审核意见。  可用于暂存或者  驳回后 重新提交 在驳回的请求
     * @param id
     */
    void updateSuggestion(TestQingjia qingjia); 
    
    void add(TestQingjia resources);

	Long findTotalCount(TestQingjiaCriteria criteria);

	List<TestQingjia> findAll(TestQingjiaCriteria criteria);
//    void save(Dept d);

	void update(TestQingjia resource);
    
//    void update(Dept d);
    
//    void deleteById(Long id);
    
}
