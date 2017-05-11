package com.jerryl.auth.service.impl;

import com.jerryl.auth.common.Status;
import com.jerryl.auth.common.exception.BaseException;
import com.jerryl.auth.service.PrivilegeService;
import com.jerryl.auth.service.SSOAuthService;
import com.jerryl.auth.service.dto.SessionUser;
import com.jerryl.auth.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created by liuruijie on 2017/4/8.
 */
@Service
public class SSOAuthImpl implements SSOAuthService{
    @Autowired
    PrivilegeService privilegeService;

    /**
     * 查询数据库，校验用户权限
     * @param token 票据
     * @param moduleId 模块
     * @param actionId 操作
     * @throws IOException
     */
    @Override
    public void checkPrivilege(String token, Integer moduleId, Integer actionId) throws IOException {
        //解码token
        BASE64Decoder decoder = new BASE64Decoder();
        token = new String(decoder.decodeBuffer(token));
        //用token获取用户信息
        SessionUser user = (SessionUser) RedisUtil.get(token);
        if (user==null){
            throw new BaseException("haven't login yet!", Status.NO_LOGIN);
        }
        //查询数据库校验权限
        if(!privilegeService.checkPrivilege(user
                , moduleId
                , actionId)){
            throw new BaseException("access denied!", Status.NO_PRIVILEGE);
        }
    }
}
