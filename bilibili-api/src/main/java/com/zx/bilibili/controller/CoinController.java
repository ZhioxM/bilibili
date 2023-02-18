package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.VideoCoin;
import com.zx.bilibili.service.CoinService;
import com.zx.bilibili.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoinController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CoinService coinService;

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
        int amount = coinService.queryVideoCoinAmount(videoId);
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
}
