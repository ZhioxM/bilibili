package com.zx.bilibili.service;

import com.zx.bilibili.domain.UserFollowing;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/18 20:05
 */
public interface FollowingService {
    void addUserFollowing(UserFollowing userFollowing);

    List<UserFollowing> getUserFollowing(Long userId);

    List<UserFollowing> getUserFollowed(Long userId);

    UserFollowing getUserFollowed(Long userId, Long followedId);
}
