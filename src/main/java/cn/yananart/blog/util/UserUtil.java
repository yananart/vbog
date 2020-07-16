package cn.yananart.blog.util;

import cn.yananart.blog.domain.pojo.User;
import cn.yananart.blog.repository.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户工具
 *
 * @author Yananart
 */
@Component
public class UserUtil {

    private static UserCache userCache;

    @Autowired
    public UserUtil(UserCache userCache) {
        UserUtil.userCache = userCache;
    }

    public static User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
                return userCache.queryByUsername(username);
            }
        }
        return null;
    }

}
