package com.zx.bilibili.controller;

import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Mzx
 * @Date: 2022/12/21 19:06
 */
public class FileController {
    @Autowired
    private FileService fileService;

    // 获取文件的MD5字符串。在实际开发中。文件的分片以及MD5都是由前端完成的。这个接口只是为了测试
    // MD5加密后的字符串只跟文件的二进制流有关，与文件名、文件后缀无关（一切数据都是二进制的）
    @Deprecated
    @PostMapping("/md5files")
    public CommonResult<String> getFileMD5(MultipartFile file) throws Exception {
        String fileMD5 = fileService.getFileMD5(file);
        return CommonResult.success(fileMD5);
    }

    // 文件分片上传
    // TODO 怎么控制序号的问题？如果分片请求不是按序到达的怎么办？
    // 查查资料，看看人家是怎么解决的
    // 我的一个思路，前端通过一个请求将各个分片的序号、大小等信息等信息先全部发过来？
    @PutMapping("/file-slices")
    public CommonResult<String> uploadFileBySlices(MultipartFile slice,
                                                   String fileMd5,
                                                   Integer sliceNo,
                                                   Integer totalSliceNo) throws Exception {
        String filePath = fileService.uploadFileBySlices(slice, fileMd5, sliceNo, totalSliceNo);
        return CommonResult.success(filePath);
    }
}
