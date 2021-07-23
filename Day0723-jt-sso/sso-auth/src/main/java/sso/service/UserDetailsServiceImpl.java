package sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 说明: 学框架首先要学规范,逻辑
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * @Autowired 注解描述属性时,注入规则
     * Spring先ByType,只有一个就直接注入
     * 如果有多个,ByName查找,如果有与Bean相同的名字则注入,否则抛异常
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 当我们执行登录操作时,底层会通过过滤器等对象,调用这个方法.
     *
     * @param username 这个参数为页面输出的用户名
     * @return 一般是从数据库基于用户名查询到的用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        //1.基于用户名从数据库查询用户信息
        //User user=userMapper.selectUserByUsername(username);
        if (!"jack".equals(username))//假设这是从数据库查询的信息
            throw new UsernameNotFoundException("user not exists");
        //2.将用户信息封装到UserDetails对象中并返回
        //假设这个密码是从数据库查询出来的
        String encodedPwd = passwordEncoder.encode("123456");
        //假设这个权限信息也是从数据库查询到的
        //假如分配权限的方式是角色,编写字符串时用"ROLE_"做前缀
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        "ROLE_admin,ROLE_normal,sys:res:retrieve,sys:res:create");
        //这个user是SpringSecurity提供的UserDetails接口的实现,用于封装用户信息
        //后续我们也可以基于需要自己构建UserDetails接口的实现
        User user = new User(username, encodedPwd, grantedAuthorities);
        return user;
    }
}
