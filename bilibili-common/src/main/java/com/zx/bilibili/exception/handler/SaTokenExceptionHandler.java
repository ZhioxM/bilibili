package com.zx.bilibili.exception.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.NotSafeException;
import com.zx.bilibili.common.api.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 14:59
 */
@RestControllerAdvice
public class SaTokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public CommonResult handlerNotLoginException(NotLoginException nle) {
        System.out.println("1111");
        // 不同异常返回不同状态码
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供Token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "未提供有效的Token";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "登录信息已过期，请重新登录";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "您的账户已在另一台设备上登录，如非本人操作，请立即修改密码";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "已被系统强制下线";
        } else {
            message = "当前会话未登录";
        }
        // 返回给前端
        return CommonResult.unauthorized(message);
    }

    @ExceptionHandler
    public CommonResult handlerNotRoleException(NotRoleException e) {
        return CommonResult.forbidden("无此角色：" + e.getRole());
    }

    @ExceptionHandler
    public CommonResult handlerNotPermissionException(NotPermissionException e) {
        return CommonResult.forbidden("无此权限：" + e.getCode());
    }

    @ExceptionHandler
    public CommonResult handlerNotSafeException(NotSafeException e) {
        return CommonResult.validateFailed("二级认证异常：" + e.getMessage());
    }
}