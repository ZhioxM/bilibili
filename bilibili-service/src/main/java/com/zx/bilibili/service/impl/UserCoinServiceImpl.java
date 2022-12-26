package com.zx.bilibili.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zx.bilibili.domain.UserCoin;
import com.zx.bilibili.domain.UserCoinExample;
import com.zx.bilibili.mapper.UserCoinMapper;
import com.zx.bilibili.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 19:31
 */
@Service
public class UserCoinServiceImpl implements UserCoinService {

    @Autowired
    private UserCoinMapper userCoinMapper;

    @Override
    public void addUserCoin(Long userId, Integer amount) {
        UserCoin userCoin = new UserCoin();
        userCoin.setUserId(userId);
        userCoin.setAmount(amount);
        userCoin.setCreateTime(new Date());
        userCoinMapper.insert(userCoin);
    }

    @Override
    public void updateUserCoin(Long userId, Integer amount) {
        UserCoinExample userCoinExample = new UserCoinExample();
        userCoinExample.createCriteria().andUserIdEqualTo(userId);
        UserCoin userCoin = new UserCoin();
        userCoin.setAmount(amount);
        userCoin.setUpdateTime(new Date());
        userCoinMapper.updateByExampleSelective(userCoin, userCoinExample);
    }

    @Override
    public UserCoin getUserCoin(Long userId) {
        UserCoinExample userCoinExample = new UserCoinExample();
        userCoinExample.createCriteria().andUserIdEqualTo(userId);
        List<UserCoin> db = userCoinMapper.selectByExample(userCoinExample);
        if (CollectionUtil.isEmpty(db)) {
            return null;
        }
        return db.get(0);
    }
}
