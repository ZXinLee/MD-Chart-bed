package sso.config;

import io.jsonwebtoken.impl.JwtMap;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sso.utli.JwtUtils;
import sso.utli.WebUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 构建配置安全对象
 * 1)认证规则(哪些资源必须认证才可访问)
 * 2)加密规则(添加用户时密码写到了数据库,登录时要将输入的密码与数据查询出的密码进行比对)
 * 3)认证成功怎么处理?(跳转页面,返回json)
 * 4)认证失败怎么处理?(跳转页面,返回json)
 * 5)没有登录就去访问资源系统怎么处理?(返回登录页面,返回json)
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证规则规则
     *
     * @param http 安全对象
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1. 关闭跨域攻击
        http.csrf().disable();
        //2. 配置form认证
        http.formLogin()
                //登录成功怎么处理?(向客户端返回json)
                .successHandler(authenticationSuccessHandler())//登陆成功
                //登录失败怎么处理?(向客户端返回json)
                .failureHandler(authenticationFailureHandler());//登陆失败
        //假如某个资源必须认证才可访问,那没有认证怎么办?(返回json)
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint());//提示需要认证
        //3. 所有资源都要认证
        http.authorizeRequests()
                .anyRequest()//所有请求->对应任意资源
                .authenticated();//必须认证
    }

    /**
     * 登陆成功以后的处理器
     *      返回登陆成功信息并返回令牌
     *
     * @return
     */
    private AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (httpServletRequest, httpServletResponse, authentication) -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("state", 200);
            map.put("message", "login ok");

            //思考除了返回这些信息,还要返回什么?(JWT令牌)
            //创建一个令牌对象(应该包含认证和权限信息),JWT格式的令牌可以满足这种需求
            HashMap<String, Object> userInfo = new HashMap<>();
            //1. 获取用户对象(包含了认证和授权信息)
            User user = (User) authentication.getPrincipal();
            // 获取用户名,并装到userInfo中
            userInfo.put("username", user.getUsername());
            //2. 获取用户权限
            List<String> authorities = new ArrayList<>();
            user.getAuthorities().forEach((authority) -> {
                authorities.add(authority.getAuthority());
            });
            userInfo.put("authorities",authorities);

            //创建token
            String token = JwtUtils.generatorToken(userInfo);
            map.put("token", token);
            WebUtils.writeJsonToClient(httpServletResponse, map);
        };
    }

    /**
     * 登陆失败处理器
     *
     * @return
     */
    private AuthenticationFailureHandler authenticationFailureHandler() {

        return (httpServletRequest, httpServletResponse, e) -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("state", 500);
            map.put("message", "username or password error");
            WebUtils.writeJsonToClient(httpServletResponse, map);
        };
    }

    /**
     * 未登录时访问资源给出提示需要登陆
     *
     * @return
     */
    private AuthenticationEntryPoint authenticationEntryPoint() {

        return (httpServletRequest, httpServletResponse, e) -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("state", 500);
            map.put("message", "please login");
            WebUtils.writeJsonToClient(httpServletResponse, map);
        };
    }
}
