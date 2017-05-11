package com.jerryl.auth.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/7.
 */
public class SessionUser implements Serializable{
    private String passportId;
    private String nickName;
    private List<Integer> roleIds;

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
