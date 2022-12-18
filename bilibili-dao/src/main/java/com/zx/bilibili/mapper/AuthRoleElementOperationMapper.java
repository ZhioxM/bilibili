package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.AuthRoleElementOperation;
import com.zx.bilibili.domain.AuthRoleElementOperationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthRoleElementOperationMapper {
    int countByExample(AuthRoleElementOperationExample example);

    int deleteByExample(AuthRoleElementOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthRoleElementOperation record);

    int insertSelective(AuthRoleElementOperation record);

    List<AuthRoleElementOperation> selectByExample(AuthRoleElementOperationExample example);

    AuthRoleElementOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthRoleElementOperation record, @Param("example") AuthRoleElementOperationExample example);

    int updateByExample(@Param("record") AuthRoleElementOperation record, @Param("example") AuthRoleElementOperationExample example);

    int updateByPrimaryKeySelective(AuthRoleElementOperation record);

    int updateByPrimaryKey(AuthRoleElementOperation record);
}