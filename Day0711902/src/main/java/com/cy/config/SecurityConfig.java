package com.cy.config;

import com.cy.config.handler.DefaultAccessDeniedExceptionHandler;
import com.cy.config.handler.DefaultAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 由@Configuration注解描述的类为spring中的配置类,配置类会在spring
 * 工程启动时优先加载,在配置类中通常会对第三方资源进行初始配置.
 *
 * @EnableGlobalMethodSecurity 此注解由SpringSecurity提供,用于描述权限配置类
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //对http进行权限控制配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //1. 关闭跨域攻击
        http.csrf().disable();
        //2. 配置登陆url(登陆表单)
        http.formLogin()
                .loginPage("/login.html")//登陆页
                .loginProcessingUrl("/login")//登陆请求路径
                .failureUrl("/login.html?error")//登陆失败(默认跳转页面)
                .defaultSuccessUrl("/index.html");//登陆成功跳转 -- 请求重定向
                //.successForwardUrl("/index.html");//登陆成功后的转发 -- 请求转发(controller中的方法)
                //.successHandler(new DefaultAuthenticationSuccessHandler())//登陆成功返回请求头的json
                //.failureHandler(new DefaultAuthenticationFailureHandler());
        http.logout()//退出操作
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout.html?logout");

        //设置认证与拒绝访问的异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint())//未认证
                .accessDeniedHandler(new DefaultAccessDeniedExceptionHandler());//没有权限

        //3. 放行URL(无需登陆即可访问)
        http.authorizeRequests()
                .antMatchers("/login.html")//放行资源
                .permitAll()//允许直接访问
                .anyRequest().authenticated();//除了以上资源,其他资源必须认证才可访问
    }


    /**
     * @return
     * @Bean 注解在@Configuration注解描述类中描述方法
     * 用于告诉框架这个方法的返回值会交给Spring管理
     * 并给该对象起个名字,默认与方法名[passwordEncoder()]相同,也可以自定义
     */
    @Bean
    //@Bean("xxxxx") --自定义名字
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
