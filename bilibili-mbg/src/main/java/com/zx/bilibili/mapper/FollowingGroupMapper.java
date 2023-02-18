package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.FollowingGroup;
import com.zx.bilibili.domain.FollowingGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowingGroupMapper {
    int countByExample(FollowingGroupExample example);

    int deleteByExample(FollowingGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FollowingGroup record);

    int insertSelective(FollowingGroup record);

    List<FollowingGroup> selectByExample(FollowingGroupExample example);

    FollowingGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FollowingGroup record, @Param("example") FollowingGroupExample example);

    int updateByExample(@Param("record") FollowingGroup record, @Param("example") FollowingGroupExample example);

    int updateByPrimaryKeySelective(FollowingGroup record);

    int updateByPrimaryKey(FollowingGroup record);
}