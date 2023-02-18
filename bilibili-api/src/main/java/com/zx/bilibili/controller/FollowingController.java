package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.constant.UserConstant;
import com.zx.bilibili.domain.FollowingGroup;
import com.zx.bilibili.domain.UserFollowing;
import com.zx.bilibili.domain.UserInfo;
import com.zx.bilibili.service.FollowingGroupService;
import com.zx.bilibili.service.FollowingService;
import com.zx.bilibili.service.UserService;
import com.zx.bilibili.vo.UserFollowedVo;
import com.zx.bilibili.vo.UserFollowingVo;
import com.zx.bilibili.vo.UserInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/20 15:42
 */
@RestController
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserService userService;

    @ApiOperation("添加用户关注")
    @SaCheckLogin
    @PostMapping("/follow")
    public CommonResult<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId = StpUtil.getLoginIdAsLong();
        userFollowing.setUserId(userId);
        followingService.addUserFollowing(userFollowing);
        return CommonResult.success(null);
    }

    @ApiOperation("获取用户的关注列表")
    @SaCheckLogin
    @GetMapping("/follow/list")
    public CommonResult<List<UserFollowingVo>> getUserFollowings() {
        List<UserFollowingVo> result = new ArrayList<>();
        Long userId = StpUtil.getLoginIdAsLong();
        List<UserFollowing> userFollowingList = followingService.getUserFollowing(userId);
        // 获取关注ID列表
        Set<Long> followingIdSet = userFollowingList.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        // 所有关注的用户信息
        List<UserInfo> allFollowingUserInfoList = userService.getUserInfoByUserIds(followingIdSet);
        // 转化为键值对，后面用到
        Map<Long, UserInfo> userInfoMap = allFollowingUserInfoList.stream().collect(Collectors.toMap(UserInfo::getUserId, Function.identity()));
        // 全部关注
        UserFollowingVo allFollowingVo = new UserFollowingVo();
        allFollowingVo.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allFollowingVo.setFollowingUserInfoList(allFollowingUserInfoList);
        result.add(allFollowingVo);

        // 分组关注
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        // 遍历所有分组
        for (FollowingGroup group : groupList) {
            UserFollowingVo vo = new UserFollowingVo();
            List<UserInfo> groupFollowingUserInfoList = new ArrayList<>();
            for (UserFollowing userFollowing : userFollowingList) {
                // 获取当前分组下的所有关注的用户ID，查询他们的信息
                if (ObjUtil.equal(group.getId(), userFollowing.getGroupId())) {
                    UserInfo followingUserInfo = userInfoMap.get(userFollowing.getFollowingId());
                    groupFollowingUserInfoList.add(followingUserInfo);
                }
            }
            vo.setUserId(group.getUserId());
            vo.setId(group.getId());
            vo.setType(group.getType());
            vo.setName(group.getName());
            vo.setFollowingUserInfoList(groupFollowingUserInfoList);
            result.add(vo);
        }
        return CommonResult.success(result);
    }


    @ApiOperation("获取用户的粉丝列表")
    @SaCheckLogin
    @GetMapping("/fan/list")
    public CommonResult<List<UserFollowedVo>> getUserFollowed() {
        List<UserFollowedVo> result = new ArrayList<>();
        Long userId = StpUtil.getLoginIdAsLong();
        List<UserFollowing> followedList = followingService.getUserFollowed(userId);
        // 获取粉丝ID
        Set<Long> followedIdSet = followedList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        for (Long followedId : followedIdSet) {
            UserFollowedVo vo = new UserFollowedVo();
            vo.setUserId(followedId);
            // 查询粉丝信息
            UserInfo userInfo = userService.getUserInfo(followedId);
            // 判断自己有没有回关这位粉丝
            UserFollowing db = followingService.getUserFollowed(userId, followedId);
            boolean followed = ObjUtil.isNotEmpty(db);
            UserInfoVo userInfoVo = new UserInfoVo(
                    userInfo.getId(),
                    userInfo.getUserId(),
                    userInfo.getNick(),
                    userInfo.getAvatar(),
                    userInfo.getGender(),
                    userInfo.getBirth(),
                    userInfo.getSign(),
                    followed
            );
            vo.setUserInfoVo(userInfoVo);
            result.add(vo);
        }
        return CommonResult.success(result);

    }


    @ApiOperation("添加用户关注分组")
    @SaCheckLogin
    @PostMapping("/follow/group")
    public CommonResult<Long> addUserFlowingGroups(@RequestBody FollowingGroup followingGroup) {
        Long userId = StpUtil.getLoginIdAsLong();
        followingGroup.setUserId(userId);
        Long groupId = followingGroupService.addUserFollowingGroup(followingGroup);
        return CommonResult.success(groupId);
    }

    @ApiOperation("获取用户关注分组")
    @SaCheckLogin
    @GetMapping("/follow/group/list")
    public CommonResult<List<FollowingGroup>> getUserFollowingGroups(){
        Long userId = StpUtil.getLoginIdAsLong();
        List<FollowingGroup> list = followingGroupService.getUserFollowingGroups(userId);
        return CommonResult.success(list);
    }
}
