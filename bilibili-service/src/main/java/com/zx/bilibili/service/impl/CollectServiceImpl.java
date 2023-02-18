package com.zx.bilibili.service.impl;

import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoCollection;
import com.zx.bilibili.domain.VideoCollectionExample;
import com.zx.bilibili.mapper.VideoCollectionMapper;
import com.zx.bilibili.service.CollectService;
import com.zx.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 17:54
 */
public class CollectServiceImpl implements CollectService {
    @Autowired
    private VideoCollectionMapper videoCollectionMapper;

    @Autowired
    private VideoService videoService;

    @Override
    public List<Video> listVideoInCollectionFolderByCollectionTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area) {
        VideoCollectionExample videoCollectionExample = new VideoCollectionExample();
        videoCollectionExample.createCriteria().andUserIdEqualTo(userId).andGroupIdEqualTo(groupId);
        videoCollectionExample.setOrderByClause("createTime DESC"); // 按时间收藏降序排序
        List<VideoCollection> videoCollections = videoCollectionMapper.selectByExample(videoCollectionExample);
        // 当前收藏夹下的所有视频ID
        List<Long> videoIds = videoCollections.stream().map(VideoCollection::getVideoId).collect(Collectors.toList());
        // return videoService.listVideoByIds(pageNum, pageSize, area, videoIds);
        return null;
    }

    @Override
    public List<Video> listVideoInCollectionFolderByPlayAmount(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area) {
        return null;
    }

    @Override
    public List<Video> listVideoInCollectionFolderBySubmitTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area) {
        return null;
    }
}
