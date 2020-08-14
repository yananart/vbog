package cn.yananart.base.service;

import cn.yananart.base.domain.pojo.User;

/**
 * 用户服务
 *
 * @author Yananart
 */
public interface UserService {
    /**
     * 获取登录用户
     *
     * @return 当前登录对象
     */
    User getLoginUser();

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    User getUserById(Integer id);

    /**
     * 根据username获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByName(String username);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 成功与否
     */
    Boolean addUser(User user);

}
