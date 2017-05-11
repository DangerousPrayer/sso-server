package com.jerryl.auth.dao.model;

/**
 * Created by liuruijie on 2017/5/11.
 */
public class ModuleRegister{
    private Integer registerId;
    private String metaDataUrl;
    private Integer hostId;
    private String hostAuthKey;
    private Byte status;

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public String getMetaDataUrl() {
        return metaDataUrl;
    }

    public void setMetaDataUrl(String metaDataUrl) {
        this.metaDataUrl = metaDataUrl;
    }

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
