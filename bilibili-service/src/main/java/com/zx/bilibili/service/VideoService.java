package com.zx.bilibili.service;

import com.zx.bilibili.domain.CollectionGroup;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoCollection;
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

    void likeVideo(Long userId, Long videoId);

    void unlikeVideo(Long userId, Long videoId);

    int likeCount(Long videoId);

    boolean likeStatus(Long userId, Long videoId);

    void collectVideo(Long userId, Long videoId, Long[] groupId);

    void uncollectVideo(Long userId, Long videoId, Long[] groupId);

    int collectCount(Long videoId);

    List<CollectionGroup> listAllCollectionGroup(Long userId);

    List<VideoCollection> listVideoCollection(Long userId, Long videoId);

    void addCoin(Long userId, Long videoId, Integer coin);

    int listVideoCoinAmount(Long videoId);
}
