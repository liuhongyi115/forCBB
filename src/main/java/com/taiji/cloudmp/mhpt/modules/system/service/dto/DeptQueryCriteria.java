package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import java.util.Set;

import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class DeptQueryCriteria{

//    @Query(type = Query.Type.IN, propName="id")
    private Set<Long> ids;

//    @Query(type = Query.Type.INNER_LIKE)
    private String name;

//    @Query
    private Boolean enabled;

//    @Query
    private Long pid;
}