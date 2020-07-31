package cn.yananart.blog.service.impl;

import cn.yananart.blog.config.BlogConfig;
import cn.yananart.blog.constant.ErrorCode;
import cn.yananart.blog.domain.pojo.Token;
import cn.yananart.blog.domain.pojo.User;
import cn.yananart.blog.exception.BlogException;
import cn.yananart.blog.repository.cache.TokenCache;
import cn.yananart.blog.repository.cache.UserCache;
import cn.yananart.blog.repository.mapper.UserMapper;
import cn.yananart.blog.service.LoginService;
import cn.yananart.blog.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author Yananart
 */
@Slf4j
@Service
@DependsOn("passwordUtil")
public class LoginServiceImpl implements LoginService {

    private UserCache userCache;
    private TokenCache tokenCache;
    private BlogConfig blogConfig;
    private UserMapper userMapper;

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Autowired
    public void setTokenCache(TokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    @Autowired
    public void setBlogConfig(BlogConfig blogConfig) {
        this.blogConfig = blogConfig;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.isEmpty(username)) {
            User user = userCache.queryByUsername(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                        username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles())
                );
            }
            throw new UsernameNotFoundException("no " + username);
        }
        throw new UsernameNotFoundException("username is null");
    }

    /**
     * 项目启动后将配置中的管理员入库或更新
     */
    @PostConstruct
    public void checkAndAddAdmin() {
        String username = blogConfig.getAdminUsername();
        String password = PasswordUtil.encode(blogConfig.getAdminPassword());
        User user = userCache.queryByUsername(username);
        if (user == null) {
            // 当前用户不存在
            log.info("录入默认管理员账号{}", username);
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRoles("ADMIN,USER");
            try {
                userMapper.insertUser(user);
            } catch (Exception e) {
                log.error("录入默认管理员用户失败", e);
                throw new BlogException(ErrorCode.INIT_ADMIN_ERROR);
            }
            log.info("录入默认管理员账号{} 成功", username);
        } else {
            // 用户存在的情况下，要看是否强制更新
            if (blogConfig.getAdminForceUpdate()) {
                log.info("强制更新默认管理员信息");
                user.setPassword(password);
                user.setRoles("ADMIN,USER");
                try {
                    userMapper.updateByUsername(user);
                    userCache.deleteByUsername(username);
                    Token token = tokenCache.queryByUsername(username);
                    if (token != null) {
                        userCache.deleteByToken(token.getToken());
                        tokenCache.deleteByUsername(username);
                    }
                } catch (Exception e) {
                    log.error("更新默认管理员用户{}失败", username, e);
                    throw new BlogException(ErrorCode.INIT_ADMIN_ERROR);
                }
                log.info("更新管理员{}信息 成功", username);
            }
        }
    }
}
