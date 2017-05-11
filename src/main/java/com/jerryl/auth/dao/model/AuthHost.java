package com.jerryl.auth.dao.model;

import java.util.Date;

/**
 * Created by liuruijie on 2017/5/11.
 */
public class AuthHost {
    private Integer hostId;
    private String hostAuthKey;
    private String hostIp;
    private Date authTime;
    private Byte status;

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getHostAuthKey() {
        return hostAuthKey;
    }

    public void setHostAuthKey(String hostAuthKey) {
        this.hostAuthKey = hostAuthKey;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
