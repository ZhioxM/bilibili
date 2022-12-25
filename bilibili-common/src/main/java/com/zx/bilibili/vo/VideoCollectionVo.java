package com.zx.bilibili.vo;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 17:55
 */
public class VideoCollectionVo {
    private Long groupId;

    private String name;

    private boolean collected;

    public VideoCollectionVo() {
    }

    public VideoCollectionVo(Long groupId, String name, boolean collected) {
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
}
