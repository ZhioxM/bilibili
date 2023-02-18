package com.zx.bilibili.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.zx.bilibili.bo.VideoRootCommentBO;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoComment;
import com.zx.bilibili.domain.VideoCommentExample;
import com.zx.bilibili.mapper.VideoCommentMapper;
import com.zx.bilibili.service.ReplyService;
import com.zx.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 14:30
 */
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private VideoCommentMapper videoCommentMapper;

    @Autowired
    private VideoService videoService;

    @Override
    public void addVideoComment(VideoComment videoComment) {
        Long videoId = videoComment.getVideoId();
        if (ObjUtil.isNull(videoId)) {
            throw new CommonException("参数异常");
        }
        Video video = videoService.queryVideoById(videoId);
        if (ObjUtil.isNull(video)) {
            throw new CommonException("非法视频");
        }
        videoComment.setCreateTime(new Date());
        videoCommentMapper.insertSelective(videoComment);
    }

    @Override
    public List<VideoRootCommentBO> listVideoRootComment(Long videoId, Integer pageNum, Integer pageSize, Integer orderBy) {
        Video video = videoService.queryVideoById(videoId);
        if (ObjUtil.isNull(video)) {
            throw new CommonException("非法视频");
        }
        PageHelper.startPage(pageNum, pageSize);
        VideoCommentExample rootEx = new VideoCommentExample();
        rootEx.createCriteria().andVideoIdEqualTo(videoId).andRootIdIsNull();
        if (orderBy == 0) {
            // 按时间排序
            rootEx.setOrderByClause("createTime DESC");
        } else {
            // 按热度排序
            rootEx.setOrderByClause("likeCount DESC");
        }
        // 顶级评论
        List<VideoComment> rootComments = videoCommentMapper.selectByExample(rootEx);
        List<VideoRootCommentBO> result = new ArrayList<>(rootComments.size());
        // 顶级评论的子评论个数
        for (VideoComment rootComment : rootComments) {
            Long rootId = rootComment.getId();
            VideoCommentExample countExample = new VideoCommentExample();
            countExample.createCriteria().andVideoIdEqualTo(videoId).andRootIdEqualTo(rootId);
            // 查询顶级评论的回复条数
            int total = videoCommentMapper.countByExample(countExample);
            // 查询最新发布的的三条回复
            List<VideoComment> topK = videoCommentMapper.selectTokSubComment(rootId, 3);
            VideoRootCommentBO bo = new VideoRootCommentBO(rootComment, total, topK);
            result.add(bo);
        }
        return result;
    }

    @Override
    public List<VideoComment> listVideoSubComment(Long videoId, Long commentId, Integer pageNum, Integer pageSize) {
        // 分页查询回复
        PageHelper.startPage(pageNum, pageSize);
        VideoCommentExample videoCommentExample = new VideoCommentExample();
        videoCommentExample.createCriteria().andRootIdEqualTo(commentId);
        videoCommentExample.setOrderByClause("create_time ASC"); // 时间升序
        return videoCommentMapper.selectByExample(videoCommentExample);
    }
}
