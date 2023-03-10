package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zx.bilibili.bo.VideoBo;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.constant.VideoAreaConstant;
import com.zx.bilibili.domain.*;
import com.zx.bilibili.mapper.*;
import com.zx.bilibili.service.CoinService;
import com.zx.bilibili.service.UserService;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.service.VideoTagService;
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
    private VideoTagService videoTagService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

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

        // TODO ??????VideoTag??????
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
        if (!StrUtil.equals(area, VideoAreaConstant.ALL_AREA)) {
            videoExample.createCriteria().andAreaEqualTo(area);
        }
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
    public void collectVideo(Long userId, Long videoId, Long[] groupId) {
        Video db = queryVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("????????????");
        }

        // ??????????????????????????????????????????????????????????????????
        // ????????????ID????????????
        Set<Long> groups = Arrays.stream(groupId).collect(Collectors.toSet());
        CollectionGroupExample collectionGroupExample = new CollectionGroupExample();
        for (Long id : groups) {
            collectionGroupExample.createCriteria().andIdEqualTo(id);
            List<CollectionGroup> groupDb = collectionGroupMapper.selectByExample(collectionGroupExample);
            if (CollectionUtil.isEmpty(groupDb)) {
                throw new CommonException("???????????????");
            }
            collectionGroupExample.clear();
        }

        // ?????????????????????????????????????????????
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // ???????????????????????????
                try {
                    VideoCollectionExample deleteExample = new VideoCollectionExample();
                    deleteExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
                    videoCollectionMapper.deleteByExample(deleteExample);
                    // ????????????????????????
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
        Video db = queryVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("????????????");
        }
        // ????????????
        VideoCollectionExample deleteExample = new VideoCollectionExample();
        deleteExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId).andGroupIdIn(Arrays.asList(groupId));
    }

    @Override
    public int collectCount(Long videoId) {
        // Video db = getVideoById(videoId);
        // if(ObjUtil.isNull(db)) {
        //     throw new CommonException("????????????");
        // }
        VideoCollectionExample ex = new VideoCollectionExample();
        ex.createCriteria().andVideoIdEqualTo(videoId);
        return videoCollectionMapper.countByExample(ex);
    }


    @Override
    public List<VideoCollection> listVideoCollection(Long userId, Long videoId) {
        VideoCollectionExample ex = new VideoCollectionExample();
        ex.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        return videoCollectionMapper.selectByExample(ex);
    }

    @Override
    public void addCoin(Long userId, Long videoId, Integer amount) {
        Video db = queryVideoById(videoId);
        if (ObjUtil.isNull(db)) {
            throw new CommonException("????????????");
        }

        UserCoin userCoin = coinService.getCoin(userId);
        if (userCoin.getAmount() < amount) {
            throw new CommonException("???????????????");
        }

        // ???????????????????????????
        VideoCoin finalDb = queryVideoCoin(userId, videoId);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    Date now = new Date();
                    if (finalDb == null) {
                        // ??????????????????
                        VideoCoin videoCoin = new VideoCoin();
                        videoCoin.setVideoId(videoId);
                        videoCoin.setUserId(userId);
                        videoCoin.setAmount(amount);
                        videoCoin.setCreateTime(now);
                        videoCoinMapper.insert(videoCoin);
                    } else {
                        // ??????????????????
                        finalDb.setAmount(finalDb.getAmount() + amount);
                        finalDb.setUpdateTime(now);
                        videoCoinMapper.updateByPrimaryKeySelective(finalDb);
                    }
                    // ?????????????????????
                    coinService.updateCoin(userId, userCoin.getAmount() - amount);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int queryVideoCoinAmount(Long videoId) {
        VideoCoinExample videoCoinExample = new VideoCoinExample();
        videoCoinExample.createCriteria().andVideoIdEqualTo(videoId);
        List<VideoCoin> videoCoins = videoCoinMapper.selectByExample(videoCoinExample);
        int amount = 0;
        for (VideoCoin coin : videoCoins) {
            amount += coin.getAmount();
        }
        return amount;
    }

    @Override
    public VideoCoin queryVideoCoin(Long userId, Long videoId) {
        VideoCoinExample videoCoinExample = new VideoCoinExample();
        videoCoinExample.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        List<VideoCoin> videoCoins = videoCoinMapper.selectByExample(videoCoinExample);
        if (CollectionUtil.isEmpty(videoCoins)) {
            return null;
        }
        return videoCoins.get(0);
    }

    @Override
    public Video queryVideoById(Long videoId) {
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(videoId);
        List<Video> videos = videoMapper.selectByExample(videoExample);
        if (CollectionUtil.isEmpty(videos)) {
            return null;
        }
        return videos.get(0);
    }

    @Override
    public List<Video> listVideoByGroupIdOrderByCollectionTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area) {
        int start = (pageNum - 1) * pageSize;
        int end = pageSize;
        return videoMapper.selectByGroupIdAndAreaOrderByCollectionTime(userId, groupId, start, end, area);
    }

    @Override
    public List<Video> listVideoByGroupIdOrderByPlayAmount(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area) {
        // ??????ID???????????????????????????????????????????????????userId
        int start = (pageNum - 1) * pageSize;
        int end = pageSize;
        // TODO ?????????????????????
        return null;
    }

    @Override
    public List<Video> listVideoByGroupIdOrderByUploadTime(Long userId, Long groupId, Integer pageNum, Integer pageSize, String area) {
        int start = (pageNum - 1) * pageSize;
        int end = pageSize;
        return videoMapper.selectByGroupIdAndAreaOrderByUploadTime(userId, groupId, start, end, area);
    }

    @Override
    public VideoBo queryVideoDetail(Long videoId) {
        Video video = queryVideoById(videoId);
        VideoBo bo = new VideoBo(video.getId(), video.getUserId(), video.getUrl(), video.getThumbnail(), video.getTitle(), video.getType(), video.getDuration(), video.getArea(), video.getCreateTime());
        UserInfo userInfo = userService.getUserInfo(video.getUserId());
        List<Tag> tags = videoTagService.listTagByVideoId(videoId);
        bo.setUserInfo(userInfo);
        bo.setTags(tags);
        return bo;
    }
}
