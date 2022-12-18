package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoComment;
import com.zx.bilibili.domain.VideoCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoCommentMapper {
    int countByExample(VideoCommentExample example);

    int deleteByExample(VideoCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoComment record);

    int insertSelective(VideoComment record);

    List<VideoComment> selectByExampleWithBLOBs(VideoCommentExample example);

    List<VideoComment> selectByExample(VideoCommentExample example);

    VideoComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoComment record, @Param("example") VideoCommentExample example);

    int updateByExampleWithBLOBs(@Param("record") VideoComment record, @Param("example") VideoCommentExample example);

    int updateByExample(@Param("record") VideoComment record, @Param("example") VideoCommentExample example);

    int updateByPrimaryKeySelective(VideoComment record);

    int updateByPrimaryKeyWithBLOBs(VideoComment record);

    int updateByPrimaryKey(VideoComment record);
}