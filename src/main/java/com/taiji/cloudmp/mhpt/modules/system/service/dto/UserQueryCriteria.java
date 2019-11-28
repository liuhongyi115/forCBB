package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.taiji.cloudmp.annotation.Query;
import com.taiji.cloudmp.pager.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class UserQueryCriteria extends BaseModel implements Serializable  {

    @Query
    private Long id;

    @Query(propName = "id", type = Query.Type.IN, joinName = "dept")
    private Set<Long> deptIds;

    // 多字段模糊
//    @Query(blurry = "email,username")
    private String blurry;

    @Query
    private Boolean enabled;

    private Long deptId;

    private String username;

    private String email;


}
