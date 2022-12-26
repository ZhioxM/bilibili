package com.zx.bilibili.bo;

import com.zx.bilibili.domain.VideoComment;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 16:03
 */
public class VideoRootCommentBO {
    // 顶级评论
    private VideoComment rootComment;

    // 总回复数
    private Integer totalReply;

    // 前K条回复
    private List<VideoComment> topKSubComment;

    public VideoRootCommentBO() {
    }

    public VideoRootCommentBO(VideoComment rootComment, Integer totalReply, List<VideoComment> topKSubComment) {
        this.rootComment = rootComment;
        this.totalReply = totalReply;
        this.topKSubComment = topKSubComment;
    }

    public VideoComment getRootComment() {
        return rootComment;
    }

    public void setRootComment(VideoComment rootComment) {
        this.rootComment = rootComment;
    }

    public Integer getTotalReply() {
        return totalReply;
    }

    public void setTotalReply(Integer totalReply) {
        this.totalReply = totalReply;
    }

    public List<VideoComment> getTopKSubComment() {
        return topKSubComment;
    }

    public void setTopKSubComment(List<VideoComment> topKSubComment) {
        this.topKSubComment = topKSubComment;
    }
}
