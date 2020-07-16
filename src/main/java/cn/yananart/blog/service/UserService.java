package cn.yananart.blog.service;

import cn.yananart.blog.domain.pojo.User;

/**
 * 用户服务
 *
 * @author Yananart
 */
public interface UserService {
    /**
     * 获取登录用户
     */
    User getLoginUser();

    /**
     * 根据id获取用户
     */
    User getUserById(Integer id);

}
