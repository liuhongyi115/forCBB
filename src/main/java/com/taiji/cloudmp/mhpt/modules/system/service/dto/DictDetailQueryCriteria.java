package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import com.taiji.cloudmp.annotation.Query;
import com.taiji.cloudmp.pager.BaseModel;
import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Data
public class DictDetailQueryCriteria  extends BaseModel {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;
}