package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.mapper.PassportMapper;
import com.jerryl.auth.dao.mapper.UserMapper;
import com.jerryl.auth.dao.model.Passport;
import com.jerryl.auth.service.PassportService;
import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.ModuleType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuruijie on 2017/4/7.
 * 账号相关
 */
@RestController
@RequestMapping("passport")
public class PassportController implements RestServiceController<Passport, String>{
    @Autowired
    PassportService passportService;
    @Autowired
    PassportMapper passportMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Object getOne(@PathVariable("id") String s) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.SHOW)
    public Object getList(@RequestParam(value = "rowSize", required = false) Integer rowSize, @RequestParam(value = "page", required = false) Integer page) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.ADD)
    public Object postOne(@RequestBody Passport entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object putOne(@PathVariable("id") String s, @RequestBody Passport entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object patchOne(@PathVariable("id") String s, @RequestBody Passport entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.DEL)
    public Object deleteOne(@PathVariable("id") String s) {
//        userMapper.deleteByPassportId(s);
        passportMapper.delete(s);
        return ToWeb.buildResult();
    }
}
