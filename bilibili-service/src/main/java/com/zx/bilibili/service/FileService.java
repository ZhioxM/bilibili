package com.zx.bilibili.service;

import com.zx.bilibili.domain.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Mzx
 * @Date: 2022/12/21 19:06
 */
public interface FileService {
    String uploadFileBySlices(MultipartFile slice,
                                     String fileMD5,
                                     Integer sliceNo,
                                     Integer totalSliceNo) throws Exception;


    String getFileMD5(MultipartFile file) throws Exception;

    File getFileByMd5(String fileMd5);
}
