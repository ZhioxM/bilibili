package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.domain.File;
import com.zx.bilibili.domain.FileExample;
import com.zx.bilibili.dto.FileMergeDTO;
import com.zx.bilibili.dto.FileUploadDTO;
import com.zx.bilibili.mapper.FileMapper;
import com.zx.bilibili.service.FileService;
import com.zx.bilibili.util.FastDFSUtil;
import com.zx.bilibili.util.FileChunkMergeUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/21 19:08
 */
@Service
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private FileChunkMergeUtil fileChunkMergeUtil;

    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMD5, Integer sliceNo, Integer totalSliceNo) throws Exception {
        // 判断该文件是否已经上传，避免重复上传
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andMd5EqualTo(fileMD5);
        List<File> db = fileMapper.selectByExample(fileExample);
        if (CollectionUtil.isNotEmpty(db)) {
            return db.get(0).getUrl();
        }
        // 上传至文件服务器，得到文件在服务器上的存储路径
        String url = fastDFSUtil.uploadFileBySlices(slice, fileMD5, sliceNo, totalSliceNo);
        // 文件入库
        if (!StringUtil.isNullOrEmpty(url)) {
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
    public String uploadFileByChunk(MultipartFile chunk, FileUploadDTO fileUploadDTO) throws Exception {
        if (chunk == null || chunk.isEmpty()) {
            throw new CommonException("上传分片为空");
        }
        // 秒传，判断文件是否上传过
        File db = this.getFileByMd5(fileUploadDTO.getMd5());
        if (ObjUtil.isNotNull(db)) {
            // 文件上传过，直接返回文件的DFS路径，后续无需继续上传
            return db.getUrl();
        }
        if (fileUploadDTO.getCurrChunk() == null || fileUploadDTO.getCurrChunk() <= 0) {
            // 没有分片，当做普通文件上传
            String url = fastDFSUtil.uploadCommonFile(chunk);
            // 文件信息入库
            File file = new File();
            file.setCreateTime(new Date());
            file.setMd5(fileUploadDTO.getMd5());
            file.setUrl(url);
            file.setType(fastDFSUtil.getFileExtName(chunk));
            fileMapper.insert(file);
            return url;
        } else {
            // 存储分片的文件对象
            java.io.File targetFile = this.createFile(fileUploadDTO);
            // 保存分片文件
            chunk.transferTo(targetFile);
            return null;
        }
    }

    @Override
    public String mergeFile(FileMergeDTO fileMergeDTO) {
        // 判断文件是否上传过
        File db = this.getFileByMd5(fileMergeDTO.getMd5());
        if (ObjUtil.isNotNull(db)) {
            return db.getUrl();
        }
        String path = fileChunkMergeUtil.mergeChunks(fileMergeDTO);
        // 文件入库
        if (!StringUtil.isNullOrEmpty(path)) {
            File file = new File();
            file.setCreateTime(new Date());
            file.setMd5(fileMergeDTO.getMd5());
            file.setUrl(path);
            file.setType(fileMergeDTO.getExt());
            fileMapper.insert(file);
        }
        return path;
    }

    @Override
    public File getFileByMd5(String fileMd5) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andMd5EqualTo(fileMd5);
        List<File> db = fileMapper.selectByExample(fileExample);
        if (CollectionUtil.isNotEmpty(db)) {
            return null;
        }
        return db.get(0);
    }

    private java.io.File createFile(FileUploadDTO fileUploadDTO) {
        String dir = fileUploadDTO.getTmpDir();
        dir += "/" + fileUploadDTO.getMd5(); // 以文件md5作为下一级文件夹的名字
        this.createDir(dir, false);
        String fileName = String.valueOf(fileUploadDTO.getCurrChunk());
        return new java.io.File(dir, fileName);
    }

    private void createDir(String url, boolean hasTmp) {
        // Java 创建文件夹的方式 https://www.cnblogs.com/zimug/p/13594066.html
        Path path = Paths.get(url);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            logger.error("文件夹创建失败");
            throw new CommonException("文件夹创建失败");
        }
    }
}
