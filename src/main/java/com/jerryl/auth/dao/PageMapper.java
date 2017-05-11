package com.jerryl.auth.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/17.
 * 对于需要映射分页sql的mapper
 * ，给出一个规范
 */
public interface PageMapper<T> {
    List<T> findAll(@Param("page") PageRequest request);
    Long countAll();
}
