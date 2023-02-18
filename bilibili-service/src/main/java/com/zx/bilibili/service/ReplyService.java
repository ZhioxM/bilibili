package com.zx.bilibili.service;

import com.zx.bilibili.bo.VideoRootCommentBO;
import com.zx.bilibili.domain.VideoComment;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 14:27
 */
public interface ReplyService {
    List<VideoRootCommentBO> listVideoRootComment(Long videoId, Integer pageNum, Integer pageSize, Integer orderBy);

    List<VideoComment> listVideoSubComment(Long videoId, Long commentId, Integer pageNum, Integer pageSize);

    void addVideoComment(VideoComment videoComment);
}
