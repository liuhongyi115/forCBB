package com.taiji.cloudmp.mhpt.modules.demo.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.demo.domain.TestQingjia;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaCriteria;
import com.taiji.cloudmp.mhpt.modules.demo.service.dto.TestQingjiaDTO;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
public interface TestQingjiaService {

    /**
     * queryAll
     * @param criteria
     * @return
     */
    Object queryAllDaiShenHe(TestQingjiaCriteria criteria, Pageable pageable);

    /**
     * 
     * @param id
     * @return
     */
     TestQingjiaDTO findById(String id);
     
     TestQingjiaDTO zanCun(TestQingjia qingjia);
    
     TestQingjiaDTO submitQingjia(TestQingjia qingjia,String loginUserId);
     
     TestQingjiaDTO reject(TestQingjia qingjia,String loginUserId);
     
     String formFilePath(String procInstId);
     
     TestQingjiaDTO create(TestQingjia qingjia);

	 Object queryAll(TestQingjiaCriteria criteria, Pageable pageable);
	 
	 InputStream getLiuChengTu(String processInstanceId);
	 
	 List<Map<String,Object>> getHistoryOfProcess(String procInstId);
	 

	void startFlow(TestQingjia resource, String userId);
}