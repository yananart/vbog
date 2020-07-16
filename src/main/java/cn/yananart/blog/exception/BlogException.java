package cn.yananart.blog.exception;

import cn.yananart.blog.constant.ErrorCode;
import lombok.Getter;

/**
 * 全局异常
 *
 * @author Yananart
 */
@Getter
public class BlogException extends RuntimeException {

    private Integer code;

    public BlogException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BlogException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
