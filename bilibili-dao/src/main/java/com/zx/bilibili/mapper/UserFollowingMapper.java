package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.UserFollowing;
import com.zx.bilibili.domain.UserFollowingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFollowingMapper {
    int countByExample(UserFollowingExample example);

    int deleteByExample(UserFollowingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFollowing record);

    int insertSelective(UserFollowing record);

    List<UserFollowing> selectByExample(UserFollowingExample example);

    UserFollowing selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFollowing record, @Param("example") UserFollowingExample example);

    int updateByExample(@Param("record") UserFollowing record, @Param("example") UserFollowingExample example);

    int updateByPrimaryKeySelective(UserFollowing record);

    int updateByPrimaryKey(UserFollowing record);
}