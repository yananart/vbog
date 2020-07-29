package cn.yananart.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 本项目自定义的配置信息
 *
 * @author Yananart
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "blog")
public class BlogConfig {

    /**
     * token的有效期(秒)
     */
    private Integer tokenExpire = 86400;

    /**
     * 密码加密工具类
     */
    private String passwordEncoder = "org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder";

    /**
     * 管理员用户名
     */
    private String adminUsername = "Yananart";

    /**
     * 管理员密码
     */
    private String adminPassword = "Yananart";

}
