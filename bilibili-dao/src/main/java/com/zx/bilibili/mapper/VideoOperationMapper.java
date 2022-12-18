package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoOperation;
import com.zx.bilibili.domain.VideoOperationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoOperationMapper {
    int countByExample(VideoOperationExample example);

    int deleteByExample(VideoOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoOperation record);

    int insertSelective(VideoOperation record);

    List<VideoOperation> selectByExample(VideoOperationExample example);

    VideoOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoOperation record, @Param("example") VideoOperationExample example);

    int updateByExample(@Param("record") VideoOperation record, @Param("example") VideoOperationExample example);

    int updateByPrimaryKeySelective(VideoOperation record);

    int updateByPrimaryKey(VideoOperation record);
}