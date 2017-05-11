package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.RoleMapper;
import com.jerryl.auth.dao.mapper.UserPrivilegeMapper;
import com.jerryl.auth.dao.model.Role;
import com.jerryl.auth.service.PrivilegeService;
import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.ModuleType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 * 权限相关
 */
@RestController
@RequestMapping("privilege")
public class PrivilegeController {
    @Autowired
    UserPrivilegeMapper privilegeMapper;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    RoleMapper roleMapper;

    @PostMapping("savePassportRoles")
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.ADD)
    public Object savePassportRoles(String passportId, @RequestParam("roleIds[]") Integer[] roleIds){
        privilegeService.bindPassportRoles(passportId,roleIds);
        return ToWeb.buildResult().msg("角色分配成功！").refresh();
    }

    @PostMapping("saveRoleActions")
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.ADD)
    public Object saveRoleActions(Integer roleId, Integer moduleId, @RequestParam("actions[]") Integer[] actions){
        privilegeService.bindRoleActions(roleId, moduleId, actions);
        return ToWeb.buildResult().msg("权限分配成功！").refresh();
    }

    @GetMapping("roles")
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.SHOW)
    public Object listRoles(@RequestParam(value = "passportId"
            ,required = false) String passportId){
        if(passportId==null){
            List<Role> roles = roleMapper.findAll(new PageRequest(1, 100));
            return ToWeb.buildResult().setObjData(roles);
        }else{
            List<Integer> ids = roleMapper.findIdsByPassport(passportId);
            return ToWeb.buildResult().setObjData(ids);
        }
    }

    @GetMapping("actions")
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.SHOW)
    public Object listActions(@RequestParam("roleId")Integer roleId
            , @RequestParam("moduleId")Integer moduleId){
        List<Integer> actions = privilegeService.getRoleModuleActions(roleId, moduleId);
        return ToWeb.buildResult().setObjData(actions);
    }
}
