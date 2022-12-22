package com.zx.bilibili.service.impl;

import com.github.pagehelper.PageHelper;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoExample;
import com.zx.bilibili.domain.VideoTag;
import com.zx.bilibili.mapper.VideoMapper;
import com.zx.bilibili.mapper.VideoTagMapper;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:20
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    private VideoTagMapper videoTagMapper;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Override
    @Transactional
    public void addVideo(Video video, List<VideoTag> tags) {
        Date now = new Date();
        video.setCreateTime(now);
        videoMapper.insertSelective(video);

        for (VideoTag videoTag : tags) {
            videoTag.setVideoId(video.getId());
            videoTag.setCreateTime(now);
            videoTagMapper.insert(videoTag);
        }
    }

    @Override
    public List<Video> listVideos(Integer pageNum, Integer pageSize, String area) {
        PageHelper.startPage(pageNum, pageSize);
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andAreaEqualTo(area);
        return videoMapper.selectByExample(videoExample);
    }

    @Override
    public void loadVideo(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            fastDFSUtil.viewVideoOnlineBySlices(request, response, url);
        } catch (Exception ignored) {
        }
    }
}
