package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.zx.bilibili.common.api.CommonPage;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.User;
import com.zx.bilibili.domain.UserFollowing;
import com.zx.bilibili.domain.UserInfo;
import com.zx.bilibili.service.UserFollowingService;
import com.zx.bilibili.service.UserService;
import com.zx.bilibili.util.RSAUtil;
import com.zx.bilibili.vo.UserInfoVo;
import com.zx.bilibili.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 22:27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFollowingService userFollowingService;

    @ApiOperation("获取公钥，用于前端RSA加密(对密码进行加密)")
    @GetMapping("/rsa-pks")
    public CommonResult<String> getRsaPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return CommonResult.success(pk);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return CommonResult.success(null);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<String> login(String phone, String pwd) throws Exception {
        // 1. 先登录上
        userService.login(phone, pwd);
        // 2. 获取token
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // 3. 将token返回给前端
        return CommonResult.success(tokenInfo.tokenValue);
    }

    @ApiOperation("查询当前用户信息")
    @SaCheckLogin
    @GetMapping("/info")
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

    @ApiOperation("修改用户密码")
    @SaCheckLogin
    @PutMapping("/pwd")
    public CommonResult<String> updateUsers(@RequestBody User user) throws Exception {
        // 获取当前的登录ID
        long loginId = StpUtil.getLoginIdAsLong();
        user.setId(loginId);
        userService.updateUserPwd(user);
        return CommonResult.success(null);
    }

    @ApiOperation("更新当前用户的详细信息")
    @SaCheckLogin
    @PutMapping("/info")
    public CommonResult<String> updateUserInfos(@RequestBody UserInfo userInfo) {
        long loginId = StpUtil.getLoginIdAsLong();
        userInfo.setUserId(loginId);
        userService.updateUserInfos(userInfo);
        return CommonResult.success(null);
    }

    @ApiOperation("分页查询所有用户信息")
    @GetMapping("/info/list")
    public CommonResult<CommonPage<UserInfoVo>> pageListUserInfos(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                                String nick){
        Long userId = StpUtil.getLoginIdAsLong();
        List<UserInfo> userInfoList = userService.listUserInfo(pageNum, pageSize, nick);
        Set<Long> followingId = userFollowingService.getUserFollowing(userId).stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfoVo> userInfoVoList = userInfoList.stream().map((o) -> {
            // 判断是否回关(TODO 优化，可以先一次将用户的关注列表全部查出来，再判断是当前用户有没有关注过这位用户)
            // UserFollowing db = userFollowingService.getUserFollowed(userId, o.getUserId());
            boolean followed = followingId.contains(o.getUserId());
            return new UserInfoVo(o.getId(),
                                  o.getUserId(),
                                  o.getNick(),
                                  o.getAvatar(),
                                  o.getGender(),
                                  o.getBirth(),
                                  o.getCreateTime(),
                                  o.getUpdateTime(),
                                  o.getSign(),
                                  followed);
        }).collect(Collectors.toList());
        return CommonResult.success(CommonPage.restPage(userInfoVoList));
    }

    @ApiOperation("退出登录")
    @SaCheckLogin
    @DeleteMapping("/logout")
    public CommonResult<?> logout(HttpServletRequest request) {
        Long userId = StpUtil.getLoginIdAsLong();
        StpUtil.logout(userId);
        return CommonResult.success(null);
    }
}
