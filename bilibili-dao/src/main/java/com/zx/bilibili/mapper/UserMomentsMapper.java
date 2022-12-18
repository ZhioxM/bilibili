package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.UserMoments;
import com.zx.bilibili.domain.UserMomentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMomentsMapper {
    int countByExample(UserMomentsExample example);

    int deleteByExample(UserMomentsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserMoments record);

    int insertSelective(UserMoments record);

    List<UserMoments> selectByExample(UserMomentsExample example);

    UserMoments selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserMoments record, @Param("example") UserMomentsExample example);

    int updateByExample(@Param("record") UserMoments record, @Param("example") UserMomentsExample example);

    int updateByPrimaryKeySelective(UserMoments record);

    int updateByPrimaryKey(UserMoments record);
}