package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoLike;
import com.zx.bilibili.domain.VideoLikeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoLikeMapper {
    int countByExample(VideoLikeExample example);

    int deleteByExample(VideoLikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoLike record);

    int insertSelective(VideoLike record);

    List<VideoLike> selectByExample(VideoLikeExample example);

    VideoLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoLike record, @Param("example") VideoLikeExample example);

    int updateByExample(@Param("record") VideoLike record, @Param("example") VideoLikeExample example);

    int updateByPrimaryKeySelective(VideoLike record);

    int updateByPrimaryKey(VideoLike record);
}