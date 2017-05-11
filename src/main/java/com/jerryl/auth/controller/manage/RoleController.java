package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.RoleMapper;
import com.jerryl.auth.dao.model.Role;
import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.ModuleType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 * 角色相关
 */
@RestController
@RequestMapping("roles")
public class RoleController implements RestServiceController<Role, Integer>{
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Object getOne(@PathVariable("id") Integer id) {
        Role role = roleMapper.findOne(id);
        return ToWeb.buildResult().setObjData(role);
    }

    @Override
    public Object getList(@RequestParam(value = "rowSize", required = false) Integer rowSize, @RequestParam(value = "page", required = false) Integer page) {
        if(page==null){
            page = 1;
        }
        if(rowSize==null){
            rowSize = 1000;
        }
        List<Role> roles = roleMapper.findAll(new PageRequest(page, rowSize));
        int total = roleMapper.countAll();

        return ToWeb.buildResult()
                .rows(ToWeb.Rows.buildRows()
                        .setTotalRows(total)
                        .setTotalPages(total/rowSize+1)
                        .setRowSize(rowSize)
                        .setList(roles)
                        .setCurrent(page));
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.ADD)
    public Object postOne(@RequestBody Role entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object putOne(@PathVariable("id") Integer id, @RequestBody Role entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object patchOne(@PathVariable("id") Integer id, @RequestBody Role entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.DEL)
    public Object deleteOne(@PathVariable("id") Integer id) {
        return null;
    }
}
