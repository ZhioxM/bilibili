package com.zx.bilibili.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Mzx
 * @Date: 2022/12/18 23:13
 */
public class UserInfoVo {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别：0男 1女 2未知")
    private String gender;
    @ApiModelProperty(value = "生日")
    private String birth;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "是否回关")
    private Boolean followed;

    public UserInfoVo() {
    }

    public UserInfoVo(Long id, Long userId, String nick, String avatar, String gender, String birth, String sign, Boolean followed) {
        this.id = id;
        this.userId = userId;
        this.nick = nick;
        this.avatar = avatar;
        this.gender = gender;
        this.birth = birth;
        this.sign = sign;
        this.followed = followed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }
}
