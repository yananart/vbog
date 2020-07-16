package cn.yananart.blog.controller;

import cn.yananart.blog.domain.res.BaseRes;
import cn.yananart.blog.service.UserService;
import cn.yananart.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public BaseRes getById(@PathVariable("id") Integer id) {
        return ResultUtil.returnSuccessObject(userService.getUserById(id));
    }

    @GetMapping("/me")
    public BaseRes getMe() {
        return ResultUtil.returnSuccessObject(userService.getLoginUser());
    }
}
