package cn.yananart.blog.repository.cache.impl;

import cn.yananart.blog.constant.Constants;
import cn.yananart.blog.domain.pojo.Token;
import cn.yananart.blog.repository.cache.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Yananart
 */
@Repository
public class TokenCacheImpl implements TokenCache {

    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String KEY_TOKEN_USER = Constants.REDIS_SCOPE + "token:user:";

    @Override
    public Token queryByUsername(String username) {
        String key = KEY_TOKEN_USER + username;
        if (redisTemplate.hasKey(key)) {
            return (Token) redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    @Override
    public void deleteByUsername(String username) {
        String key = KEY_TOKEN_USER + username;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void cacheByUsername(String username, Token token) {
        String key = KEY_TOKEN_USER + username;
        redisTemplate.opsForValue().set(key, token);
    }
}
