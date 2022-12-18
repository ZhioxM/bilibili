package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.AuthElementOperation;
import com.zx.bilibili.domain.AuthElementOperationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthElementOperationMapper {
    int countByExample(AuthElementOperationExample example);

    int deleteByExample(AuthElementOperationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthElementOperation record);

    int insertSelective(AuthElementOperation record);

    List<AuthElementOperation> selectByExample(AuthElementOperationExample example);

    AuthElementOperation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthElementOperation record, @Param("example") AuthElementOperationExample example);

    int updateByExample(@Param("record") AuthElementOperation record, @Param("example") AuthElementOperationExample example);

    int updateByPrimaryKeySelective(AuthElementOperation record);

    int updateByPrimaryKey(AuthElementOperation record);
}