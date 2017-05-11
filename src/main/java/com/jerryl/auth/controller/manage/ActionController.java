package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.mapper.ActionMapper;
import com.jerryl.auth.dao.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 * 操作相关
 */
@RestController
@RequestMapping("actions")
public class ActionController {
    @Autowired
    ActionMapper actionMapper;

    //获取所有操作
    @GetMapping
    public Object selectList(){
        List<Action> actions = actionMapper.findAll();
        return ToWeb.buildResult().setObjData(actions);
    }
}
