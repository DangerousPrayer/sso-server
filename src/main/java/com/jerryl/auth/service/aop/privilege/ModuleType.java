package com.jerryl.auth.service.aop.privilege;

/**
 * Created by liuruijie on 2017/4/7.
 * 各个模块，以及对应的id
 */
public enum ModuleType {
    PRIVILEGE_MANAGEMENT(1);

    int value;
    ModuleType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
