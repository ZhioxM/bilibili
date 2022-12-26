package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zx.bilibili.common.api.CommonPage;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.CollectionGroup;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoCollection;
import com.zx.bilibili.service.CollectionFolderService;
import com.zx.bilibili.service.VideoCollectionService;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.vo.CollectionGroupVo;
import com.zx.bilibili.vo.VideoCollectionFolderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zx.bilibili.constant.VideoSortConstants.*;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 17:28
 */
public class VideoCollectionController {
    @Autowired
    private CollectionFolderService collectionFolderService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoCollectionService videoCollectionService;


    @ApiOperation("查询用户收藏夹")
    @SaCheckLogin
    @GetMapping("/video/collect/group")
    public CommonResult<List<CollectionGroupVo>> getCollectionGroup() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<CollectionGroup> collectionGroups = collectionFolderService.listAllCollectionGroup(userId);
        List<CollectionGroupVo> vo = collectionGroups.stream().map(o -> {
            return new CollectionGroupVo(o.getId(), o.getName(), o.getType());
        }).collect(Collectors.toList());
        return CommonResult.success(vo);
    }

    @ApiOperation("新增用户收藏夹")
    @SaCheckLogin
    @PostMapping("/video/collect/group")
    public CommonResult addCollectionGroup(String name) {
        Long userId = StpUtil.getLoginIdAsLong();
        collectionFolderService.addCollectionFolder(userId, name);
        return CommonResult.success(null);
    }

    @ApiOperation("删除用户收藏夹")
    @SaCheckLogin
    @DeleteMapping("/video/collect/group/{groupId}")
    public CommonResult addCollectionGroup(@PathVariable Long groupId) {
        Long userId = StpUtil.getLoginIdAsLong();
        collectionFolderService.deleteCollectionFolder(userId, groupId);
        return CommonResult.success(null);
    }

    @ApiOperation("添加收藏")
    @SaCheckLogin
    @PostMapping("/video/collect")
    public CommonResult collectVideo(Long videoId, Long[] groupId) {
        Long userId = StpUtil.getLoginIdAsLong();
        videoService.collectVideo(userId, videoId, groupId);
        return CommonResult.success(null);
    }

    @ApiOperation("删除收藏")
    @SaCheckLogin
    @DeleteMapping("/video/collect")
    public CommonResult unCollectVideo(Long videoId, Long[] groupId) {
        Long userId = StpUtil.getLoginIdAsLong();
        videoService.uncollectVideo(userId, videoId, groupId);
        return CommonResult.success(null);
    }

    @ApiOperation("查询视频的收藏数量")
    @GetMapping("/video/collect/total")
    public CommonResult countVideoCollect(Long videoId) {
        int count = videoService.collectCount(videoId);
        return CommonResult.success(count);
    }

    @ApiOperation("查询用户对当前视频的收藏夹信息")
    @SaCheckLogin
    @GetMapping("/video/collect/list")
    public CommonResult<List<VideoCollectionFolderVo>> listVideoCollection(@RequestParam Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        List<VideoCollection> videoCollections = videoService.listVideoCollection(userId, videoId);
        // 所在收藏的分组id
        Set<Long> collectedGroupId = videoCollections.stream().map(VideoCollection::getGroupId).collect(Collectors.toSet());
        // 所有收藏夹
        List<CollectionGroup> collectionGroups = collectionFolderService.listAllCollectionGroup(userId);
        List<VideoCollectionFolderVo> vo = collectionGroups.stream().map(o -> new VideoCollectionFolderVo(videoId, o.getId(), o.getName(), collectedGroupId.contains(o.getId()))).collect(Collectors.toList());
        return CommonResult.success(vo);
    }

    @ApiOperation("查询用户是否收藏此视频")
    @SaCheckLogin
    @GetMapping("/video/collect/{videoId}")
    public CommonResult getVideoCollectionStatus(@PathVariable Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        List<VideoCollection> videoCollections = videoService.listVideoCollection(userId, videoId);
        if (CollectionUtil.isEmpty(videoCollections)) {
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

    @ApiOperation("查询用户收藏夹下的视频, 如果是全部分区，area不必传")
    @SaCheckLogin
    @GetMapping("/video/list/{groupId}")
    public CommonResult<CommonPage<Video>> listVideoInCollectionFolderByArea(@PathVariable Long groupId,
                                                                             @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                             @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                                             String area,
                                                                             @RequestParam(required = false, defaultValue = LATEST_COLLECT + "") Integer order) {
        // TODO 默认的排序方式是 “最近收藏”, 需要实现最多播放，最多收藏; 这个搜索逻辑挺麻烦的（在t_video_collection表中添加冗余字段，即视频的分区、上传时间，可以在查找收藏表时提前进行分页，或者不增加冗余字段而是进行连表查询）
        Long userId = StpUtil.getLoginIdAsLong();
        List<Video> videos = null;
        switch (order) {
            case LATEST_COLLECT:
                videos = videoService.listVideoByGroupIdOrderByCollectionTime(userId, groupId, pageNum, pageSize, area);
                break;
            case MOST_PLAY:
                videos = videoService.listVideoByGroupIdOrderByPlayAmount(userId, groupId, pageNum, pageSize, area);
                break;
            case LATEST_SUBMIT:
                videos = videoService.listVideoByGroupIdOrderByUploadTime(userId, groupId, pageNum, pageSize, area);
                break;
        }
        return CommonResult.success(CommonPage.restPage(videos));
    }
}
