package com.zx.bilibili.vo;

/**
 * 用户收藏夹VO
 *
 * @Author: Mzx
 * @Date: 2022/12/25 17:55
 */
public class VideoCollectionFolderVo {

    private Long videoId;
    private Long groupId;

    private String name;

    private boolean collected;

    public VideoCollectionFolderVo() {
    }

    public VideoCollectionFolderVo(Long videoId, Long groupId, String name, boolean collected) {
        this.videoId = videoId;
        this.groupId = groupId;
        this.name = name;
        this.collected = collected;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}
