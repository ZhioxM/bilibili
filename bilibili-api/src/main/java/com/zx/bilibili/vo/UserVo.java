package com.zx.bilibili.vo;

import com.zx.bilibili.domain.UserInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 23:05
 */
public class UserVo {
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户信息")
    private UserInfo userInfo;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
