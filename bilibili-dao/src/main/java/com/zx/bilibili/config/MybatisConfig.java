package com.zx.bilibili.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 19:54
 */
@Configuration
@MapperScan({"com.zx.bilibili.mapper"}) // 配置接口扫描
public class MybatisConfig {

}
