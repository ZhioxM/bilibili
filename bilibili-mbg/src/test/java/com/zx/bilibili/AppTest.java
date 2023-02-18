package com.zx.bilibili;

import com.zx.bilibili.domain.AuthRole;
import com.zx.bilibili.mapper.AuthRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: Mzx
 * @Date: 2022/12/13 20:00
 */
@SpringBootTest
public class AppTest {
    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Test
    public void test1() {
        List<AuthRole> authRoles = authRoleMapper.selectByExample(null);
        for (AuthRole authRole : authRoles) {
            System.out.println(authRole);
        }
    }
}
