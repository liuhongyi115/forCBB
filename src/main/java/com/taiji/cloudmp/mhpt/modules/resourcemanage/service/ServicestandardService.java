package com.taiji.cloudmp.mhpt.modules.resourcemanage.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourcemanage.domain.ServiceStandard;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServiceStandardQueryCriteria;
import com.taiji.cloudmp.mhpt.modules.resourcemanage.service.dto.ServicestandardDTO;

/**
* @author xiao
* @date 2019-10-22
*/
public interface ServicestandardService {
	
	
	/**
	    * queryAll 分页
	    * @param criteria
	    * @param pageable
	    * @return
	    */
	Object queryContent (ServiceStandardQueryCriteria criteria);

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ServiceStandardQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ServiceStandardQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    ServicestandardDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    ServicestandardDTO create(ServiceStandard resources);

    /**
     * update
     * @param resources
     */
    void update(ServiceStandard resources);

    /**
     * delete
     * @param id
     */
    void delete(String[] id);
}