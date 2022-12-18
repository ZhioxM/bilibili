package com.zx.bilibili.vo;

import com.zx.bilibili.domain.UserInfo;

/**
 * 用户粉丝VO
 * @Author: Mzx
 * @Date: 2022/12/18 22:03
 */
public class UserFollowedVo {
    private Long userId; // 应该叫followedId才对，但是为了与前端匹配。没办法

    private UserInfo userInfo;

    private boolean followed; // 是否回关

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    @Override
    public String toString() {
        return "UserFollowedVo{" +
                "followingId=" + userId +
                ", userInfo=" + userInfo +
                ", followed=" + followed +
                '}';
    }
}
