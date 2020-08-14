package cn.yananart.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 本项目自定义的配置信息
 *
 * @author Yananart
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "this.base")
public class BaseConfig {

    /**
     * token的有效期(秒)
     */
    private Integer tokenExpire = 86400;

    /**
     * 密码加密工具类
     */
    private Class<? extends PasswordEncoder> passwordEncoder = org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.class;

    /**
     * 管理员用户名
     */
    private String adminUsername = "Yananart";

    /**
     * 管理员密码
     */
    private String adminPassword = "Yananart";

    /**
     * 是否强制更新admin用户
     */
    private Boolean adminForceUpdate = false;

}
