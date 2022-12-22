package com.zx.bilibili.service;

import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:13
 */
public interface VideoService {
    void addVideo(Video video, List<VideoTag> tags);

    List<Video> listVideos(Integer pageNum, Integer pageSize, String area);

    void loadVideo(HttpServletRequest request, HttpServletResponse response, String url);
}
