package com.jerryl.auth.controller.manage;

import com.jerryl.auth.common.ToWeb;
import com.jerryl.auth.dao.Page;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.AuthHostMapper;
import com.jerryl.auth.dao.model.AuthHost;
import com.jerryl.auth.service.AuthHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liuruijie on 2017/5/11.
 */
@RestController
@RequestMapping("auth-hosts")
public class AuthHostController implements RestServiceController<AuthHost, Integer>{
    @Autowired
    AuthHostMapper authHostMapper;
    @Autowired
    AuthHostService authHostService;

    @Override
    public Object getOne(@PathVariable("id") Integer integer) {
        return null;
    }

    @Override
    public Object getList(@RequestParam(value = "rowSize", required = false, defaultValue = "100") Integer rowSize
            , @RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
        Page<AuthHost> authHostPage = authHostService.selectPage(new PageRequest(page, rowSize));

        return ToWeb.buildResult().setRows(ToWeb.Rows.buildRows()
                .setCurrent(page)
                .setTotalPages(authHostPage.getTotalPages())
                .setTotalRows(authHostPage.getTotalRows())
                .setList(authHostPage.getRows())
                .setRowSize(rowSize)
        );
    }

    @Override
    public Object postOne(@RequestBody AuthHost entity) {
        authHostService.initAuthHost(entity);
        return ToWeb.buildResult().msg(entity.getHostAuthKey());
    }

    @Override
    public Object putOne(@PathVariable("id") Integer integer, @RequestBody AuthHost entity) {
        return null;
    }

    @Override
    public Object patchOne(@PathVariable("id") Integer integer, @RequestBody AuthHost entity) {
        return null;
    }

    @Override
    public Object deleteOne(@PathVariable("id") Integer integer) {
        return null;
    }
}
