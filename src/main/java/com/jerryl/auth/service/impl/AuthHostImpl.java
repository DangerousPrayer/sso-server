package com.jerryl.auth.service.impl;

import com.jerryl.auth.Config;
import com.jerryl.auth.dao.PageMapper;
import com.jerryl.auth.dao.mapper.AuthHostMapper;
import com.jerryl.auth.dao.model.AuthHost;
import com.jerryl.auth.service.AuthHostService;
import com.jerryl.auth.service.PageServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * Created by liuruijie on 2017/5/11.
 */
@Service
public class AuthHostImpl extends PageServiceAdapter<AuthHost> implements AuthHostService{
    @Autowired
    AuthHostMapper authHostMapper;

    @Override
    protected PageMapper<AuthHost> getMapper() {
        return authHostMapper;
    }

    @Override
    public void initAuthHost(AuthHost authHost) {
        String s = DigestUtils.md5DigestAsHex(
                (authHost.getHostIp()+Config.HOST_AUTH_INIT_KEY)
                .getBytes());
        authHost.setHostAuthKey(s);
        authHostMapper.insertOne(authHost);
    }
}
