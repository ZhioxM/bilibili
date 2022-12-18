package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoCoin;
import com.zx.bilibili.domain.VideoCoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoCoinMapper {
    int countByExample(VideoCoinExample example);

    int deleteByExample(VideoCoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoCoin record);

    int insertSelective(VideoCoin record);

    List<VideoCoin> selectByExample(VideoCoinExample example);

    VideoCoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoCoin record, @Param("example") VideoCoinExample example);

    int updateByExample(@Param("record") VideoCoin record, @Param("example") VideoCoinExample example);

    int updateByPrimaryKeySelective(VideoCoin record);

    int updateByPrimaryKey(VideoCoin record);
}