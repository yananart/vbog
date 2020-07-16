package cn.yananart.blog.util;

import cn.yananart.blog.constant.Constants;
import cn.yananart.blog.domain.res.BaseListRes;
import cn.yananart.blog.domain.res.BaseObjectRes;
import cn.yananart.blog.domain.res.BaseRes;

import java.util.List;

/**
 * 返回工具类
 *
 * @author Yananart
 */
public class ResultUtil {

    public static BaseRes returnSuccess() {
        return Constants.RES_SUCCESS;
    }

    public static BaseRes returnSuccessObject(Object object) {
        return new BaseObjectRes<>(object);
    }

    public static BaseRes returnSuccessList(List<Object> list) {
        return new BaseListRes<>(list);
    }

}
