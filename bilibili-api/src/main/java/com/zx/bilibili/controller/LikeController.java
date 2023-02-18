package com.zx.bilibili.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    @Autowired
    private VideoService videoService;


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
}
