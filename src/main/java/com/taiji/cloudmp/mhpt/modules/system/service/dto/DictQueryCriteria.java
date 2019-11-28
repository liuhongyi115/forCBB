package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.taiji.cloudmp.annotation.Query;
import lombok.Data;

import java.util.Set;

/**
 * 公共查询类
 */
@Data
public class DictQueryCriteria {

    // 多字段模糊
//    @Query(blurry = "name,remark")
//    private String blurry;

    @Query(type = Query.Type.IN, propName="id")
    private Set<Long> ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;


    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    @Query
    private Long pid;

}
