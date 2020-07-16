package cn.yananart.blog.domain.res;

import lombok.Getter;

/**
 * 基本对象返回
 *
 * @author Yananart
 */
@Getter
public class BaseObjectRes<T> extends BaseRes {
    /**
     * 对象
     */
    private T data;

    public BaseObjectRes(T data) {
        this.data = data;
    }
}
