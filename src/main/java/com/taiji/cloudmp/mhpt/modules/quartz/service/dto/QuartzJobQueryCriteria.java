package com.taiji.cloudmp.mhpt.modules.quartz.service.dto;

import com.taiji.cloudmp.pager.BaseModel;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zheng Jie
 * @date 2019-6-4 10:33:02
 */
@Data
@NoArgsConstructor
public class QuartzJobQueryCriteria extends BaseModel {

    //@Query(type = Query.Type.INNER_LIKE)
    private String jobName;
    
//    @Query
    private Boolean isSuccess;
}
