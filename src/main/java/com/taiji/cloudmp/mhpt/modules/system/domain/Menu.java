package com.taiji.cloudmp.mhpt.modules.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Getter
@Setter
public class Menu implements Serializable {

    private Long id;

    private String name;

    private Long sort;

    private String path;

    private String component;

    private String icon;

    /**
     * 上级菜单ID
     */
    private Long pid;

    /**
     * 是否为外链 true/false
     */
    private Boolean iFrame;

//    @ManyToMany(mappedBy = "menus")
//    @JsonIgnore
//    private Set<Role> roles;

    private Timestamp createTime;

    public interface Update{}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
