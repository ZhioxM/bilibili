package com.zx.bilibili.service;

import com.zx.bilibili.bo.VideoBo;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoCoin;
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

    void collectVideo(Long userId, Long videoId, Long[] groupId);

    void uncollectVideo(Long userId, Long videoId, Long[] groupId);

    int collectCount(Long videoId);

    List<VideoCollection> listVideoCollection(Long userId, Long videoId);

    void addCoin(Long userId, Long videoId, Integer coin);

    int queryVideoCoinAmount(Long videoId);

    VideoCoin queryVideoCoin(Long userId, Long videoId);

    Video queryVideoById(Long videoId);

    /**
     * 按最近收藏进行查找
     */
    List<Video> listVideoByGroupIdOrderByCollectionTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area);

    /**
     * 按最多播放进行查找
     */
    List<Video> listVideoByGroupIdOrderByPlayAmount(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area);

    /**
     * 按最新投稿进行查找
     */
    List<Video> listVideoByGroupIdOrderByUploadTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area);

    VideoBo queryVideoDetail(Long videoId);
}
