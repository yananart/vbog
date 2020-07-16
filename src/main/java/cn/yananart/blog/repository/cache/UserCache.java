package cn.yananart.blog.repository.cache;

import cn.yananart.blog.domain.pojo.Token;
import cn.yananart.blog.domain.pojo.User;

/**
 * 用户缓存
 *
 * @author Yananart
 */
public interface UserCache {

    User queryById(Integer id);

    User queryByUsername(String username);

    User queryByToken(String token);

    Token cacheByToken(User user);
}
