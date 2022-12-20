package com.zx.bilibili.vo;

import com.zx.bilibili.domain.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * 用户关注VO
 * @Author: Mzx
 * @Date: 2022/12/18 20:37
 */
public class UserFollowingVo {
    private Long id; // 原本起名叫groupId, 为了与前端匹配，所以保留原本的

    private Long userId; // 这是有用的，与用户自定义分组有关

    private String name; // 原本起名叫groupName, 为了与前端匹配

    private String type; // 原本起名叫groupType, 为了与前端配对

    private Date createTime; // 感觉这两个时间字段用不上

    private Date updateTime;

    private List<UserInfo> followingUserInfoList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<UserInfo> getFollowingUserInfoList() {
        return followingUserInfoList;
    }

    public void setFollowingUserInfoList(List<UserInfo> followingUserInfoList) {
        this.followingUserInfoList = followingUserInfoList;
    }

    @Override
    public String toString() {
        return "UserFollowingVo{" +
                "groupId=" + id +
                ", userId=" + userId +
                ", groupName='" + name + '\'' +
                ", groupType='" + type + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", followingUserInfoList=" + followingUserInfoList +
                '}';
    }
}
