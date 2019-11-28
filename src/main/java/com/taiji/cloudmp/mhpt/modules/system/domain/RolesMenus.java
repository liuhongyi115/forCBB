package com.taiji.cloudmp.mhpt.modules.system.domain;

/**
 * @author wyw
 * @date 2019/10/10 14:11
 */
public class RolesMenus {

    private Long MenuId;

    private Long RoleId;

    public Long getMenuId() {
        return MenuId;
    }

    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }

    public void setMenuId(Long menuId) {
        MenuId = menuId;
    }
}
