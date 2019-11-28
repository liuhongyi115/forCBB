package com.taiji.cloudmp.mhpt.modules.system.service.dto;

import java.util.Set;

import com.taiji.cloudmp.pager.BaseModel;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author Zheng Jie
* @date 2019-6-4 14:49:34
*/
@Data
@NoArgsConstructor
public class JobQueryCriteria extends BaseModel {

    private String name;

    private Boolean enabled;

    private Long deptId;

    private Set<Long> deptIds;
}