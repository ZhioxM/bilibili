package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.bo.VideoRootCommentBO;
import com.zx.bilibili.common.api.CommonPage;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoCoin;
import com.zx.bilibili.domain.VideoComment;
import com.zx.bilibili.service.VideoCommentService;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.vo.VideoCommentVo;
import com.zx.bilibili.vo.VideoRootCommentVo;
import com.zx.bilibili.vo.VideoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:15
 */
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoCommentService videoCommentService;

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
    public CommonResult<CommonPage<Video>> listVideo(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                     @RequestParam String area) {
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

    @ApiOperation("视频投币")
    @SaCheckLogin
    @PostMapping("/video/coin")
    public CommonResult addCoin(@RequestParam Long videoId,
                                @RequestParam Integer coin) {
        Long userId = StpUtil.getLoginIdAsLong();
        videoService.addCoin(userId, videoId, coin);
        return CommonResult.success(null);
    }

    @ApiOperation("查询视频的投币数")
    @GetMapping("/video/coin")
    public CommonResult getVideoCoinAmount(@RequestParam Long videoId) {
        int amount = videoService.queryVideoCoinAmount(videoId);
        return CommonResult.success(amount);
    }

    @ApiOperation("查询用户是否对当前视频投过币")
    @SaCheckLogin
    @GetMapping("/video/coin/{videoId}")
    public CommonResult getVideoCoinStatus(@PathVariable Long videoId) {
        Long userId = StpUtil.getLoginIdAsLong();
        VideoCoin db = videoService.queryVideoCoin(userId, videoId);
        return CommonResult.success(ObjUtil.isNotNull(db));
    }

    @ApiOperation("分页查询一级评论")
    @GetMapping("/video/comment/{videoId}")
    public CommonResult<List<VideoRootCommentVo>> listVideoRootComment(@PathVariable Long videoId,
                                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(required = false, defaultValue = "0") Integer orderBy) {
        List<VideoRootCommentBO> bos = videoCommentService.listVideoRootComment(videoId, pageNum, pageSize, orderBy);
        List<VideoRootCommentVo> vos = new ArrayList<>(bos.size());
        for (VideoRootCommentBO bo : bos) {
            VideoRootCommentVo vo = new VideoRootCommentVo(bo.getRootComment().getId(),
                                                           bo.getRootComment().getVideoId(),
                                                           bo.getRootComment().getUserId(),
                                                           bo.getRootComment().getReplyUserId(),
                                                           bo.getRootComment().getCreateTime(),
                                                           bo.getRootComment().getContext(),
                                                           bo.getTotalReply());
            List<VideoCommentVo> reply = bo.getTopKSubComment().stream().map(
                    o -> new VideoCommentVo(o.getId(), o.getVideoId(), o.getUserId(), o.getReplyUserId(), o.getRootId(), o.getParentId(), o.getCreateTime(), o.getContext())).collect(Collectors.toList());
            vo.setReply(reply);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @ApiOperation("分页查询子评论")
    @GetMapping("/video/comment/{videoId}/{commentId}")
    public CommonResult<List<VideoCommentVo>> listVideoSubComment(@PathVariable Long videoId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                  @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        // 二级评论也可以做分页
        List<VideoComment> subComments = videoCommentService.listVideoSubComment(videoId, commentId, pageNum, pageSize);
        List<VideoCommentVo> vos = subComments.stream().map(
                o -> new VideoCommentVo(o.getId(), o.getVideoId(), o.getUserId(), o.getReplyUserId(), o.getRootId(), o.getParentId(), o.getCreateTime(), o.getContext())).collect(Collectors.toList());
        return CommonResult.success(vos);
    }

}
