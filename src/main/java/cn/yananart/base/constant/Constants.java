package cn.yananart.base.constant;

import cn.yananart.base.domain.res.BaseRes;

/**
 * 常量
 *
 * @author Yananart
 */
public interface Constants {
    /**
     * 成功基本返回
     */
    BaseRes RES_SUCCESS = new BaseRes();

    /**
     * redis存储的域
     */
    String REDIS_SCOPE = "blog:";

}
