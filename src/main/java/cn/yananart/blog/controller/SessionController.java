package cn.yananart.blog.controller;

import cn.yananart.blog.constant.ErrorCode;
import cn.yananart.blog.domain.res.BaseRes;
import cn.yananart.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * session
 *
 * @author Yananart
 */
@Slf4j
@RestController
@RequestMapping("/session")
public class SessionController {

    @RequestMapping("/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseRes invalid(){
        throw  new BlogException(ErrorCode.SESSION_INVALID);
    }

}
