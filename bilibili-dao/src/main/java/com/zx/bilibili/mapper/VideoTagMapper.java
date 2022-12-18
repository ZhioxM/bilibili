package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoTag;
import com.zx.bilibili.domain.VideoTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoTagMapper {
    int countByExample(VideoTagExample example);

    int deleteByExample(VideoTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoTag record);

    int insertSelective(VideoTag record);

    List<VideoTag> selectByExample(VideoTagExample example);

    VideoTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoTag record, @Param("example") VideoTagExample example);

    int updateByExample(@Param("record") VideoTag record, @Param("example") VideoTagExample example);

    int updateByPrimaryKeySelective(VideoTag record);

    int updateByPrimaryKey(VideoTag record);
}