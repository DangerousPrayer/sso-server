package com.jerryl.auth.service;

import com.jerryl.auth.dao.Page;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.UserInfo;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/17.
 * 用户相关接口
 * ，继承PageService接口是为了获取到默认的分页实现
 */
public interface UserService extends PageService<UserInfo>{
    //根据昵称查询用户
    Page<UserInfo> selectByNickName(PageRequest request, String nickName);
}
