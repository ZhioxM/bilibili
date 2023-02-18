package com.zx.bilibili.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoLike;
import com.zx.bilibili.domain.VideoLikeExample;
import com.zx.bilibili.mapper.VideoLikeMapper;
import com.zx.bilibili.service.LikeService;
import com.zx.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Mzx
 * @Date: 2023/2/18 15:13
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private VideoLikeMapper videoLikeMapper;

    @Autowired
    private VideoService videoService;

    @Override
    public void likeVideo(Long userId, Long videoId) {
        Video db = videoService.queryVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("非法视频");
        }

        VideoLikeExample videoLikeExample = new VideoLikeExample();
        videoLikeExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        int i = videoLikeMapper.countByExample(videoLikeExample);
        if (i != 0) {
            throw new CommonException("视频已点过赞");
        }

        VideoLike videoLike = new VideoLike();
        videoLike.setUserId(userId);
        videoLike.setVideoId(videoId);
        videoLike.setCreateTime(new Date());
        videoLikeMapper.insert(videoLike);
    }

    @Override
    public void unlikeVideo(Long userId, Long videoId) {
        VideoLikeExample videoLikeExample = new VideoLikeExample();
        videoLikeExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        videoLikeMapper.deleteByExample(videoLikeExample);
    }

    @Override
    public int likeCount(Long videoId) {
        VideoLikeExample videoLikeExample = new VideoLikeExample();
        videoLikeExample.createCriteria().andVideoIdEqualTo(videoId);
        return videoLikeMapper.countByExample(videoLikeExample);
    }

    @Override
    public boolean likeStatus(Long userId, Long videoId) {
        VideoLikeExample videoLikeExample = new VideoLikeExample();
        videoLikeExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        int i = videoLikeMapper.countByExample(videoLikeExample);
        return i == 1;
    }
}
