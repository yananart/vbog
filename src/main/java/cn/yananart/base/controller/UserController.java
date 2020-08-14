package cn.yananart.base.controller;

import cn.yananart.base.constant.ErrorCode;
import cn.yananart.base.domain.res.BaseRes;
import cn.yananart.base.exception.BlogException;
import cn.yananart.base.service.UserService;
import cn.yananart.base.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 *
 * @author Yananart
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/uid/{id}")
    public BaseRes getById(@PathVariable("id") Integer id) {
        if (id == null) {
            throw new BlogException(ErrorCode.PARAM_INPUT_ERROR);
        }
        return ResultUtil.returnSuccessObject(userService.getUserById(id));
    }

    @GetMapping("/name/{name}")
    public BaseRes getByName(@PathVariable("name") String username) {
        if (StringUtils.isEmpty(username)) {
            throw new BlogException(ErrorCode.PARAM_INPUT_ERROR);
        }
        return ResultUtil.returnSuccessObject(userService.getUserByName(username));
    }

    @GetMapping("/me")
    public BaseRes getMe() {
        return ResultUtil.returnSuccessObject(userService.getLoginUser());
    }
}
