package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.Danmu;
import com.zx.bilibili.domain.DanmuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DanmuMapper {
    int countByExample(DanmuExample example);

    int deleteByExample(DanmuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Danmu record);

    int insertSelective(Danmu record);

    List<Danmu> selectByExampleWithBLOBs(DanmuExample example);

    List<Danmu> selectByExample(DanmuExample example);

    Danmu selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Danmu record, @Param("example") DanmuExample example);

    int updateByExampleWithBLOBs(@Param("record") Danmu record, @Param("example") DanmuExample example);

    int updateByExample(@Param("record") Danmu record, @Param("example") DanmuExample example);

    int updateByPrimaryKeySelective(Danmu record);

    int updateByPrimaryKeyWithBLOBs(Danmu record);

    int updateByPrimaryKey(Danmu record);
}