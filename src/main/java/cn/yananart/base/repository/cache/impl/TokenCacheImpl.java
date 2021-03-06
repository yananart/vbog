package cn.yananart.base.repository.cache.impl;

import cn.yananart.base.constant.Constants;
import cn.yananart.base.domain.pojo.Token;
import cn.yananart.base.repository.cache.TokenCache;
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
    public Token queryByUserId(Integer uId) {
        String key = KEY_TOKEN_USER + uId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (Token) redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    @Override
    public void deleteByUserId(Integer uId) {
        String key = KEY_TOKEN_USER + uId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void cacheByUserId(Integer uId, Token token) {
        String key = KEY_TOKEN_USER + uId;
        redisTemplate.opsForValue().set(key, token);
    }
}
