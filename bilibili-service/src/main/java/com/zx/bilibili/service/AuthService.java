package com.zx.bilibili.service;

import com.zx.bilibili.common.api.UserAuthority;

/**
 * @Author: Mzx
 * @Date: 2022/12/20 21:35
 */
public interface AuthService {
    UserAuthority getUserAuthorities(Long userId);

    void addUserDefaultRole(Long id);
}
