package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.zx.bilibili.common.api.CommonPage;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.vo.VideoVo;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.VerticalDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
}
