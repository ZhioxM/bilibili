package com.zx.bilibili.util;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.exception.ConditionException;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Component
public class FastDFSUtil {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private AppendFileStorageClient appendFileStorageClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String PATH_KEY = "path-key:";

    // Redis存储分片偏移量的Key
    private static final String UPLOADED_OFFSET_KEY = "uploaded-size-key:";

    // Redis存储分片序号的key
    private static final String UPLOADED_NO_KEY = "uploaded-no-key:";

    private static final String DEFAULT_GROUP = "group1";

    private static final int SLICE_SIZE = 1024 * 1024 * 2; // 文件分片大小，2M

    @Value("${fdfs.http.storage-addr}")
    private String httpFdfsStorageAddr;

    public String getFileExtName(MultipartFile file) {
        if (file == null) {
            throw new CommonException("非法文件！");
        }
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    public String getFileExtName(File file) {
        if (file == null) {
            throw new CommonException("非法文件！");
        }
        String fileName = file.getPath();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    //上传小文件
    public String uploadCommonFile(MultipartFile file) throws Exception {
        Set<MetaData> metaDataSet = new HashSet<>();
        String fileExtName = this.getFileExtName(file);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, metaDataSet);
        return storePath.getPath();
    }

    public String uploadCommonFile(File file, String fileType) throws Exception {
        Set<MetaData> metaDataSet = new HashSet<>();
        StorePath storePath = fastFileStorageClient.uploadFile(new FileInputStream(file),
                                                               file.length(), fileType, metaDataSet);
        return storePath.getPath();
    }

    //上传可以分片的文件(第一次上传时使用，返回文件存储的路径)
    public String uploadAppenderFile(MultipartFile file) throws Exception {
        String fileType = this.getFileExtName(file);
        StorePath storePath = appendFileStorageClient.uploadAppenderFile(DEFAULT_GROUP, file.getInputStream(), file.getSize(), fileType);
        return storePath.getPath();
    }

    public String uploadAppenderFile(File file) throws Exception {
        String fileType = this.getFileExtName(file);
        StorePath storePath = appendFileStorageClient.uploadAppenderFile(DEFAULT_GROUP, Files.newInputStream(file.toPath()), file.length(), fileType);
        return storePath.getPath();
    }

    public void appendFile(File file, String filePath) throws IOException {
        appendFileStorageClient.appendFile(DEFAULT_GROUP, filePath, Files.newInputStream(file.toPath()), file.length());
    }

    // 续传断点传输的文件
    public void modifyAppenderFile(MultipartFile file, String filePath, long offset) throws Exception {
        appendFileStorageClient.modifyFile(DEFAULT_GROUP, filePath, file.getInputStream(), file.getSize(), offset);
    }

    // 文件分片上传
    @Deprecated // 废弃。没有考虑文件分片乱序到达的情况
    public String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws Exception {
        if (file == null || sliceNo == null || totalSliceNo == null) {
            throw new ConditionException("参数异常！");
        }
        String pathKey = PATH_KEY + fileMd5;
        String uploadedOffsetKey = UPLOADED_OFFSET_KEY + fileMd5;
        String uploadedNoKey = UPLOADED_NO_KEY + fileMd5;
        String uploadedOffsetStr = redisTemplate.opsForValue().get(uploadedOffsetKey);
        Long uploadedOffset = 0L;
        if (!StringUtil.isNullOrEmpty(uploadedOffsetStr)) {
            uploadedOffset = Long.valueOf(uploadedOffsetStr);
        }
        if (sliceNo == 1) {
            //上传第一个分片
            String path = this.uploadAppenderFile(file);
            if (StringUtil.isNullOrEmpty(path)) {
                throw new CommonException("上传失败！");
            }
            // 保存文件路径
            redisTemplate.opsForValue().set(pathKey, path);
            // 保存分片序号
            redisTemplate.opsForValue().set(uploadedNoKey, "1");
        } else {
            // 断点上传后续分片
            String filePath = redisTemplate.opsForValue().get(pathKey);
            if (StringUtil.isNullOrEmpty(filePath)) {
                throw new CommonException("上传失败！");
            }
            this.modifyAppenderFile(file, filePath, uploadedOffset);
            redisTemplate.opsForValue().increment(uploadedNoKey);
        }
        // 修改历史上传分片文件大小，作为下次文件上传的偏移量
        uploadedOffset += file.getSize();
        redisTemplate.opsForValue().set(uploadedOffsetKey, String.valueOf(uploadedOffset));

        //如果所有分片全部上传完毕，则清空redis里面相关的key和value，并返回文件的路径
        String uploadedNoStr = redisTemplate.opsForValue().get(uploadedNoKey);
        Integer uploadedNo = Integer.valueOf(uploadedNoStr);
        String resultPath = "";
        if (uploadedNo.equals(totalSliceNo)) {
            resultPath = redisTemplate.opsForValue().get(pathKey);
            List<String> keyList = Arrays.asList(uploadedNoKey, pathKey, uploadedOffsetKey);
            redisTemplate.delete(keyList);
        }
        return resultPath;
    }

    // 文件的分片可以由前端完成，这里是为了在后端测试分片长传的功能
    public void convertFileToSlices(MultipartFile multipartFile) throws Exception {
        String fileExtName = this.getFileExtName(multipartFile);
        //生成临时文件，将MultipartFile转为File
        File file = this.multipartFileToFile(multipartFile);
        long fileLength = file.length();
        int count = 1;
        for (int i = 0; i < fileLength; i += SLICE_SIZE) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(i);
            byte[] bytes = new byte[SLICE_SIZE];
            int len = randomAccessFile.read(bytes);
            String path = "D:\\bilibili-temp-file\\" + count + "." + fileExtName;
            File slice = new File(path);
            FileOutputStream fos = new FileOutputStream(slice);
            fos.write(bytes, 0, len);
            fos.close();
            randomAccessFile.close();
            count++;
        }
        //删除临时文件
        file.delete();
    }

    public File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        String originalFileName = multipartFile.getOriginalFilename();
        String[] fileName = originalFileName.split("\\.");
        File file = File.createTempFile(fileName[0], "." + fileName[1]);
        multipartFile.transferTo(file);
        return file;
    }

    //删除
    public void deleteFile(String filePath) {
        fastFileStorageClient.deleteFile(filePath);
    }


    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String path) throws Exception {
        // 根据文件在文件服务器上的路径查询文件的所有信息
        FileInfo fileInfo = fastFileStorageClient.queryFileInfo(DEFAULT_GROUP, path);
        // 文件总大小
        long totalFileSize = fileInfo.getFileSize();
        // 文件在DFS上的完整请求路径
        String url = httpFdfsStorageAddr + path;
        // 获取前端请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.put(header, request.getHeader(header));
        }
        // 用于视频分片加载的请求头数据（Range）
        String rangeStr = request.getHeader("Range");
        String[] range;
        if (StringUtil.isNullOrEmpty(rangeStr)) {
            rangeStr = "bytes=0-" + (totalFileSize - 1);
        }
        range = rangeStr.split("bytes=|-");
        long begin = 0;
        if (range.length >= 2) {
            begin = Long.parseLong(range[1]);
        }
        long end = totalFileSize - 1;
        if (range.length >= 3) {
            end = Long.parseLong(range[2]);
        }
        long len = (end - begin) + 1;

        // 设置响应头
        String contentRange = "bytes " + begin + "-" + end + "/" + totalFileSize;
        response.setHeader("Content-Range", contentRange);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Type", "video/mp4");
        response.setContentLength((int) len);
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 向DFS发送请求
        HttpUtil.get(url, headers, response);
    }

    public void downLoadFile(String url, String localPath) {
        fastFileStorageClient.downloadFile(DEFAULT_GROUP, url,
                                           new DownloadCallback<String>() {
                                               @Override
                                               public String recv(InputStream ins) throws IOException {
                                                   File file = new File(localPath);
                                                   OutputStream os = new FileOutputStream(file);
                                                   int len = 0;
                                                   byte[] buffer = new byte[1024];
                                                   while ((len = ins.read(buffer)) != -1) {
                                                       os.write(buffer, 0, len);
                                                   }
                                                   os.close();
                                                   ins.close();
                                                   return "success";
                                               }
                                           });
    }
}
