package com.zx.bilibili.service;

import com.zx.bilibili.domain.UserCoin;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 19:29
 */
public interface CoinService {
    void addCoin(Long userId, Integer amount);

    void updateCoin(Long userId, Integer amount);

    UserCoin getCoin(Long userId);
}
