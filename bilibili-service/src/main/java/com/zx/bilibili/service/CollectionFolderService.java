package com.zx.bilibili.service;

import com.zx.bilibili.domain.CollectionGroup;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 17:15
 */
public interface CollectionFolderService {
    Long addCollectionFolder(Long userId, String name);

    void deleteCollectionFolder(Long userId, Long groupId);

    List<CollectionGroup> listAllCollectionGroup(Long userId);
}
