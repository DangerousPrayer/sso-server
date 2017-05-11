package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.Status;
import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.common.exception.BaseException;
import com.jerryl.auth.service.PassportService;
import com.jerryl.auth.service.SSOAuthService;
import com.jerryl.auth.service.dto.RegisterForm;
import com.jerryl.auth.service.dto.SessionUser;
import com.jerryl.auth.util.RedisUtil;
import com.jerryl.auth.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by liuruijie on 2017/4/8.
 *
 */
@RequestMapping("public")
@RestController
public class PublicController {
    @Autowired
    PassportService passportService;
    @Autowired
    Environment env;

    @PostMapping("login")
    public Object login(HttpServletRequest request, HttpServletResponse response, String id, String password, String backToUrl){
        //验证账号密码，登陆
        SessionUser user = passportService.loginAsSessionUser(id, password);
        //根据用户信息获取token
        String token = passportService.createToken(user);
        //维护token与用户信息的映射
        RedisUtil.put(token, user);

        //将返回页面地址url解码
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            backToUrl = new String(decoder.decodeBuffer(backToUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置cookie域
        String s = env.getProperty("auth-client-hosts");
        String[] clientHosts = s.split(",");

        //将token和返回地址一同返回给前台
        return ToWeb.buildResult().putExtra("token", token)
                .putExtra("hosts", clientHosts)
                .putExtra("backToUrl", backToUrl);
    }

    @PostMapping("logout")
    public Object logout(HttpServletRequest request) throws IOException {
        //获取请求头中的认证信息
        String authStr = request.getHeader("authorization");
        if(authStr == null){
            return ToWeb.buildResult().refresh();
        }

        authStr = authStr.split(" ")[1];
        if(authStr == null){
            return ToWeb.buildResult().refresh();
        }
        //解码出token
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(authStr);
        String token = new String(bytes);
        RedisUtil.remove(token);
        return ToWeb.buildResult().refresh();
    }

    /**
     * 注册
     * @param registerForm 注册表单
     */
    @PostMapping("register")
    public Object register(@RequestBody RegisterForm registerForm){
        passportService.createAccount(registerForm);

        //返回重定向地址
        return ToWeb.buildResult()
                .putExtra("backToUrl", registerForm.getBackToUrl());
    }
}
