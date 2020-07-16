package cn.yananart.blog.repository.cache;

import cn.yananart.blog.domain.pojo.Token;

/**
 * @author Yananart
 */
public interface TokenCache {

    Token queryByUsername(String username);

    void deleteByUsername(String username);

    void cacheByUsername(String username, Token token);
}
