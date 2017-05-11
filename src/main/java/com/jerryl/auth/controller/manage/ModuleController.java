package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.ModuleMapper;
import com.jerryl.auth.dao.model.Module;
import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.ModuleType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 * 模块相关
 */
@RestController
@RequestMapping("modules")
public class ModuleController implements RestServiceController<Module, Integer>{
    @Autowired
    ModuleMapper moduleMapper;

    @Override
    public Object getOne(@PathVariable("id") Integer id) {
        return null;
    }

    @Override
    public Object getList(@RequestParam(value = "rowSize", required = false) Integer rowSize, @RequestParam(value = "page", required = false) Integer page) {
        if(page==null){
            page = 1;
        }
        if(rowSize==null){
            rowSize = 1000;
        }
        List<Module> modules = moduleMapper.findAll(new PageRequest(page, rowSize));
        int total = moduleMapper.countAll();

        return ToWeb.buildResult()
                .rows(ToWeb.Rows.buildRows()
                .setTotalRows(total)
                .setTotalPages(total/rowSize+1)
                .setRowSize(rowSize)
                .setList(modules)
                .setCurrent(page));
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.ADD)
    public Object postOne(@RequestBody Module entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object putOne(@PathVariable("id") Integer id, @RequestBody Module entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object patchOne(@PathVariable("id") Integer id, @RequestBody Module entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.DEL)
    public Object deleteOne(@PathVariable("id") Integer id) {
        return null;
    }
}
