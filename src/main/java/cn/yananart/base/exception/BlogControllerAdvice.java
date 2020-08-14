package cn.yananart.base.exception;

import cn.yananart.base.domain.res.BaseRes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕捉
 *
 * @author Yananart
 */
@ControllerAdvice
public class BlogControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = BlogException.class)
    public BaseRes exceptionHandler(BlogException e) {
        return new BaseRes(e.getCode(), e.getMessage());
    }
}
