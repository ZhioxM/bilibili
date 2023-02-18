package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.RefreshToken;
import com.zx.bilibili.domain.RefreshTokenExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RefreshTokenMapper {
    int countByExample(RefreshTokenExample example);

    int deleteByExample(RefreshTokenExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RefreshToken record);

    int insertSelective(RefreshToken record);

    List<RefreshToken> selectByExample(RefreshTokenExample example);

    RefreshToken selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RefreshToken record, @Param("example") RefreshTokenExample example);

    int updateByExample(@Param("record") RefreshToken record, @Param("example") RefreshTokenExample example);

    int updateByPrimaryKeySelective(RefreshToken record);

    int updateByPrimaryKey(RefreshToken record);
}