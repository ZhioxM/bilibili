package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.constant.UserConstant;
import com.zx.bilibili.domain.FollowingGroup;
import com.zx.bilibili.domain.User;
import com.zx.bilibili.domain.UserFollowing;
import com.zx.bilibili.domain.UserInfo;
import com.zx.bilibili.service.FollowingGroupService;
import com.zx.bilibili.service.UserFollowingService;
import com.zx.bilibili.service.UserService;
import com.zx.bilibili.util.RSAUtil;
import com.zx.bilibili.vo.UserFollowedVo;
import com.zx.bilibili.vo.UserFollowingVo;
import com.zx.bilibili.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 22:27
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private FollowingGroupService followingGroupService;

    @ApiOperation("获取公钥，用于前端RSA加密(对密码进行加密)")
    @GetMapping("/rsa-pks")
    public CommonResult<String> getRsaPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return CommonResult.success(pk);
    }

    @ApiOperation("用户注册")
    @PostMapping("/users")
    public CommonResult<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return CommonResult.success(null);
    }

    @ApiOperation("用户登录")
    @PostMapping("/user-tokens")
    public CommonResult<String> login(@RequestBody User user) throws Exception {
        // 1. 先登录上
        userService.login(user);
        // 2. 获取token
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 3. 将token返回给前端
        return CommonResult.success(tokenInfo.tokenValue);
    }

    @ApiOperation("查询当前用户信息")
    @SaCheckLogin
    @GetMapping("/users")
    public CommonResult<UserVo> getUserInfo() {
        long userId = StpUtil.getLoginIdAsLong();
        User user = userService.getUser(userId);
        UserInfo userInfo = userService.getUserInfo(userId);
        UserVo userVo = new UserVo();
        userVo.setId(userId);
        userVo.setPhone(user.getPhone());
        userVo.setPassword(user.getPassword());
        userVo.setSalt(user.getSalt());
        userVo.setEmail(user.getEmail());
        userVo.setCreatetime(user.getCreateTime());
        userVo.setUpdatetime(user.getUpdateTime());
        userVo.setUserInfo(userInfo);
        return CommonResult.success(userVo);
    }

    @ApiOperation("更新当前用户信息")
    @SaCheckLogin
    @PutMapping("/users")
    public CommonResult<String> updateUsers(@RequestBody User user) throws Exception {
        // 获取当前的登录ID
        long loginId = StpUtil.getLoginIdAsLong();
        user.setId(loginId);
        userService.updateUsers(user);
        return CommonResult.success(null);
    }

    @ApiOperation("更新当前用户的详细信息")
    @SaCheckLogin
    @PutMapping("/user-infos")
    public CommonResult<String> updateUserInfos(@RequestBody UserInfo userInfo) {
        long loginId = StpUtil.getLoginIdAsLong();
        userInfo.setUserId(loginId);
        userService.updateUserInfos(userInfo);
        return CommonResult.success(null);
    }

    // @GetMapping("/user-infos")
    // public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick){
    //     Long userId = userSupport.getCurrentUserId();
    //     JSONObject params = new JSONObject();
    //     params.put("no", no);
    //     params.put("size", size);
    //     params.put("nick", nick);
    //     params.put("userId", userId);
    //     PageResult<UserInfo> result = userService.pageListUserInfos(params);
    //     if(result.getTotal() > 0){
    //         List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
    //         result.setList(checkedUserInfoList);
    //     }
    //     return new JsonResponse<>(result);
    // }

    // @PostMapping("/user-dts")
    // public CommonResult<Map<String, Object>> loginForDts(@RequestBody User user) throws Exception {
    //     Map<String, Object> map = userService.loginForDts(user);
    //     return CommonResult.success(map);
    // }

    @ApiOperation("退出登录")
    @SaCheckLogin
    @DeleteMapping("/refresh-tokens")
    public CommonResult<?> logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("refreshToken");
        Long userId = StpUtil.getLoginIdAsLong();
        userService.logout(refreshToken, userId);
        return CommonResult.success(null);
    }

    @PostMapping("/access-tokens")
    public CommonResult<String> refreshAccessToken(HttpServletRequest request) throws Exception {
        String refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.refreshAccessToken(refreshToken);
        return CommonResult.success(accessToken);
    }

    @ApiOperation("获取用户的关注列表")
    @SaCheckLogin
    @GetMapping("/user-followings")
    public CommonResult<List<UserFollowingVo>> getUserFollowings() {
        List<UserFollowingVo> result = new ArrayList<>();
        Long userId = StpUtil.getLoginIdAsLong();
        List<UserFollowing> userFollowingList = userFollowingService.getUserFollowing(userId);
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
    @GetMapping("/user-fans")
    public CommonResult<List<UserFollowedVo>> getUserFollowed() {
        List<UserFollowedVo> result = new ArrayList<>();
        Long userId = StpUtil.getLoginIdAsLong();
        List<UserFollowing> followedList = userFollowingService.getUserFollowed(userId);
        // 获取粉丝ID
        Set<Long> followedIdSet = followedList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        for (Long followedId : followedIdSet) {
            UserFollowedVo vo = new UserFollowedVo();
            vo.setUserId(followedId);
            // 查询粉丝信息
            vo.setUserInfo(userService.getUserInfo(followedId));
            // 判断自己有没有回关这位粉丝
            UserFollowing db = userFollowingService.getUserFollowed(userId, followedId);
            if (ObjUtil.isNotEmpty(db)) {
                vo.setFollowed(true);
            }
            result.add(vo);
        }
        return CommonResult.success(result);

    }
}
