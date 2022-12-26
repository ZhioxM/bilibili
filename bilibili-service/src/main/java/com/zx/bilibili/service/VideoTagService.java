package com.zx.bilibili.service;

import com.zx.bilibili.domain.Tag;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 21:32
 */
public interface VideoTagService {

    List<Tag> listTagByVideoId(Long videoId);
}
