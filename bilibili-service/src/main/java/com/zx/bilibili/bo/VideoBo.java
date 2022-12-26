package com.zx.bilibili.bo;

import com.zx.bilibili.domain.Tag;
import com.zx.bilibili.domain.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 20:42
 */
public class VideoBo {
    private Long id;

    private Long userId;

    private String url;

    private String thumbnail;

    private String title;

    private String type;

    private String duration;

    private String area;

    private Date uploadTime;

    private List<Tag> tags;

    private UserInfo userInfo;

    public VideoBo() {
    }

    public VideoBo(Long id, Long userId, String url, String thumbnail, String title, String type, String duration, String area, Date uploadTime) {
        this.id = id;
        this.userId = userId;
        this.url = url;
        this.thumbnail = thumbnail;
        this.title = title;
        this.type = type;
        this.duration = duration;
        this.area = area;
        this.uploadTime = uploadTime;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
