package com.jerryl.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jerryl.auth.common.Status;
import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.common.exception.BaseException;
import com.jerryl.auth.dao.mapper.AuthHostMapper;
import com.jerryl.auth.dao.mapper.ModuleMapper;
import com.jerryl.auth.dao.mapper.ModuleRegisterMapper;
import com.jerryl.auth.dao.model.AuthHost;
import com.jerryl.auth.dao.model.Module;
import com.jerryl.auth.dao.model.ModuleRegister;
import com.jerryl.auth.service.PrivilegeService;
import com.jerryl.auth.service.SSOAuthService;
import com.jerryl.auth.service.dto.SessionUser;
import com.jerryl.auth.util.HttpUtil;
import com.jerryl.auth.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/8.
 */
@Service
public class SSOAuthImpl implements SSOAuthService{
    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    ModuleRegisterMapper moduleRegisterMapper;
    @Autowired
    AuthHostMapper authHostMapper;
    @Autowired
    ModuleMapper moduleMapper;

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

    @Override
    public void registerHost(String hostAuthKey, List<String> metaDataUrls) {
        AuthHost authHost = authHostMapper.selectByAuthKey(hostAuthKey);
        if(authHost==null){
            throw new BaseException("wrong host-auth-key!", Status.FAIL);
        }

        Integer hostId = authHost.getHostId();
        registerModule(hostId, metaDataUrls);

        for (String url: metaDataUrls){
            HttpUtil.RequestConfig config = new HttpUtil.RequestConfig(url, "GET");
            HttpUtil.sendRequest(config, new HttpUtil.ResponseListener() {
                @Override
                public void afterResponse(String webResult) {
                    Module module = JSON.parseObject(webResult, Module.class);
                    moduleMapper.insertOne(module);
                    ModuleRegister register = new ModuleRegister();
                    register.setStatus((byte) 1);
                    moduleRegisterMapper.updateOne(register);
                    if(moduleRegisterMapper.checkCompleted(hostId)!=null){
                        AuthHost host = new AuthHost();
                        host.setStatus((byte) 1);
                        host.setAuthTime(new Date());
                        authHostMapper.updateOne(host);
                    }
                }
            }, true);
        }
    }

    @Transactional
    protected void registerModule(Integer hostId, List<String> metaDataUrls){
        for(String metaDataUrl: metaDataUrls){
            moduleRegisterMapper.insertOne(metaDataUrl, hostId);
        }
    }
}
