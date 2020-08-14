package cn.yananart.base.repository.cache;

import cn.yananart.base.domain.pojo.Token;
import cn.yananart.base.domain.pojo.User;

/**
 * 用户缓存
 *
 * @author Yananart
 */
public interface UserCache {

    /**
     * 按id查询用户信息
     *
     * @param uId id
     * @return user
     */
    User queryById(Integer uId);

    /**
     * 按id删除缓存
     * @param uId uId
     */
    void deleteById(Integer uId);

    /**
     * 按用户名查询用户信息
     *
     * @param username username
     * @return user
     */
    User queryByUsername(String username);

    /**
     * 按用户名删除缓存
     *
     * @param username username
     */
    void deleteByUsername(String username);

    /**
     * 根据token查询用户信息
     *
     * @param token token
     * @return user
     */
    User queryByToken(String token);

    /**
     * 根据token清除缓存
     *
     * @param token token
     */
    void deleteByToken(String token);

    /**
     * 缓存用户信息并生成token
     *
     * @param username 用户名
     * @return token
     */
    Token cacheByUsernameForToken(String username);
}
