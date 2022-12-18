package com.zx.bilibili.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class VideoBinaryPicture implements Serializable {
    private Long id;

    private Long videoId;

    private Integer frameNo;

    private Long videoTimestamp;

    private Date createTime;

    private String url;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Integer getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(Integer frameNo) {
        this.frameNo = frameNo;
    }

    public Long getVideoTimestamp() {
        return videoTimestamp;
    }

    public void setVideoTimestamp(Long videoTimestamp) {
        this.videoTimestamp = videoTimestamp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", videoId=").append(videoId);
        sb.append(", frameNo=").append(frameNo);
        sb.append(", videoTimestamp=").append(videoTimestamp);
        sb.append(", createTime=").append(createTime);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}