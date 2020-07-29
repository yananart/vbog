package cn.yananart.blog.service.impl;

import cn.yananart.blog.constant.ErrorCode;
import cn.yananart.blog.domain.pojo.User;
import cn.yananart.blog.exception.BlogException;
import cn.yananart.blog.repository.cache.UserCache;
import cn.yananart.blog.service.UserService;
import cn.yananart.blog.util.PasswordUtil;
import cn.yananart.blog.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yananart
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserCache userCache;

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public User getLoginUser() {
        String username = UserUtil.getLoginUser();
        User user = userCache.queryByUsername(username);
        if (user == null) {
            throw new BlogException(ErrorCode.USER_NO_LOGIN);
        }
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        User user = userCache.queryById(id);
        if (user == null) {
            throw new BlogException(ErrorCode.USER_NO_EXITS);
        }
        return user;
    }

    @Override
    public User getUserByName(String username) {
        User user = userCache.queryByUsername(username);
        if (user == null) {
            throw new BlogException(ErrorCode.USER_NO_EXITS);
        }
        return user;
    }

    @Override
    public Boolean addUser(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setRoles("USER");

        return null;
    }
}
