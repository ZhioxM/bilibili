package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.AuthRoleMenu;
import com.zx.bilibili.domain.AuthRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthRoleMenuMapper {
    int countByExample(AuthRoleMenuExample example);

    int deleteByExample(AuthRoleMenuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthRoleMenu record);

    int insertSelective(AuthRoleMenu record);

    List<AuthRoleMenu> selectByExample(AuthRoleMenuExample example);

    AuthRoleMenu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthRoleMenu record, @Param("example") AuthRoleMenuExample example);

    int updateByExample(@Param("record") AuthRoleMenu record, @Param("example") AuthRoleMenuExample example);

    int updateByPrimaryKeySelective(AuthRoleMenu record);

    int updateByPrimaryKey(AuthRoleMenu record);
}