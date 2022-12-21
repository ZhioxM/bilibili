package com.zx.bilibili.service;

import com.zx.bilibili.common.api.UserAuthority;
import com.zx.bilibili.domain.UserRole;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/20 21:35
 */
public interface UserAuthorityService {
    UserAuthority getUserAuthorities(Long userId);

    void addUserDefaultRole(Long id);
}
