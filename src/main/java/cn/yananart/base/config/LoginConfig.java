package cn.yananart.base.config;

import cn.yananart.base.constant.ErrorCode;
import cn.yananart.base.domain.pojo.Token;
import cn.yananart.base.domain.res.BaseRes;
import cn.yananart.base.filter.AuthenticationFilter;
import cn.yananart.base.filter.CertificationFilter;
import cn.yananart.base.repository.cache.UserCache;
import cn.yananart.base.service.LoginService;
import cn.yananart.base.util.PasswordUtil;
import cn.yananart.base.util.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 登录配置
 *
 * @author Yananart
 */
@Configuration
public class LoginConfig extends WebSecurityConfigurerAdapter {

    /**
     * 登录服务
     */
    private LoginService loginService;
    /**
     * 用户数据缓存
     */
    private UserCache userCache;
    /**
     * 密码加密工具类
     */
    private PasswordUtil passwordUtil;
    /**
     * 统一认证
     */
    private CertificationFilter certificationFilter;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Autowired
    public void setPasswordUtil(PasswordUtil passwordUtil) {
        this.passwordUtil = passwordUtil;
    }

    @Autowired
    public void setCertificationFilter(CertificationFilter certificationFilter) {
        this.certificationFilter = certificationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里加密对象的注入通过自定义配置里的实例化后注入
        auth.userDetailsService(loginService).passwordEncoder(passwordUtil.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 未认证返回
        http.exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("text/json;charset=utf-8");
                    BaseRes res = ResultUtil.returnError(ErrorCode.NO_AUTH_ERROR);
                    httpServletResponse.getWriter().write(JSON.toJSONString(res));
                });

        // session管理
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 替换默认的
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 统一认证
        http.addFilterBefore(certificationFilter, UsernamePasswordAuthenticationFilter.class);
        // 登录管理
        http.formLogin().permitAll();

        // 登录认证url
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/manager/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated();

        // 注销处理
        http.logout()
                .logoutUrl("/auth/logout")
                // 注销的时候并不清掉token
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("text/json;charset=utf-8");
                    httpServletResponse.getWriter().write(JSON.toJSONString(ResultUtil.returnSuccess()));
                })
                .invalidateHttpSession(true)
                .permitAll();
    }

    /**
     * 统一登录
     */
    @Bean
    protected AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("text/json;charset=utf-8");
            // 生成一个token，并写入缓存
            Token token = userCache.cacheByUsernameForToken(authentication.getName());
            BaseRes res = ResultUtil.returnSuccessObject(token);
            response.getWriter().write(JSON.toJSONString(res));
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("text/json;charset=utf-8");
            BaseRes res = ResultUtil.returnError(-1000, exception.getMessage());
            response.getWriter().write(JSON.toJSONString(res));
        });
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
