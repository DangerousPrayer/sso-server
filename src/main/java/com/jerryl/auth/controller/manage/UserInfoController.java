package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.PassportMapper;
import com.jerryl.auth.dao.mapper.UserMapper;
import com.jerryl.auth.dao.model.UserInfo;
import com.jerryl.auth.service.aop.privilege.ActionType;
import com.jerryl.auth.service.aop.privilege.ModuleType;
import com.jerryl.auth.service.aop.privilege.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 * 用户相关
 */
@RestController
@RequestMapping("users")
public class UserInfoController implements RestServiceController<UserInfo, Long>{
    @Autowired
    UserMapper userMapper;
    @Autowired
    PassportMapper passportMapper;

    @GetMapping("{id}")
    public Object getOne(@PathVariable("id")Long id){
        UserInfo userInfo = userMapper.findOne(id);
        return ToWeb.buildResult().setObjData(userInfo);
    }

    @GetMapping
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.SHOW)
    public Object getList(Integer page, Integer size){
        if(page==null){
            page = 1;
        }
        if(size==null){
            size = 1000;
        }

        List<UserInfo> userInfos = userMapper.findAll(new PageRequest(page, size));
        long total = userMapper.countAll();
        return ToWeb.buildResult()
                .rows(ToWeb.Rows.buildRows()
                .setTotalRows(total)
                .setCurrent(page)
                .setList(userInfos)
                .setRowSize(size)
                .setTotalPages((int) (total/size+1)));
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.ADD)
    public Object postOne(@RequestBody UserInfo entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object putOne(@PathVariable("id") Long id, @RequestBody UserInfo entity) {
        entity.setId(id);
        userMapper.updateOne(entity);
        return ToWeb.buildResult().msg("修改成功").refresh();
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.EDIT)
    public Object patchOne(@PathVariable("id") Long id, @RequestBody UserInfo entity) {
        return null;
    }

    @Override
    @Privilege(moduleType = ModuleType.PRIVILEGE_MANAGEMENT
            , actionType = ActionType.DEL)
    public Object deleteOne(@PathVariable("id") Long id) {
        userMapper.deleteOne(id);
        return ToWeb.buildResult().refresh();
    }
}
