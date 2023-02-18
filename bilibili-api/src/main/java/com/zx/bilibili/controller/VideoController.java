package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.zx.bilibili.bo.VideoBo;
import com.zx.bilibili.common.api.CommonPage;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.Video;
import com.zx.bilibili.service.UserService;
import com.zx.bilibili.service.VideoService;
import com.zx.bilibili.vo.VideoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/22 15:15
 */
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;


    @Autowired
    private UserService userService;

    // TODO 理清文件上传和视频投稿这两个API的关系
    @ApiOperation("视频投稿")
    @SaCheckLogin
    @PostMapping("/video")
    public CommonResult<Long> addVideo(@RequestBody VideoVo videoVo) {
        Long userId = StpUtil.getLoginIdAsLong();
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

    @ApiOperation("视频详情")
    @SaCheckLogin
    @GetMapping("/video/detail{videoId}")
    public CommonResult<VideoBo> getVideoDetail(@PathVariable Long videoId) {
        VideoBo detail = videoService.queryVideoDetail(videoId);
        return CommonResult.success(detail);
    }
}
