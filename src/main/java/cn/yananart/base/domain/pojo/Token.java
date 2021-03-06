package cn.yananart.base.domain.pojo;

import lombok.Data;

/**
 * 凭证
 *
 * @author Yananart
 */
@Data
public class Token {
    /**
     * token
     */
    private String token;
    /**
     * 有效期 秒
     */
    private Integer expire;
    /**
     * 用户名
     */
    private String username;
}
