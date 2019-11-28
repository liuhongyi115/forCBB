package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.taiji.cloudmp.annotation.Query;
import com.taiji.cloudmp.pager.BaseModel;
import lombok.Data;

/**
 * 公共查询类
 */
@Data
public class RoleQueryCriteria extends BaseModel {

    // 多字段模糊
//    @Query(blurry = "name,remark")
//    private String blurry;

    private String name;
}
