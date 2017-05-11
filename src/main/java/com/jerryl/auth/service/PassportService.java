package com.jerryl.auth.service;

import com.jerryl.auth.service.dto.RegisterForm;
import com.jerryl.auth.service.dto.SessionUser;

/**
 * Created by liuruijie on 2017/4/7.
 * 账号服务
 */
public interface PassportService {
    /**
     * 登陆
     */
    SessionUser loginAsSessionUser(String passportId, String password);

    /**
     * 注册
     */
    void createAccount(RegisterForm registerForm);

    /**
     * 创建token票据
     */
    String createToken(SessionUser user);
}
