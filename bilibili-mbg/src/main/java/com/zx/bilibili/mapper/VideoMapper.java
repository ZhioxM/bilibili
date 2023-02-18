package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.Video;
import com.zx.bilibili.domain.VideoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    int countByExample(VideoExample example);

    int deleteByExample(VideoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Video record);

    int insertSelective(Video record);

    List<Video> selectByExampleWithBLOBs(VideoExample example);

    List<Video> selectByExample(VideoExample example);

    Video selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByExampleWithBLOBs(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByExample(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKeyWithBLOBs(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> selectByGroupIdAndAreaOrderByCollectionTime(@Param("userId") Long userId, @Param("groupId") Long groupId, @Param("start") int start, @Param("end") int end, @Param("area") String area);

    List<Video> selectByGroupIdAndAreaOrderByUploadTime(@Param("userId") Long userId, @Param("groupId") Long groupId, @Param("start") int start, @Param("end") int end, @Param("area") String area);
}