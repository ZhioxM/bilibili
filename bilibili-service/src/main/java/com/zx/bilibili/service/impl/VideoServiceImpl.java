package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.domain.*;
import com.zx.bilibili.mapper.*;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:20
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private VideoLikeMapper videoLikeMapper;

    @Autowired
    private VideoCollectionMapper videoCollectionMapper;

    @Autowired
    private CollectionGroupMapper collectionGroupMapper;

    @Autowired
    private VideoCoinMapper videoCoinMapper;

    @Autowired
    private UserCoinMapper userCoinMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

    @Override
    public void likeVideo(Long userId, Long videoId) {
        Video db = getVideoById(videoId);
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

    @Override
    public void collectVideo(Long userId, Long videoId, Long[] groupId) {
        Video db = getVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("非法视频");
        }

        // 同一个视频，用户可以收藏在多个不同的文件夹下
        // 判断分组ID是否正确
        Set<Long> groups = Arrays.stream(groupId).collect(Collectors.toSet());
        CollectionGroupExample collectionGroupExample = new CollectionGroupExample();
        for (Long id : groups) {
            collectionGroupExample.createCriteria().andIdEqualTo(id);
            List<CollectionGroup> groupDb = collectionGroupMapper.selectByExample(collectionGroupExample);
            if (CollectionUtil.isEmpty(groupDb)) {
                throw new CommonException("分组不存在");
            }
            collectionGroupExample.clear();
        }

        // 使用编程式事务，降低事务的粒度
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 删除原有的收藏关系
                try {
                    VideoCollectionExample deleteExample = new VideoCollectionExample();
                    deleteExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
                    videoCollectionMapper.deleteByExample(deleteExample);
                    // 添加新的收藏关系
                    Date now = new Date();
                    for (Long id : groups) {
                        VideoCollection videoCollection = new VideoCollection();
                        videoCollection.setUserId(userId);
                        videoCollection.setVideoId(videoId);
                        videoCollection.setGroupId(id);
                        videoCollection.setCreateTime(now);
                        videoCollectionMapper.insert(videoCollection);
                    }
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void uncollectVideo(Long userId, Long videoId, Long[] groupId) {
        Video db = getVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("非法视频");
        }
        // 删除收藏
        VideoCollectionExample deleteExample = new VideoCollectionExample();
        deleteExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId).andGroupIdIn(Arrays.asList(groupId));
    }

    @Override
    public int collectCount(Long videoId) {
        // Video db = getVideoById(videoId);
        // if(ObjUtil.isNull(db)) {
        //     throw new CommonException("非法视频");
        // }
        VideoCollectionExample ex = new VideoCollectionExample();
        ex.createCriteria().andVideoIdEqualTo(videoId);
        return videoCollectionMapper.countByExample(ex);
    }

    @Override
    public List<CollectionGroup> listAllCollectionGroup(Long userId) {
        CollectionGroupExample ex = new CollectionGroupExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        List<CollectionGroup> customGroup = collectionGroupMapper.selectByExample(ex);

        ex.clear();
        ex.createCriteria().andTypeEqualTo("0");
        List<CollectionGroup> defaultGroup = collectionGroupMapper.selectByExample(ex);
        return CollectionUtil.unionAll(customGroup, defaultGroup);
    }

    @Override
    public List<VideoCollection> listVideoCollection(Long userId, Long videoId) {
        VideoCollectionExample ex = new VideoCollectionExample();
        ex.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        return videoCollectionMapper.selectByExample(ex);
    }

    @Override
    public void addCoin(Long userId, Long videoId, Integer amount) {
        Video db = getVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("非法视频");
        }

        UserCoinExample coinExample = new UserCoinExample();
        coinExample.createCriteria().andUserIdEqualTo(userId);
        UserCoin userCoin = userCoinMapper.selectByExample(coinExample).get(0);
        if (userCoin.getAmount() < amount) {
            throw new CommonException("硬币数不足");
        }

        // 查询之前的投币记录
        VideoCoinExample videoCoinExample = new VideoCoinExample();
        videoCoinExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        List<VideoCoin> videoCoins = videoCoinMapper.selectByExample(videoCoinExample);
        VideoCoin dbCoin = null;
        if (CollectionUtil.isNotEmpty(videoCoins)) {
            dbCoin = videoCoins.get(0);
        }

        VideoCoin finalDb = dbCoin;
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Date now = new Date();
                    if (finalDb == null) {
                        // 插入投币记录
                        VideoCoin videoCoin = new VideoCoin();
                        videoCoin.setVideoId(videoId);
                        videoCoin.setUserId(userId);
                        videoCoin.setAmount(amount);
                        videoCoin.setCreateTime(now);
                        videoCoinMapper.insert(videoCoin);
                    } else {
                        // 更新投币记录
                        finalDb.setAmount(finalDb.getAmount() + amount);
                        finalDb.setUpdateTime(now);
                        videoCoinMapper.updateByPrimaryKeySelective(finalDb);
                    }
                    // 更新用户硬币数
                    userCoin.setAmount(userCoin.getAmount() - amount);
                    userCoinMapper.updateByPrimaryKeySelective(userCoin);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int listVideoCoinAmount(Long videoId) {
        VideoCoinExample videoCoinExample = new VideoCoinExample();
        videoCoinExample.createCriteria().andVideoIdEqualTo(videoId);
        List<VideoCoin> videoCoins = videoCoinMapper.selectByExample(videoCoinExample);
        int amount = 0;
        for (VideoCoin coin : videoCoins) {
            amount += coin.getAmount();
        }
        return amount;
    }

    public Video getVideoById(Long videoId) {
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(videoId);
        List<Video> videos = videoMapper.selectByExample(videoExample);
        if (CollectionUtil.isEmpty(videos)) {
            return null;
        }
        return videos.get(0);
    }
}
