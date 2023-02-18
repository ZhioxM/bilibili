package com.zx.bilibili.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.constant.UserConstant;
import com.zx.bilibili.domain.*;
import com.zx.bilibili.exception.ConditionException;
import com.zx.bilibili.mapper.RefreshTokenMapper;
import com.zx.bilibili.mapper.UserInfoMapper;
import com.zx.bilibili.mapper.UserMapper;
import com.zx.bilibili.service.CoinService;
import com.zx.bilibili.service.UserService;
import com.zx.bilibili.util.MD5Util;
import com.zx.bilibili.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 22:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Autowired
    private CoinService coinService;

    @Override
    @Transactional
    public void addUser(User user) {
        String phone = user.getPhone();
        if (StrUtil.isBlankIfStr(phone)) {
            throw new CommonException("手机号不能为空");
        }
        User dbUser = getUserByPhone(phone);
        if (ObjUtil.isNotEmpty(dbUser)) {
            throw new CommonException("该手机号已注册");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        // 前端传过来的是公钥加密后的密码
        String password = user.getPassword();
        if (StrUtil.isBlankIfStr(password)) {
            throw new CommonException("密码不能为空");
        }
        // RSA私钥解密获取明文密码
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        // 对明文密码进行摘要后存入数据库
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userMapper.insertSelective(user);

        //添加用户信息(后期用户可以自行修改用户信息)
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userInfoMapper.insertSelective(userInfo);

        // 添加用户默认权限
        // 添加用户初始的金币数
        coinService.addCoin(user.getId(), 0);
    }

    @Override
    public User getUserByPhone(String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtil.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public void login(String phone, String pwd) throws Exception {
        if (StrUtil.isBlankIfStr(phone) || StrUtil.isBlankIfStr(pwd)) {
            throw new CommonException("请输入正确的用户名和密码");
        }
        User dbUser = getUserByPhone(phone);
        String rawPwd; //  明文密码
        try {
            rawPwd = RSAUtil.decrypt(pwd);
        } catch (Exception e) {
            throw new CommonException("请输入正确的手机号和密码");
        }
        // 数据库中存储的是md5后的密码，是不可逆的；要验证密码是否正确，只能对前端的传过来的密码重做一次摘要，比较摘要后的产物是否一致
        String md5Pwd = MD5Util.sign(rawPwd, dbUser.getSalt(), "UTF-8");
        if (StrUtil.equals(md5Pwd, dbUser.getPassword())) {
            // 密码验证成功
            StpUtil.login(dbUser.getId());
        } else {
            throw new CommonException("请输入正确的手机号和密码");
        }
        // TODO token是怎么返回的
    }

    @Override
    public User getUser(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public UserInfo getUserInfo(Long userId) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andUserIdEqualTo(userId);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
        return userInfos.get(0);
    }

    @Override
    public void updateUserPwd(User user) throws Exception {
        Long id = user.getId();
        User dbUser = userMapper.selectByPrimaryKey(id);
        if (dbUser == null) {
            throw new ConditionException("用户不存在！");
        }
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andUserIdEqualTo(userInfo.getUserId());
        userInfoMapper.updateByExampleSelective(userInfo, userInfoExample);
    }

    @Override
    public List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList) {
        if (CollectionUtil.isEmpty(userIdList)) {
            return new ArrayList<>();
        }
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andUserIdIn(new ArrayList<>(userIdList));
        return userInfoMapper.selectByExample(userInfoExample);
    }

    @Override
    public Map<String, Object> loginForDts(User user) throws Exception {
        return null;
    }

    @Override
    public String refreshAccessToken(String refreshToken) throws Exception {
        return null;
    }

    @Override
    public List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList) {
        return null;
    }

    @Override
    public String getRefreshTokenByUserId(Long userId) {
        RefreshTokenExample refreshTokenExample = new RefreshTokenExample();
        refreshTokenExample.createCriteria().andUserIdEqualTo(userId);
        List<RefreshToken> refreshTokens = refreshTokenMapper.selectByExample(refreshTokenExample);
        return refreshTokens.get(0).getRefreshToken();
    }

    @Override
    public List<UserInfo> listUserInfo(Integer pageNum, Integer pageSize, String nick) {
        PageHelper.startPage(pageNum, pageSize);
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.setOrderByClause("id DESC");
        if (StrUtil.isNotBlank(nick)) {
            userInfoExample.createCriteria().andNickLike(nick);
        }
        return userInfoMapper.selectByExample(userInfoExample);
    }
}
