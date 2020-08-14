package cn.yananart.base.domain.res;

import cn.yananart.base.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 基本返回类
 *
 * @author Yananart
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseRes {
    /**
     * 错误码
     */
    private Integer code = ErrorCode.SUCCESS_CODE.getCode();
    /**
     * 信息
     */
    private String message = ErrorCode.SUCCESS_CODE.getMessage();
}
