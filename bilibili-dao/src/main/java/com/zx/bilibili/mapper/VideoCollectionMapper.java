package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoCollection;
import com.zx.bilibili.domain.VideoCollectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoCollectionMapper {
    int countByExample(VideoCollectionExample example);

    int deleteByExample(VideoCollectionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoCollection record);

    int insertSelective(VideoCollection record);

    List<VideoCollection> selectByExample(VideoCollectionExample example);

    VideoCollection selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoCollection record, @Param("example") VideoCollectionExample example);

    int updateByExample(@Param("record") VideoCollection record, @Param("example") VideoCollectionExample example);

    int updateByPrimaryKeySelective(VideoCollection record);

    int updateByPrimaryKey(VideoCollection record);
}