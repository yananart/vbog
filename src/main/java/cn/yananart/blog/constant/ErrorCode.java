package cn.yananart.blog.constant;

import lombok.Getter;

/**
 * 错误码
 *
 * @author Yananart
 */
@Getter
public enum ErrorCode {

    SUCCESS_CODE(0, "success"),

    PARAM_INPUT_ERROR(-1, "参数错误"),
    SESSION_INVALID(-99, "Session失效"),
    NO_AUTH_ERROR(-100, "no user auth"),
    USER_NO_EXITS(100, "用户不存在"),
    USER_NO_LOGIN(101, "用户未登录");

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
