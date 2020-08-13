package cn.yananart.blog.repository.cache;

import cn.yananart.blog.domain.pojo.Token;

/**
 * token缓存
 *
 * @author Yananart
 */
public interface TokenCache {

    /**
     * 根据用户ID获取token
     *
     * @param uId uId
     * @return token
     */
    Token queryByUserId(Integer uId);

    /**
     * 按用户ID删除token
     *
     * @param uId uId
     */
    void deleteByUserId(Integer uId);

    /**
     * 按用户ID缓存token
     *
     * @param uId   uId
     * @param token token
     */
    void cacheByUserId(Integer uId, Token token);
}
