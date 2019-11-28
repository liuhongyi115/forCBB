package com.taiji.cloudmp.mhpt.modules.resourceApply.service;

import org.springframework.data.domain.Pageable;

import com.taiji.cloudmp.mhpt.modules.resourceApply.domain.ResApplyBaseInfo;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoDTO;
import com.taiji.cloudmp.mhpt.modules.resourceApply.service.dto.ResApplyBaseInfoQueryCriteria;

/**
* @author lhy
* @date 2019-11-13
*/
public interface ResApplyBaseInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ResApplyBaseInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ResApplyBaseInfoQueryCriteria criteria);



  
    
    /////////////////////////////////////////////////////////
    
    /***
     * 写入基本信息
     * @param resources  基本信息
     * @param userId   操作人id
     * @param username  操作人 username
     * @return
     */
    ResApplyBaseInfoDTO createBaseInfoWithBanjianCode(ResApplyBaseInfo resources,String userId,String username);
    
    /**
    * 查询 待申请的 order  (目前缺少 驳回的单子。 后面需要在sql中补充union的sql[将驳回的union进来])
    * @param criteria  至少传入 userid 和 state
    * @param pageable
    * @return
    */
    Object queryByDaiShenQing(ResApplyBaseInfoQueryCriteria criteria, Pageable pageable);
    
    /**
     * findById
     * @param id
     * @return
     */
    ResApplyBaseInfoDTO findById(String id);


    /**
     * update
     * @param resources
     */
    ResApplyBaseInfoDTO update(ResApplyBaseInfo resources);
    
    
    /**
     * delete删除基本信息
     * 目前只级联删除了 trans和proc
     * 主机和服务写完以后需要  删除 对应的主机以及服务
     * @param id  基本信息id
     */
    void delete(String id);
    
}