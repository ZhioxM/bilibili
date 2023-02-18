package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.AuthMenu;
import com.zx.bilibili.domain.AuthMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMenuMapper {
    int countByExample(AuthMenuExample example);

    int deleteByExample(AuthMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthMenu record);

    int insertSelective(AuthMenu record);

    List<AuthMenu> selectByExample(AuthMenuExample example);

    AuthMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthMenu record, @Param("example") AuthMenuExample example);

    int updateByExample(@Param("record") AuthMenu record, @Param("example") AuthMenuExample example);

    int updateByPrimaryKeySelective(AuthMenu record);

    int updateByPrimaryKey(AuthMenu record);
}