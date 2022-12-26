package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.domain.CollectionGroup;
import com.zx.bilibili.domain.CollectionGroupExample;
import com.zx.bilibili.mapper.CollectionGroupMapper;
import com.zx.bilibili.service.CollectionFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/26 17:19
 */
@Service
public class CollectionFolderServiceImpl implements CollectionFolderService {
    @Autowired
    private CollectionGroupMapper collectionGroupMapper;

    @Override
    public Long addCollectionFolder(Long userId, String name) {
        CollectionGroup db = queryCollectionFolderByName(userId, name);
        if (ObjUtil.isNotNull(db)) {
            throw new CommonException("分组名重复");
        }
        CollectionGroup record = new CollectionGroup();
        record.setName(name);
        record.setUserId(userId);
        record.setCreateTime(new Date());
        record.setType("1");
        collectionGroupMapper.insert(record);
        return record.getId();
    }

    @Override
    public void deleteCollectionFolder(Long userId, Long groupId) {
        collectionGroupMapper.deleteByPrimaryKey(groupId);
    }

    private CollectionGroup queryCollectionFolderByName(Long userId, String name) {
        CollectionGroupExample collectionGroupExample = new CollectionGroupExample();
        collectionGroupExample.createCriteria().andUserIdEqualTo(userId).andNameEqualTo(name);
        List<CollectionGroup> collectionGroups = collectionGroupMapper.selectByExample(collectionGroupExample);
        if (CollectionUtil.isEmpty(collectionGroups)) {
            return null;
        }
        return collectionGroups.get(0);
    }

    @Override
    public List<CollectionGroup> listAllCollectionGroup(Long userId) {
        CollectionGroupExample ex = new CollectionGroupExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        List<CollectionGroup> customGroup = collectionGroupMapper.selectByExample(ex);

        ex.clear();
        ex.createCriteria().andTypeEqualTo("0");
        List<CollectionGroup> defaultGroup = collectionGroupMapper.selectByExample(ex);
        return CollectionUtil.unionAll(customGroup, defaultGroup);
    }
}
