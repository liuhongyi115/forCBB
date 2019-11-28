package com.taiji.cloudmp.mhpt.modules.system.domain;

import com.taiji.cloudmp.mhpt.modules.system.service.dto.DeptDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.MenuDTO;
import com.taiji.cloudmp.mhpt.modules.system.service.dto.PermissionDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * 角色
 */
@Getter
@Setter
public class Role  implements Serializable {

    private Long id;

    private String name;

    private String dataScope;

    private Integer level;

    private String remark;

    private Set<Permission> permissions;

    private Set<Menu> menus;

    private Set<Dept> depts;

    private Date createTime;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createDateTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public interface Update{}


}
