package com.zx.bilibili.mapper;

import com.zx.bilibili.domain.VideoBinaryPicture;
import com.zx.bilibili.domain.VideoBinaryPictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideoBinaryPictureMapper {
    int countByExample(VideoBinaryPictureExample example);

    int deleteByExample(VideoBinaryPictureExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoBinaryPicture record);

    int insertSelective(VideoBinaryPicture record);

    List<VideoBinaryPicture> selectByExampleWithBLOBs(VideoBinaryPictureExample example);

    List<VideoBinaryPicture> selectByExample(VideoBinaryPictureExample example);

    VideoBinaryPicture selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoBinaryPicture record, @Param("example") VideoBinaryPictureExample example);

    int updateByExampleWithBLOBs(@Param("record") VideoBinaryPicture record, @Param("example") VideoBinaryPictureExample example);

    int updateByExample(@Param("record") VideoBinaryPicture record, @Param("example") VideoBinaryPictureExample example);

    int updateByPrimaryKeySelective(VideoBinaryPicture record);

    int updateByPrimaryKeyWithBLOBs(VideoBinaryPicture record);

    int updateByPrimaryKey(VideoBinaryPicture record);
}