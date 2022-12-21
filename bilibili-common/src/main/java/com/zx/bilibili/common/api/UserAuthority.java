package com.zx.bilibili.common.api;

import com.zx.bilibili.domain.AuthRoleElementOperation;
import com.zx.bilibili.domain.AuthRoleMenu;

import java.util.List;

/**
 * 用户权限
 */
public class UserAuthority {

    /**
     * 用户具有的元素可见性权限列表
     */
    List<AuthRoleElementOperation> roleElementOperationList;

    /**
     * 用户具有的操作权限列表
     */
    List<AuthRoleMenu> roleMenuList;

    public List<AuthRoleElementOperation> getRoleElementOperationList() {
        return roleElementOperationList;
    }

    public void setRoleElementOperationList(List<AuthRoleElementOperation> roleElementOperationList) {
        this.roleElementOperationList = roleElementOperationList;
    }

    public List<AuthRoleMenu> getRoleMenuList() {
        return roleMenuList;
    }

    public void setRoleMenuList(List<AuthRoleMenu> roleMenuList) {
        this.roleMenuList = roleMenuList;
    }
}
