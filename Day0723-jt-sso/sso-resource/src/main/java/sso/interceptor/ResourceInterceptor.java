package sso.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;
import sso.utli.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 令牌拦截器: 拦截客户端向服务器请求时传递的令牌
 */
public class ResourceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //1. 从请求中获取Token对象(获取方式根据传递方式来决定)
        String token = request.getHeader("token");//通过请求头的方式来传递
        //String token1 = request.getParameter("token");//通过URL传参
        //2. 验证Token是否存在
        if (token == null || "".equals(token)) throw new RuntimeException("请先登录");
        //3. 验证Token是否过期
        if (JwtUtils.isTokenExpired(token)) throw new RuntimeException("登陆超时,请重新登陆");
        //4. 解析Token中的认证和权限信息(一般储存在jwt的负载部分)
        Claims claims = JwtUtils.getClaimsFromToken(token);
        List<String> list = (List<String>) claims.get("authorities");
        //5. 封装 储存 认证和权限信息
        UserDetails userDetails = User.builder()
                .username((String) claims.get("username"))
                .password("")
                .authorities(list.toArray(new String[]{}))
                .build();
        //5.2 构建Security权限交互对象(固定写法)
        PreAuthenticatedAuthenticationToken authenticationToken =
                new PreAuthenticatedAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities());
        //5.3 将权限交互对象与当前请求进行绑定
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        //5.4 将认证之后的token存储到Security上下文(会话对象)
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return true;
    }
}
