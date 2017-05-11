package com.jerryl.auth.service.dto;

/**
 * Created by liuruijie on 2017/4/7.
 */
public class RegisterForm {
    private String account;
    private String password;
    private String nickName;
    private String email;
    private String phone;
    private String backToUrl;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBackToUrl() {
        return backToUrl;
    }

    public void setBackToUrl(String backToUrl) {
        this.backToUrl = backToUrl;
    }
}
