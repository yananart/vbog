package cn.yananart.base.repository.mapper;

import cn.yananart.base.domain.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Yananart
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * id查询
     */
    User queryById(Integer id);

    /**
     * 用户名查询
     */
    User queryByUsername(String username);

    /**
     * 录入用户
     *
     * @param user 用户对象
     */
    void insertUser(User user);

    /**
     * 更新用户 以id
     *
     * @param user 用户对象
     */
    void updateById(User user);

    /**
     * 更新用户 以username
     *
     * @param user 用户对象
     */
    void updateByUsername(User user);
}
