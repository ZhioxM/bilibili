package com.zx.bilibili.exception.handler;

import com.zx.bilibili.common.api.CommonException;
import com.zx.bilibili.common.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Mzx
 * @Date: 2022/12/25 15:40
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(CommonException.class)
    public CommonResult commonExceptionHandler(CommonException e) {
        logger.error("服务器发生异常: " + e.getMessage());
        return CommonResult.failed(e.getMessage());
    }
}
