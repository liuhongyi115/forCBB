package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.taiji.cloudmp.annotation.Query;

import lombok.Data;

/**
 * 公共查询类
 */
@Data
public class MenuQueryCriteria {

    // 多字段模糊
//    @Query(blurry = "name,path,component")
//    private String blurry;
//    
    private String name;
    private String path;
    private String component;
    
}
