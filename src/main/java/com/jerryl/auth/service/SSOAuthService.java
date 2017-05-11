package com.jerryl.auth.service;

import java.io.IOException;

/**
 * Created by liuruijie on 2017/4/8.
 */
public interface SSOAuthService {
    /**
     * 通过token获取用户信息，从而进行权限验证
     * @param token 票据
     * @param moduleId 模块
     * @param actionId 操作
     * @return 是否权限通过
     */
    void checkPrivilege(String token, Integer moduleId, Integer actionId) throws IOException;
}
