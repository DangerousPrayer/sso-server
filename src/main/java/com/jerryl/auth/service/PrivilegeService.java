package com.jerryl.auth.service;

import com.jerryl.auth.dao.model.RolePrivileges;
import com.jerryl.auth.service.dto.SessionUser;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 * 权限服务
 */
public interface PrivilegeService {

    /**
     * 校验权限
     */
    boolean checkPrivilege(SessionUser user, Integer moduleId, Integer actionId);

    List<RolePrivileges> getRolePrivileges(String roleId);

    /**
     * 获取某个角色在某个模块中可进行的操作
     */
    List<Integer> getRoleModuleActions(Integer roleId, Integer moduleId);

    /**
     * 绑定账号角色
     */
    void bindPassportRoles(String passportId, Integer[] roleIds);

    /**
     * 绑定角色在模块中的操作
     */
    void bindRoleAction(Integer roleId, Integer moduleId, Integer actionId);

    /**
     * 绑定角色在模块中的操作
     */
    void bindRoleActions(Integer roleId, Integer moduleId, Integer[] actionIds);
}
