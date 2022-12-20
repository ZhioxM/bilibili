package com.zx.bilibili.vo;

import com.zx.bilibili.domain.UserInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 用户粉丝VO
 * @Author: Mzx
 * @Date: 2022/12/18 22:03
 */
public class UserFollowedVo {
    private Long userId; // 应该叫followedId才对，但是为了与前端匹配。没办法
    private UserInfoVo userInfoVo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserInfoVo getUserInfoVo() {
        return userInfoVo;
    }

    public void setUserInfoVo(UserInfoVo userInfoVo) {
        this.userInfoVo = userInfoVo;
    }
}
