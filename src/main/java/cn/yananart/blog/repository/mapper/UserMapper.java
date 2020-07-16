package cn.yananart.blog.repository.mapper;

import cn.yananart.blog.domain.pojo.User;
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

}
