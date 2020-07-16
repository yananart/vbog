package cn.yananart.blog.domain.pojo;

import lombok.Data;

/**
 * 用户
 *
 * @author Yananart
 */
@Data
public class User {
    /**
     * id
     */
    private Integer uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色
     */
    private String roles;
}
