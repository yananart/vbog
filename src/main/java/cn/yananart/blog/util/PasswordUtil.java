package cn.yananart.blog.util;

import cn.yananart.blog.config.BlogConfig;
import cn.yananart.blog.constant.ErrorCode;
import cn.yananart.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码工具
 *
 * @author Yananart
 */
@Slf4j
@Component
public class PasswordUtil {

    private static PasswordEncoder passwordEncoder;

    /**
     * 通过配置实例化当前加密类
     *
     * @param blogConfig 自定义配置
     */
    @Autowired
    public void setBlogConfig(BlogConfig blogConfig) {
        Class<?> clazz = blogConfig.getPasswordEncoder();
        Object obj;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("实例化PasswordEncoder类失败", e);
            throw new BlogException(ErrorCode.INIT_PASSWORD_ENCODER_ERROR);
        }
        if (obj instanceof PasswordEncoder) {
            passwordEncoder = (PasswordEncoder) obj;
        } else {
            log.error("当前类非PasswordEncoder接口的实现类");
            throw new BlogException(ErrorCode.INIT_PASSWORD_ENCODER_ERROR);
        }
    }

    /**
     * 获取密码加密对象
     *
     * @return 密码加密对象
     */
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * 加密
     *
     * @param password 明文密码
     * @return 密文
     */
    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

}
