package com.cy.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BCryptTest {

    @Test
    void testEncode() {
        //1. 定义一个密码
        String pwd = "root";
        //2. 定义加密对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //3. 加密 -- 对密码进行随机盐hash算法,不可逆加密
        String encode = passwordEncoder.encode(pwd);
        System.out.println(encode); //$2a$10$nlYqP82qd7tVO15nnL9mU.sJKs8tLAFKQYxN7kZkhMVVEe7GONLdC
        System.out.println(encode.length()); //60
        //4. 密码匹配测试 -- 匹配时底层会对密码再次加密
        boolean flag = passwordEncoder.matches(pwd, encode);
        System.out.println("flag = " + flag); //flag = true
    }
}
