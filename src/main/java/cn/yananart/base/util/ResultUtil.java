package cn.yananart.base.util;

import cn.yananart.base.constant.Constants;
import cn.yananart.base.constant.ErrorCode;
import cn.yananart.base.domain.res.BaseListRes;
import cn.yananart.base.domain.res.BaseObjectRes;
import cn.yananart.base.domain.res.BaseRes;

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

    public static BaseRes returnError(Integer code, String message) {
        return new BaseRes(code, message);
    }

    public static BaseRes returnError(ErrorCode errorCode) {
        return new BaseRes(errorCode.getCode(), errorCode.getMessage());
    }

}
