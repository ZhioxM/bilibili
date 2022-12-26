package com.zx.bilibili.vo;

import com.zx.bilibili.domain.Tag;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 20:21
 */
public class VideoDetailVo {
    private VideoVo videoVo;

    private List<Tag> tags;
    private UserInfoVo userInfo;

    public VideoDetailVo() {
    }

    public VideoVo getVideoVo() {
        return videoVo;
    }

    public void setVideoVo(VideoVo videoVo) {
        this.videoVo = videoVo;
    }

    public UserInfoVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
