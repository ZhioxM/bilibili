package com.zx.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 22:14
 */
@SpringBootApplication
public class BilibiliApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(BilibiliApp.class, args);
    }
}
