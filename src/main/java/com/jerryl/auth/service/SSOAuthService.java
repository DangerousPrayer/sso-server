package com.jerryl.auth.service;

import java.io.IOException;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/8.
 */
public interface SSOAuthService {
    /**
     * 通过token获取用户信息，从而进行权限验证
     * @param token 票据
     * @param moduleKey 模块
     * @param actionId 操作
     * @return 是否权限通过
     */
    void checkPrivilege(String token, String moduleKey, Integer actionId) throws IOException;

    /**
     * 注册系统模块
     * @param hostAuthKey 认证key，用于确定host
     * @param metaDataUrls 返回模块元信息的url
     */
    void registerHost(String hostAuthKey, List<String> metaDataUrls);
}
