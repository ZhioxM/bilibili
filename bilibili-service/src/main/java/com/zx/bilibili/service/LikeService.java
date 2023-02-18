package com.zx.bilibili.service;


public interface LikeService {
    void likeVideo(Long userId, Long videoId);

    void unlikeVideo(Long userId, Long videoId);

    int likeCount(Long videoId);

    boolean likeStatus(Long userId, Long videoId);
}
