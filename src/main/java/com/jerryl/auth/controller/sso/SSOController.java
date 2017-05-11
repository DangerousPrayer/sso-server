package com.jerryl.auth.controller.sso;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.service.SSOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by liuruijie on 2017/4/12.
 * 提供给子系统的sso接口
 */
@RestController
@RequestMapping("sso")
public class SSOController {
    @Autowired
    SSOAuthService ssoAuthService;

    /**
     * 校验用户权限，提供给子系统调用
     * @param token 登陆时生成的token
     * @param moduleId 模块
     * @param actionId 操作
     */
    @GetMapping("checkPrivilege")
    public Object checkToken(String token,Integer moduleId,Integer actionId) throws IOException {
        ssoAuthService.checkPrivilege(token, moduleId, actionId);
        return ToWeb.buildResult().putExtra("isOk", true);
    }
}
