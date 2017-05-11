package com.jerryl.auth.service;

import com.jerryl.auth.dao.Page;
import com.jerryl.auth.dao.PageMapper;
import com.jerryl.auth.dao.PageRequest;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/17.
 * 基本分页的默认实现
 */
public abstract class PageServiceAdapter<T> implements PageService<T> {
    //此mapper由子类给出
    protected abstract PageMapper<T> getMapper();

    //默认实现，无where条件
    public Page<T> selectPage(PageRequest request){
        PageMapper<T> pageMapper = getMapper();
        List<T> list = pageMapper.findAll(request);
        Long count = pageMapper.countAll();

        return afterSelect(request.getSize(), list, count);
    }

    //在查询之后，创建page结果对象
    protected Page<T> afterSelect(int size, List<T> list, long count){
        Page<T> page = new Page<T>();
        page.setRows(list);
        page.setTotalPages((int) (count/size+1));
        page.setTotalRows(count);
        return page;
    }
}
