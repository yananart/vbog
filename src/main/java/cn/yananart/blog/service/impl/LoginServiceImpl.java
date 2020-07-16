package cn.yananart.blog.service.impl;

import cn.yananart.blog.domain.pojo.User;
import cn.yananart.blog.repository.cache.UserCache;
import cn.yananart.blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Yananart
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private UserCache userCache;

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.isEmpty(username)) {
            User user = userCache.queryByUsername(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                        username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin")
                );
            }
            throw new UsernameNotFoundException("no " + username);
        }
        throw new UsernameNotFoundException("username is null");
    }
}
