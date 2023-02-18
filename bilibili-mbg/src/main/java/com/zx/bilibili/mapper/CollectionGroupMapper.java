package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.CollectionGroup;
import com.zx.bilibili.domain.CollectionGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionGroupMapper {
    int countByExample(CollectionGroupExample example);

    int deleteByExample(CollectionGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CollectionGroup record);

    int insertSelective(CollectionGroup record);

    List<CollectionGroup> selectByExample(CollectionGroupExample example);

    CollectionGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CollectionGroup record, @Param("example") CollectionGroupExample example);

    int updateByExample(@Param("record") CollectionGroup record, @Param("example") CollectionGroupExample example);

    int updateByPrimaryKeySelective(CollectionGroup record);

    int updateByPrimaryKey(CollectionGroup record);
}