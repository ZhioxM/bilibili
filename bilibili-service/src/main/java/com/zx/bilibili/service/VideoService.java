package com.zx.bilibili.service;

import com.zx.bilibili.domain.Tag;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoTag;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:13
 */
public interface VideoService {
    void addVideo(Video video, List<VideoTag> tags);

    List<Video> listVideos(Integer pageNum, Integer pageSize, String area);
}
