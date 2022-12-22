package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zx.bilibili.domain.File;
import com.zx.bilibili.domain.FileExample;
import com.zx.bilibili.mapper.FileMapper;
import com.zx.bilibili.service.FileService;
import com.zx.bilibili.util.FastDFSUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/21 19:08
 */
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMD5, Integer sliceNo, Integer totalSliceNo) throws Exception {
        // 判断该文件是否已经上传，避免重复上传
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andMd5EqualTo(fileMD5);
        List<File> db = fileMapper.selectByExample(fileExample);
        if(CollectionUtil.isNotEmpty(db)) {
            return db.get(0).getUrl();
        }

        String url = fastDFSUtil.uploadFileBySlices(slice, fileMD5, sliceNo, totalSliceNo);
        if(!StringUtil.isNullOrEmpty(url)){
            File file = new File();
            file.setCreateTime(new Date());
            file.setMd5(fileMD5);
            file.setUrl(url);
            file.setType(fastDFSUtil.getFileExtName(slice));
            fileMapper.insert(file);
        }
        return url;
    }

    @Override
    public String getFileMD5(MultipartFile file) throws Exception {
        return null;
    }

    @Override
    public File getFileByMd5(String fileMd5) {
        return null;
    }
}
