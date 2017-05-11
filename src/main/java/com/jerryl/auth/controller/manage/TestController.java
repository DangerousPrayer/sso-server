package com.jerryl.auth.controller.manage;

import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.ModuleType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuruijie on 2017/5/11.
 */
@RestController
public class TestController {
    @RequestMapping("add")
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            ,actionType = ActionType.ADD)
    public Object testAdd(){
        return "add ok!";
    }
}
