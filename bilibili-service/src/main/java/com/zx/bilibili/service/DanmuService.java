package com.zx.bilibili.service;

import com.zx.bilibili.domain.Danmu;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2023/2/18 14:08
 */
public interface DanmuService {
    List<Danmu> listDanmu(Long videoID, String startTime, String endTime) throws ParseException;
}
