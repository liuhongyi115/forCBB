package com.taiji.cloudmp.mhpt.modules.system.domain;

/**
 * @author wyw
 * @date 2019/10/10 14:12
 */
public class RolesPermissons {

    private Long RoleId;

    private Long PermissionId;

    public Long getPermissionId() {
        return PermissionId;
    }

    public void setPermissionId(Long permissionId) {
        PermissionId = permissionId;
    }

    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }
}
