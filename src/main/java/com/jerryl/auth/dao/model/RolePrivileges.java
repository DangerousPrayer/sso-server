package com.jerryl.auth.dao.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liuruijie on 2017/4/6.
 */
public class RolePrivileges {
    private Integer roleId;
    private Integer moduleId;
    private Set<Integer> actionIds;
    private String actions;

    public RolePrivileges() {
        actionIds = new HashSet<>();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Set<Integer> getActionIds() {
        return actionIds;
    }

    public void setActionIds(Set<Integer> actionIds) {
        this.actionIds = actionIds;
    }
}
