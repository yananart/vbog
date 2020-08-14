package cn.yananart.base.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * 用户工具
 *
 * @author Yananart
 */
public class UserUtil {

    /**
     * 获取当前用户
     *
     * @return 用户名
     */
    public static String getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return ((User) authentication.getPrincipal()).getUsername();
            }
        }
        return null;
    }

}
