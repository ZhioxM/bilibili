package com.zx.bilibili.service.impl;

import com.zx.bilibili.domain.Tag;
import com.zx.bilibili.domain.TagExample;
import com.zx.bilibili.domain.VideoTag;
import com.zx.bilibili.domain.VideoTagExample;
import com.zx.bilibili.mapper.TagMapper;
import com.zx.bilibili.mapper.VideoTagMapper;
import com.zx.bilibili.service.VideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 21:32
 */
@Service
public class VideoTagServiceImpl implements VideoTagService {
    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private TagMapper tagMapper;


    @Override
    public List<Tag> listTagByVideoId(Long videoId) {
        VideoTagExample videoTagExample = new VideoTagExample();
        videoTagExample.createCriteria().andVideoIdEqualTo(videoId);
        List<VideoTag> videoTags = videoTagMapper.selectByExample(videoTagExample);
        List<Long> tagIds = videoTags.stream().map(VideoTag::getTagId).collect(Collectors.toList());
        TagExample tagExample = new TagExample();
        tagExample.createCriteria().andIdIn(tagIds);
        return tagMapper.selectByExample(tagExample);
    }
}
