package com.zx.bilibili.service;

import com.zx.bilibili.domain.User;
import com.zx.bilibili.domain.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Mzx
 * @Date: 2022/12/20 20:06
 */
public interface UserService {
    @Transactional
    void addUser(User user);

    User getUserByPhone(String phone);

    void login(User user) throws Exception;

    User getUser(Long userId);

    UserInfo getUserInfo(Long userId);

    void updateUsers(User user) throws Exception;

    void updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);

    Map<String, Object> loginForDts(User user) throws Exception;

    String refreshAccessToken(String refreshToken) throws Exception;

    List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList);

    String getRefreshTokenByUserId(Long userId);

    List<UserInfo> listUserInfo(Integer pageNum, Integer pageSize, String nick);
}
