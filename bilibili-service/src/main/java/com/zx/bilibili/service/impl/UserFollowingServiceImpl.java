package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.constant.UserConstant;
import com.zx.bilibili.domain.FollowingGroup;
import com.zx.bilibili.domain.User;
import com.zx.bilibili.domain.UserFollowing;
import com.zx.bilibili.domain.UserFollowingExample;
import com.zx.bilibili.mapper.UserFollowingMapper;
import com.zx.bilibili.service.FollowingGroupService;
import com.zx.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/18 20:05
 */
@Service
public class UserFollowingServiceImpl implements UserFollowingService {
    @Autowired
    private UserFollowingMapper userFollowingMapper;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public void addUserFollowing(UserFollowing userFollowing) {
        Long groupId = userFollowing.getGroupId();
        if(ObjUtil.isEmpty(groupId)) {
            // 没传分组的名字，则添加到默认分组
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        } else {
            // 传了分组ID，则判断分组是否存在
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if(ObjUtil.isEmpty(followingGroup)) {
                throw new CommonException("关注的分组不存在");
            }
        }

        User user = userService.getUser(userFollowing.getFollowingId());
        if(ObjUtil.isEmpty(user)) {
            throw new CommonException("关注的用户不存在");
        }
        // 删除旧的关注关系（如果存在的话）
        UserFollowingExample userFollowingExample = new UserFollowingExample();
        userFollowingExample.createCriteria().andUserIdEqualTo(userFollowing.getUserId()).andFollowingIdEqualTo(userFollowing.getFollowingId());
        userFollowingMapper.deleteByExample(userFollowingExample);

        // 插入新的关注关系
        user.setCreateTime(new Date());
        userFollowingMapper.insertSelective(userFollowing);
    }

    @Override
    public List<UserFollowing> getUserFollowing(Long userId) {
        UserFollowingExample userFollowingExample = new UserFollowingExample();
        userFollowingExample.createCriteria().andUserIdEqualTo(userId);
        return userFollowingMapper.selectByExample(userFollowingExample);
    }

    @Override
    public List<UserFollowing> getUserFollowed(Long userId) {
        UserFollowingExample userFollowingExample = new UserFollowingExample();
        userFollowingExample.createCriteria().andFollowingIdEqualTo(userId);
        return userFollowingMapper.selectByExample(userFollowingExample);
    }

    @Override
    public UserFollowing getUserFollowed(Long userId, Long followedId) {
        UserFollowingExample userFollowingExample = new UserFollowingExample();
        userFollowingExample.createCriteria().andUserIdEqualTo(userId).andFollowingIdEqualTo(followedId);
        List<UserFollowing> userFollowingList = userFollowingMapper.selectByExample(userFollowingExample);
        if(CollectionUtil.isEmpty(userFollowingList)) {
            return null;
        }
        return userFollowingList.get(0);
    }
}
