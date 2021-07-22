package com.cy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
class Day0722ApplicationTests {

    //定义秘钥盐
    private String secret = "CGB210404";

    /**
     * 测试创建和解析token的过程
     */
    @Test
    void contextLoads() {
        //1. 创建token(包含三部分信息: 头信息 负载信息 签名信息)
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "jack");
        map.put("permissions", "sys:res:creat");
        String token = Jwts.builder()//Jwt规范 -- 基于Jwt的规范创建token对象(令牌-通票ticket)
                .setSubject("token")
                .setClaims(map)//自定义负载信息(存储登陆用户信息)
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000))//失效时间 有效时长 -- 30s
                .signWith(SignatureAlgorithm.HS256, secret)//签名加密及秘钥盐
                .compact();//生成token
        System.out.println("token = " + token);
        //token =
        // eyJhbGciOiJIUzI1NiJ9
        // .eyJwZXJtaXNzaW9ucyI6InN5czpyZXM6Y3JlYXQiLCJleHAiOjE2MjY5MzU0NTcsImlhdCI6MTYyNjkzNTQyNywidXNlcm5hbWUiOiJqYWNrIn0
        // .w9VBry0GXsuYpjoNLcVFOJ7cVjk1iUyrr-J3CHwuseE

        //2. 解析token内容
        Claims body = Jwts.parser()//获取解析器对象
                .setSigningKey(secret)//解析时需要的秘钥
                .parseClaimsJws(token)//获取token中的负载
                .getBody();//获取具体负载内容
        System.out.println(body);//{permissions=sys:res:creat, exp=1626936546, iat=1626936516, username=jack}
        System.out.println(body.get("username"));
    }

}
