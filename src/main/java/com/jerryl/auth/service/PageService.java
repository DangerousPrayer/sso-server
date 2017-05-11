package com.jerryl.auth.service;

import com.jerryl.auth.dao.Page;
import com.jerryl.auth.dao.PageRequest;

/**
 * Created by liuruijie on 2017/4/17.
 * 提供基础的分页接口
 */
public interface PageService<T> {
    Page<T> selectPage(PageRequest request);
}
