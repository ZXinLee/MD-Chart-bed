package com.cy.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
        //访问被拒绝
        //方案一:重定向
        //httpServletResponse.sendRedirect("https://xxxxxx");

        //方案二:向客户端响应Json字符串
        //2.1设置响应数据的编码
        httpServletResponse.setCharacterEncoding("utf-8");
        //2.2告诉浏览器响应数据的内容类型以及编码
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //2.3获取输出流对象
        PrintWriter out = httpServletResponse.getWriter();
        //2.4 将数据输出到客户端
        //2.4.1 封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("state", HttpServletResponse.SC_UNAUTHORIZED);//401
        map.put("message", "请先登陆");
        //2.4.2将数据转换为json格式字符串
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        //2.4.3输出数据
        out.println(jsonStr);
        out.flush();
    }
}
