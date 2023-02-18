package com.zx.bilibili.util;

import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.dto.FileMergeDTO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class FileChunkMergeUtil {

    private final Logger logger = LoggerFactory.getLogger(FileChunkMergeUtil.class);

    @Autowired
    private FastDFSUtil fastDFSUtil;

    public String mergeChunks(FileMergeDTO dto) {
        int totalChunk = dto.getTotalChunk();
        String md5 = dto.getMd5();
        String url = dto.getUrl();
        String chunkPath = Paths.get(url, md5).toString(); // 分片文件存储的文件夹路径
        int chunksNum = this.getChunksNum(chunkPath); // 获取文件夹下的分片数量，从而判断分片文件是否完整

        String path = null;
        // 检查分片数量是否完整
        if (totalChunk == chunksNum) {
            // TODO 为每一个要合并的文件创建一把锁（使用分布式锁？ 还是本地锁？）
            List<File> files = new ArrayList<>(Arrays.asList(this.getChunks(chunkPath)));
            if (totalChunk == files.size()) {
                //按照名称排序文件，这里分片都是按照数字命名的, 按照数字升序排序
                files.sort(Comparator.comparing(File::getName));
                try {
                    path = this.merge(files);
                    // 清理分片文件
                    this.cleanSpace(chunkPath);
                    return path;
                } catch (Exception e) {
                    throw new CommonException("文件合并失败");
                }
            }
        }
        logger.error("文件[签名:" + md5 + "]数据不完整");
        return path;
    }

    // 将服务器上的文件合并到DFS，并返回文件在DFS上的存储位置
    private String merge(List<File> files) throws Exception {
        String filePath = null;
        for (int i = 0; i < files.size(); i++) {
            java.io.File file = files.get(i);
            if (i == 0) {
                // 上传第一个分片，得到文件在DFS上的存储路径
                filePath = fastDFSUtil.uploadAppenderFile(file);
            } else {
                // 在末尾追加分片
                fastDFSUtil.appendFile(file, filePath);
            }
        }
        return filePath;
    }


    /**
     * 获取指定文件的分片数量
     *
     * @param folder 文件夹路径
     * @return
     */
    private int getChunksNum(String folder) {
        java.io.File[] filesList = this.getChunks(folder);
        return filesList.length;
    }

    /**
     * 获取指定文件的所有分片
     *
     * @param folder 文件夹路径
     * @return
     */
    private java.io.File[] getChunks(String folder) {
        java.io.File targetFolder = new java.io.File(folder);
        return targetFolder.listFiles((file) -> {
            if (file.isDirectory()) {
                return false;
            }
            return true;
        });
    }

    protected boolean cleanSpace(String path) {
        //删除分片文件夹
        java.io.File garbage = new java.io.File(path);
        if (!FileUtils.deleteQuietly(garbage)) {
            return false;
        }
        // 删除tmp文件
        // garbage = new java.io.File(path + ".tmp");
        // if (!FileUtils.deleteQuietly(garbage)) {
        //     return false;
        // }
        return true;
    }
}
