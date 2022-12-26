package com.zx.bilibili.vo;

import java.util.Date;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 20:02
 */
public class VideoCommentVo {
    // 评论ID
    private Long id;

    private Long videoId;

    private Long userId;

    private Long replyUserId;

    // 顶级评论ID
    private Long rootCommentId;

    // 父评论ID
    private Long parentCommentId;

    private Date createTime;

    private String context;

    public VideoCommentVo() {
    }

    public VideoCommentVo(Long id, Long videoId, Long userId, Long replyUserId, Long rootId, Long parentId, Date createTime, String context) {
        this.id = id;
        this.videoId = videoId;
        this.userId = userId;
        this.replyUserId = replyUserId;
        this.rootCommentId = rootId;
        this.parentCommentId = parentId;
        this.createTime = createTime;
        this.context = context;
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

    public Long getRootCommentId() {
        return rootCommentId;
    }

    public void setRootCommentId(Long rootCommentId) {
        this.rootCommentId = rootCommentId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
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
}
