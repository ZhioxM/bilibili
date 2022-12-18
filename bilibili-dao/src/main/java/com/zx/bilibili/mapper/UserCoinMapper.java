package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.UserCoin;
import com.zx.bilibili.domain.UserCoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCoinMapper {
    int countByExample(UserCoinExample example);

    int deleteByExample(UserCoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserCoin record);

    int insertSelective(UserCoin record);

    List<UserCoin> selectByExample(UserCoinExample example);

    UserCoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserCoin record, @Param("example") UserCoinExample example);

    int updateByExample(@Param("record") UserCoin record, @Param("example") UserCoinExample example);

    int updateByPrimaryKeySelective(UserCoin record);

    int updateByPrimaryKey(UserCoin record);
}