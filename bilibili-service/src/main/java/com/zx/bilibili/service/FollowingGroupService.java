package com.zx.bilibili.service;

import com.zx.bilibili.domain.FollowingGroup;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/18 19:49
 */
public interface FollowingGroupService {
    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    /**
     * 获取用户的全部分组(0, 1, 2以及用户自定义分组)
     * @param userId 用户获取用户自定义分组，0 1 2分组不与userId进行绑定
     * @return
     */
    List<FollowingGroup> getByUserId(Long userId);
}
