package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zx.bilibili.constant.UserConstant;
import com.zx.bilibili.domain.FollowingGroup;
import com.zx.bilibili.domain.FollowingGroupExample;
import com.zx.bilibili.mapper.FollowingGroupMapper;
import com.zx.bilibili.mapper.UserFollowingMapper;
import com.zx.bilibili.service.FollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/18 19:50
 */
@Service
public class FollowingGroupServiceImpl implements FollowingGroupService {
    @Autowired
    private FollowingGroupMapper followingGroupMapper;

    @Override
    public FollowingGroup getByType(String type) {
        FollowingGroupExample followingGroupExample = new FollowingGroupExample();
        followingGroupExample.createCriteria().andTypeEqualTo(type);
        List<FollowingGroup> followingGroups = followingGroupMapper.selectByExample(followingGroupExample);
        if (CollectionUtil.isEmpty(followingGroups)) {
            return null;
        }
        return followingGroups.get(0);
    }

    @Override
    public FollowingGroup getById(Long id) {
        return followingGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        FollowingGroupExample followingGroupExample = new FollowingGroupExample();
        followingGroupExample.createCriteria().andUserIdEqualTo(userId);
        FollowingGroupExample.Criteria criteria2 = followingGroupExample.createCriteria().andTypeIn(Arrays.asList("0", "1", "2"));
        followingGroupExample.or(criteria2);
        // 查询条件 userId = ${userId} or type in (0, 1, 2), 前者是查询用户自定义分组，后者是查询自带的三个分组
        return followingGroupMapper.selectByExample(followingGroupExample);
    }

    @Override
    public Long addUserFollowingGroup(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupMapper.insertSelective(followingGroup);
        return followingGroup.getId();
    }

    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        FollowingGroupExample followingGroupExample = new FollowingGroupExample();
        followingGroupExample.createCriteria().andUserIdEqualTo(userId);
        return followingGroupMapper.selectByExample(followingGroupExample);
    }
}
