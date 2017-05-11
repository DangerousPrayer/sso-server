package com.jerryl.auth.service.impl;

import com.jerryl.auth.Config;
import com.jerryl.auth.common.Status;
import com.jerryl.auth.common.exception.BaseException;
import com.jerryl.auth.dao.mapper.*;
import com.jerryl.auth.dao.model.Passport;
import com.jerryl.auth.dao.model.UserInfo;
import com.jerryl.auth.service.PassportService;
import com.jerryl.auth.service.dto.RegisterForm;
import com.jerryl.auth.service.dto.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 */
@Service
public class PassportImpl implements PassportService {
    @Autowired
    PassportMapper passportMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserPrivilegeMapper userPrivilegeMapper;

    @Override
    public SessionUser loginAsSessionUser(String passportId, String password) {
        SessionUser sessionUser = passportMapper.findForLogin(passportId, password);
        if(sessionUser==null){
            throw new BaseException("用户名或密码错误！", Status.FAIL);
        }
        List<Integer> roleIds = roleMapper.findIdsByPassport(passportId);
        sessionUser.setRoleIds(roleIds);
        return sessionUser;
    }

    @Transactional
    @Override
    public void createAccount(RegisterForm registerForm) {
        String s = passportMapper.checkExist(registerForm.getAccount());
        if (s!=null){
            throw new BaseException("账号已存在！", Status.FAIL);
        }

        //创建账号
        Passport passport = new Passport();
        passport.setId(registerForm.getAccount());
        passport.setPassword(registerForm.getPassword());
        passportMapper.insertOne(passport);

        //绑定初始角色和组
        userPrivilegeMapper.bindPassportRole(passport.getId(), Config.ROLE_USER_ID);

        //用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setPassportId(passport.getId());
        userInfo.setNickName(registerForm.getNickName());
        userInfo.setPhone(registerForm.getPhone());
        userInfo.setEmail(registerForm.getEmail());
        userMapper.insertOne(userInfo);
    }

    @Override
    public String createToken(SessionUser user) {
        MessageDigest md    = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] array = md.digest((System.currentTimeMillis()
                +user.getPassportId())
                .getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        String token = "token:"+encoder.encode(array);
        System.out.println(token);
        return token;
    }
}
