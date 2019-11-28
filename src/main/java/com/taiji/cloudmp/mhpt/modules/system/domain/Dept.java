package com.taiji.cloudmp.mhpt.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class Dept implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    private Boolean enabled;

    /**
     * 上级部门
     */
    private Long pid;

//    private Set<Role> roles;

    private Timestamp createTime;

    public @interface Update {}
}