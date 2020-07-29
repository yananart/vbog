package cn.yananart.blog.repository.cache.impl;

import cn.yananart.blog.config.BlogConfig;
import cn.yananart.blog.constant.Constants;
import cn.yananart.blog.domain.pojo.Token;
import cn.yananart.blog.domain.pojo.User;
import cn.yananart.blog.repository.cache.TokenCache;
import cn.yananart.blog.repository.cache.UserCache;
import cn.yananart.blog.repository.mapper.UserMapper;
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

    private BlogConfig blogConfig;

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
    public void setBlogConfig(BlogConfig blogConfig) {
        this.blogConfig = blogConfig;
    }

    private static final String KEY_USER_ID = Constants.REDIS_SCOPE + "user:id:";
    private static final String KEY_USER_NAME = Constants.REDIS_SCOPE + "user:name:";
    private static final String KEY_USER_TOKEN = Constants.REDIS_SCOPE + "user:token:";

    @Override
    public User queryById(Integer id) {
        String key = KEY_USER_ID + id;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (User) redisTemplate.opsForValue().get(key);
        }
        User user = userMapper.queryById(id);
        if (user != null) {
            redisTemplate.opsForValue().set(key, user);
        }
        return user;
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
    public User queryByToken(String token) {
        String key = KEY_USER_TOKEN + token;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (User) redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    @Override
    public Token cacheByToken(String username) {
        User user = queryByUsername(username);
        Token token = tokenCache.queryByUsername(username);
        if (token != null) {
            tokenCache.deleteByUsername(username);
        }
        token = new Token();
        String uuid = UUID.randomUUID().toString();
        token.setToken(uuid);
        token.setExpire(blogConfig.getTokenExpire());
        token.setUsername(username);
        String key = KEY_USER_TOKEN + uuid;
        redisTemplate.opsForValue().set(key, user, blogConfig.getTokenExpire(), TimeUnit.SECONDS);
        tokenCache.cacheByUsername(username, token);
        return token;
    }
}
