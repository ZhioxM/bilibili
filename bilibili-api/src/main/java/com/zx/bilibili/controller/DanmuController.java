package com.zx.bilibili.controller;

import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.Danmu;
import com.zx.bilibili.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class DanmuController {
    @Autowired
    private DanmuService danmuService;

    @GetMapping("/danmu")
    public CommonResult<List<Danmu>> listDanmu(@RequestParam Long videoId,
                                               String startTime,
                                               String endTime) throws ParseException {
        List<Danmu> danmus = danmuService.listDanmu(videoId, startTime, endTime);
        return CommonResult.success(danmus);
    }
}
