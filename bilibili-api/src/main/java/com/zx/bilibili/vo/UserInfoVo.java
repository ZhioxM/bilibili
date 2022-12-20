package com.zx.bilibili.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author: Mzx
 * @Date: 2022/12/18 23:13
 */
public class UserInfoVo {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;



    public UserInfoVo(Long id, Long userId, String nick, String avatar, String gender, String birth, Date createTime, Date updateTime, String sign, Boolean followed) {
        this.id = id;
        this.userId = userId;
        this.nick = nick;
        this.avatar = avatar;
        this.gender = gender;
        this.birth = birth;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sign = sign;
        this.followed = followed;
    }

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别：0男 1女 2未知")
    private String gender;

    @ApiModelProperty(value = "生日")
    private String birth;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "是否回关")
    private Boolean followed;

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }

    private static final long serialVersionUID = 1L;

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
        this.nick = nick == null ? null : nick.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", nick=").append(nick);
        sb.append(", avatar=").append(avatar);
        sb.append(", gender=").append(gender);
        sb.append(", birth=").append(birth);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", sign=").append(sign);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
