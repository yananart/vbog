package cn.yananart.base.constant;

import lombok.Getter;

/**
 * 错误码
 *
 * @author Yananart
 */
@Getter
public enum ErrorCode {

    /**
     * 成功
     */
    SUCCESS_CODE(0, "success"),
    /**
     * 初始化passwordEncoder异常
     */
    INIT_PASSWORD_ENCODER_ERROR(-1001, "密码加密器初始化错误"),
    INIT_ADMIN_ERROR(-1002, "初始化管理员错误"),

    PARAM_INPUT_ERROR(-1, "参数错误"),
    SESSION_INVALID(-99, "Session失效"),
    NO_AUTH_ERROR(-100, "no user auth"),
    USER_NO_EXITS(100, "用户不存在"),
    USER_NO_LOGIN(101, "用户未登录");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
