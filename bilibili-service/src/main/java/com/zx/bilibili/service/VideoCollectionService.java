package com.zx.bilibili.service;

import com.zx.bilibili.domain.Video;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 17:53
 */
public interface VideoCollectionService {
    /**
     * 按最近收藏进行查找
     */
    List<Video> listVideoInCollectionFolderByCollectionTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area);

    /**
     * 按最多播放进行查找
     */
    List<Video> listVideoInCollectionFolderByPlayAmount(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area);

    /**
     * 按最新投稿进行查找
     */
    List<Video> listVideoInCollectionFolderBySubmitTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area);


}
