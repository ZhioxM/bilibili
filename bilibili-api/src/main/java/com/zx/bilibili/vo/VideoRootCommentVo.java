package com.zx.bilibili.vo;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 16:35
 */
public class VideoRootCommentVo {
    // 评论ID
    private Long id;

    private Long videoId;

    private Long userId;

    private Long replyUserId;

    private Date createTime;

    private String context;

    private Integer totalReply;

    private List<VideoCommentVo> reply;

    public VideoRootCommentVo() {
    }

    public VideoRootCommentVo(Long id, Long videoId, Long userId, Long replyUserId, Date createTime, String context, Integer totalReply) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
        this.replyUserId = replyUserId;
        this.createTime = createTime;
        this.context = context;
        this.totalReply = totalReply;
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getTotalReply() {
        return totalReply;
    }

    public void setTotalReply(Integer totalReply) {
        this.totalReply = totalReply;
    }

    public List<VideoCommentVo> getReply() {
        return reply;
    }

    public void setReply(List<VideoCommentVo> reply) {
        this.reply = reply;
    }
}
