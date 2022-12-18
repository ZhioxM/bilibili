package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoView;
import com.zx.bilibili.domain.VideoViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoViewMapper {
    int countByExample(VideoViewExample example);

    int deleteByExample(VideoViewExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoView record);

    int insertSelective(VideoView record);

    List<VideoView> selectByExample(VideoViewExample example);

    VideoView selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoView record, @Param("example") VideoViewExample example);

    int updateByExample(@Param("record") VideoView record, @Param("example") VideoViewExample example);

    int updateByPrimaryKeySelective(VideoView record);

    int updateByPrimaryKey(VideoView record);
}