package com.jerryl.auth.service.impl;

import com.jerryl.auth.dao.mapper.ActionMapper;
import com.jerryl.auth.dao.mapper.UserPrivilegeMapper;
import com.jerryl.auth.dao.model.RolePrivileges;
import com.jerryl.auth.service.PrivilegeService;
import com.jerryl.auth.service.dto.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by liuruijie on 2017/4/7.
 */
@Service
public class PrivilegeImpl implements PrivilegeService{
    @Autowired
    ActionMapper actionMapper;
    @Autowired
    UserPrivilegeMapper privilegeMapper;

    @Override
    public boolean checkPrivilege(SessionUser user, Integer moduleId, Integer actionId) {
        List<RolePrivileges> rolePrivileges = actionMapper.findActionsByModuleRoles(moduleId, user.getRoleIds());
        for(RolePrivileges privilege: rolePrivileges){
            String[] ids = privilege.getActions().split(",");
            for(String id: ids){
                if(Integer.parseInt(id)==actionId){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<RolePrivileges> getRolePrivileges(String roleId) {
        return null;
    }

    @Override
    public List<Integer> getRoleModuleActions(Integer roleId, Integer moduleId) {
        List<RolePrivileges> rolePrivileges = actionMapper.findActionsByModuleRoles(moduleId, Arrays.asList(roleId));
        List<Integer> actions = new ArrayList<>();
        if(rolePrivileges.size()==0){
            return actions;
        }
        RolePrivileges privilege = rolePrivileges.get(0);
        for(String s: privilege.getActions().split(",")){
            if("".equals(s)){
                continue;
            }
            privilege.getActionIds().add(Integer.parseInt(s));
        }
        actions.addAll(privilege.getActionIds());
        return actions;
    }

    @Transactional
    @Override
    public void bindPassportRoles(String passportId, Integer[] roleIds) {
        privilegeMapper.unbindRoleByPassportId(passportId);
        privilegeMapper.bindRoleBatch(passportId, Arrays.asList(roleIds));
    }

    @Transactional
    @Override
    public void bindRoleAction(Integer roleId, Integer moduleId, Integer actionId) {
        List<RolePrivileges> rolePrivileges = actionMapper.findActionsByModuleRoles(moduleId
                , Arrays.asList(roleId));
        RolePrivileges privilege = rolePrivileges.size()==0?new RolePrivileges()
                :rolePrivileges.get(0);
        privilege.setModuleId(moduleId);
        privilege.setRoleId(roleId);
        //去重
        String[] ids = privilege.getActions().split(",");
        for(String id: ids){
            if("".equals(id)){continue;}
            privilege.getActionIds().add(Integer.parseInt(id));
        }
        //加入新的action
        privilege.getActionIds().add(actionId);
        //整理
        StringBuilder actions = new StringBuilder();
        for(Integer id: privilege.getActionIds()){
            actions.append(id).append(",");
        }
        actions.deleteCharAt(actions.length()-1);
        privilege.setActions(actions.toString());
        //更新
        actionMapper.unbindAllByRole(roleId, moduleId);
        actionMapper.bindRoleActions(roleId, moduleId, actions.toString());
    }

    @Override
    public void bindRoleActions(Integer roleId, Integer moduleId, Integer[] actionIds) {
        //去重
        Set<Integer> actionSet = new HashSet<>();
        actionSet.addAll(Arrays.asList(actionIds));
        //整理
        StringBuilder actions = new StringBuilder();
        for(Integer id: actionSet){
            actions.append(id).append(",");
        }
        actions.deleteCharAt(actions.length()-1);

        actionMapper.unbindAllByRole(roleId, moduleId);
        actionMapper.bindRoleActions(roleId, moduleId, actions.toString());
    }
}
