package com.zx.bilibili.service;

import com.zx.bilibili.domain.UserCoin;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 19:29
 */
public interface UserCoinService {
    void addUserCoin(Long userId, Integer amount);

    void updateUserCoin(Long userId, Integer amount);

    UserCoin getUserCoin(Long userId);
}
