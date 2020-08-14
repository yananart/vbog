package cn.yananart.base.controller.manager;

import cn.yananart.base.domain.req.UserReq;
import cn.yananart.base.domain.res.BaseRes;
import cn.yananart.base.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理接口
 *
 * @author Yananart
 */
@RestController
@RequestMapping("/manager/user")
public class UserManagerController {

    @PostMapping("add")
    public BaseRes add(UserReq req) {
        // TODO 新增用户
        return ResultUtil.returnSuccess();
    }

}
