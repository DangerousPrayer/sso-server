package com.jerryl.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liuruijie on 2017/4/6.
 */
@Configuration
@MapperScan("com.jerryl.auth.dao.mapper")
public class Cfg_Mybatis {
}
