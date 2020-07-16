package cn.yananart.blog.config;

import cn.yananart.blog.filter.AuthenticationFilter;
import cn.yananart.blog.repository.cache.UserCache;
import cn.yananart.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 登录配置
 *
 * @author Yananart
 */
@Configuration
public class LoginConfig extends WebSecurityConfigurerAdapter {

    private LoginService loginService;
    private UserCache userCache;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 未认证返回
        http.exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("text/json;charset=utf-8");
                    httpServletResponse.getWriter().write("{\"code\":-100,\"message\":\"no user auth\"}");
                });

        // session管理
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .invalidSessionUrl("/session/invalid");

        // 替换默认的
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 登录管理
        http.formLogin().permitAll();

        // 登录认证url
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/session/**").permitAll()
                .anyRequest().authenticated();

        // 注销处理
        http.logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("text/json;charset=utf-8");
                    httpServletResponse.getWriter().write("{\"code\":0,\"message\":\"success\"}");
                })
                .invalidateHttpSession(true)
                .permitAll();
    }

    @Bean
    protected AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 生成一个token，并
            response.setContentType("text/json;charset=utf-8");

            response.getWriter().write("{\"code\":0,\"message\":\"" + authentication.getName() + " login success\"}");
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("{\"code\":-1,\"message\":\"" + exception.getMessage() + "\"}");
        });
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
