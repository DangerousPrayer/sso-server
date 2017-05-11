package com.jerryl.auth.service;

import com.jerryl.auth.dao.Page;
import com.jerryl.auth.dao.PageMapper;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.UserMapper;
import com.jerryl.auth.dao.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/17.
 * 用户相关接口实现
 * 继承PageServiceAdapter，获取默认分页实现
 */
@Service
public class UserServiceImpl extends PageServiceAdapter<UserInfo> implements UserService{
    @Autowired
    UserMapper userMapper;

    //提供userMapper接口
    @Override
    protected PageMapper<UserInfo> getMapper() {
        return userMapper;
    }

    //根据昵称查询用户
    @Override
    public Page<UserInfo> selectByNickName(PageRequest request, String nickName) {
        List<UserInfo> list = userMapper.findByNickName(request, nickName);
        long count = userMapper.countByNickName(nickName);

        return afterSelect(request.getSize(), list, count);
    }
}
