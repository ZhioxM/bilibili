package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.common.api.UserAuthority;
import com.zx.bilibili.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Mzx
 * @Date: 2022/12/20 21:26
 */
@RestController
public class AuthController {

    private AuthService authService;

    @ApiOperation("获取用户权限列表")
    @SaCheckLogin
    @GetMapping("/user/auth")
    public CommonResult<UserAuthority> getUserAuthorities() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserAuthority userAuthorities = authService.getUserAuthorities(userId);
        return CommonResult.success(userAuthorities);
    }
}
