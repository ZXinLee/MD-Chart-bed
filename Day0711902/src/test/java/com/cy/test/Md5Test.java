package com.cy.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class Md5Test {

    /**
     * 说明: 实际项目中MD5盐要存储在数据库中
     * 登陆时根据用户名,将信息查询出来,基于密码和数据库的盐进行hash MD5加密
     * 再与数据库存储的密码对比
     */
    @Test
    void TestMd5(){
        //1. 定义密码,盐
        String pwd = "12345";
        String salt = "com.cy.ttpc";//可以是一个随机字符串
        //2. 对密码和盐加密
        String newPwd = DigestUtils.md5DigestAsHex((pwd + salt).getBytes());
        System.out.println(newPwd); //a0d675c59524b2ac1bc9c73093b42827
        System.out.println(newPwd.length()); //32
    }
}
