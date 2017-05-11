package com.jerryl.auth.controller.sso;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.model.ModuleRegister;
import com.jerryl.auth.service.SSOAuthService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
     * @param moduleKey 模块
     * @param actionId 操作
     */
    @GetMapping("checkPrivilege")
    public Object checkToken(String token,String moduleKey,Integer actionId) throws IOException {
        ssoAuthService.checkPrivilege(token, moduleKey, actionId);
        return ToWeb.buildResult().putExtra("isOk", true);
    }

    /**
     * 注册模块
     * @param hostAuthKey
     * @param urls
     * @return
     */
    @PostMapping("moduleRegister")
    public Object moduleRegister(String hostAuthKey, @RequestParam("dataUrls[]") List<String> urls){
        urls.remove(urls.size()-1);
        ssoAuthService.registerHost(hostAuthKey, urls);
        return ToWeb.buildResult();
    }
}
