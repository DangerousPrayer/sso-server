package com.jerryl.auth.service;

import com.jerryl.auth.dao.model.AuthHost;

/**
 * Created by liuruijie on 2017/5/11.
 */
public interface AuthHostService extends PageService<AuthHost>{
    void initAuthHost(AuthHost authHost);
}
