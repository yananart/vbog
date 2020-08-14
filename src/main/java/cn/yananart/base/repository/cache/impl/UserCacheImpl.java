package cn.yananart.base.repository.cache.impl;

import cn.yananart.base.config.BaseConfig;
import cn.yananart.base.constant.Constants;
import cn.yananart.base.domain.pojo.Token;
import cn.yananart.base.domain.pojo.User;
import cn.yananart.base.repository.cache.TokenCache;
import cn.yananart.base.repository.cache.UserCache;
import cn.yananart.base.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Yananart
 */
@Slf4j
@Component
public class UserCacheImpl implements UserCache {

    private RedisTemplate<Object, Object> redisTemplate;

    private UserMapper userMapper;

    private TokenCache tokenCache;

    private BaseConfig blogConfig;

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setTokenCache(TokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    @Autowired
    public void setBlogConfig(BaseConfig blogConfig) {
        this.blogConfig = blogConfig;
    }

    private static final String KEY_USER_ID = Constants.REDIS_SCOPE + "user:id:";
    private static final String KEY_USER_NAME = Constants.REDIS_SCOPE + "user:name:";
    private static final String KEY_USER_TOKEN = Constants.REDIS_SCOPE + "user:token:";

    @Override
    public User queryById(Integer uId) {
        String key = KEY_USER_ID + uId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (User) redisTemplate.opsForValue().get(key);
        }
        User user = userMapper.queryById(uId);
        if (user != null) {
            redisTemplate.opsForValue().set(key, user);
        }
        return user;
    }

    @Override
    public void deleteById(Integer uId) {
        String key = KEY_USER_ID + uId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public User queryByUsername(String username) {
        String key = KEY_USER_NAME + username;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (User) redisTemplate.opsForValue().get(key);
        }
        User user = userMapper.queryByUsername(username);
        if (user != null) {
            redisTemplate.opsForValue().set(key, user);
        }
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        String key = KEY_USER_NAME + username;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            User user = queryByUsername(username);
            if (user != null) {
                deleteById(user.getUid());
            }
            redisTemplate.delete(key);
        }
    }

    @Override
    public User queryByToken(String token) {
        String key = KEY_USER_TOKEN + token;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            Integer uId = (Integer) redisTemplate.opsForValue().get(key);
            if (uId != null) {
                return queryById(uId);
            }
        }
        return null;
    }

    @Override
    public void deleteByToken(String token) {
        String key = KEY_USER_TOKEN + token;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public Token cacheByUsernameForToken(String username) {
        User user = queryByUsername(username);
        Token token = tokenCache.queryByUserId(user.getUid());
        if (token != null) {
            tokenCache.deleteByUserId(user.getUid());
            deleteByToken(token.getToken());
        }
        token = new Token();
        String uuid = UUID.randomUUID().toString();
        token.setToken(uuid);
        token.setExpire(blogConfig.getTokenExpire());
        token.setUsername(username);
        String key = KEY_USER_TOKEN + uuid;
        redisTemplate.opsForValue().set(key, user.getUid(), blogConfig.getTokenExpire(), TimeUnit.SECONDS);
        tokenCache.cacheByUserId(user.getUid(), token);
        return token;
    }
}
