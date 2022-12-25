package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zx.bilibili.common.api.CommonPage;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.CollectionGroup;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoCollection;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.vo.CollectionGroupVo;
import com.zx.bilibili.vo.VideoCollectionVo;
import com.zx.bilibili.vo.VideoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:15
 */
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    // TODO 理清文件上传和视频投稿这两个API的关系
    @ApiOperation("视频投稿")
    @SaCheckLogin
    @PostMapping("/video")
    public CommonResult<Long> addVideo(@RequestBody VideoVo videoVo) {
        String userId = StpUtil.getLoginIdAsString();
        Video video = new Video();
        video.setUserId(userId);
        video.setTitle(videoVo.getTitle());
        video.setThumbnail(videoVo.getThumbnail());
        video.setDescription(videoVo.getDescription());
        video.setArea(videoVo.getArea());
        video.setDuration(videoVo.getDuration());
        video.setUrl(videoVo.getUrl());
        video.setType(videoVo.getType());
        videoService.addVideo(video, videoVo.getVideoTags());
        return CommonResult.success(video.getId());
    }

    @ApiOperation("分页查询视频列表")
    @SaCheckLogin
    @GetMapping("/videos")
    public CommonResult<CommonPage<Video>> listVideo(Integer pageNum, Integer pageSize, String area) {
        List<Video> videos = videoService.listVideos(pageNum, pageSize, area);
        return CommonResult.success(CommonPage.restPage(videos));
    }

    /**
     * 虽然直接暴露视频文件在FastDFS上的路径给浏览器，浏览器可以自动采用分片下载。但是将资源的完整路径交给前端是危险的，因为有一些资源是会员专属的之类的
     * TODO 难点：后端返回视频流的技术
     * 未登录也可以访问视频, 结合鉴权，有些视频得需要会员才能观看
     *
     * @return
     */
    @ApiOperation("视频播放")
    @GetMapping("/video")
    public void loadVideo(HttpServletRequest request, HttpServletResponse response, String url) {
        // 文件流数据通过原生Response对象返回
        videoService.loadVideo(request, response, url);
    }


    @ApiOperation("视频点赞")
    @SaCheckLogin
    @PostMapping("/video/like/{videoId}")
    public CommonResult likeVideo(@PathVariable Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        videoService.likeVideo(userId, videoId);
        return CommonResult.success(null);
    }

    @ApiOperation("取消视频点赞")
    @SaCheckLogin
    @DeleteMapping("/vedio/like/{videoId}")
    public CommonResult unlikeVideo(@PathVariable Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        videoService.unlikeVideo(userId, videoId);
        return CommonResult.success(null);
    }

    @ApiOperation("获取视频点赞数")
    @GetMapping("/video/likes/{videoId")
    public CommonResult videoLikes(@PathVariable Long videoId) {
        int count = videoService.likeCount(videoId);
        return CommonResult.success(count);
    }

    @ApiOperation("查询当前用户是否对视频点过赞")
    @SaCheckLogin
    @GetMapping("/video/like/{videoId")
    public CommonResult videoLike(@PathVariable Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        boolean i = videoService.likeStatus(userId, videoId);
        return CommonResult.success(i);
    }

    @ApiOperation("查询用户收藏夹")
    @SaCheckLogin
    @GetMapping("/video/collect/group")
    public CommonResult<List<CollectionGroupVo>> getCollectionGroup() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<CollectionGroup> collectionGroups = videoService.listAllCollectionGroup(userId);
        List<CollectionGroupVo> vo = collectionGroups.stream().map(o -> {
            return new CollectionGroupVo(o.getId(), o.getName(), o.getType());
        }).collect(Collectors.toList());
        return CommonResult.success(vo);
    }

    // 新建用户收藏夹，删除用户收藏夹

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
    public CommonResult<List<VideoCollectionVo>> listVideoCollection(Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        List<VideoCollection> videoCollections = videoService.listVideoCollection(userId, videoId);
        // 所在收藏的分组id
        Set<Long> collectedGroupId = videoCollections.stream().map(VideoCollection::getGroupId).collect(Collectors.toSet());
        // 所有收藏夹
        List<CollectionGroup> collectionGroups = videoService.listAllCollectionGroup(userId);
        List<VideoCollectionVo> vo = collectionGroups.stream().map(o -> {
            return new VideoCollectionVo(o.getId(), o.getName(), collectedGroupId.contains(o.getId()));
        }).collect(Collectors.toList());
        return CommonResult.success(vo);
    }

    @ApiOperation("查询用户是否收藏此视频")
    @SaCheckLogin
    @GetMapping("/video/collect/{videoId}")
    public CommonResult videoCollection(@PathVariable Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        List<VideoCollection> videoCollections = videoService.listVideoCollection(userId, videoId);
        if (CollectionUtil.isEmpty(videoCollections)) {
            return CommonResult.success(false);
        }
        return CommonResult.success(true);
    }

}
