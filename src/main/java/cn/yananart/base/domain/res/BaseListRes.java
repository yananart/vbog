package cn.yananart.base.domain.res;

import lombok.Getter;

import java.util.List;

/**
 * 基本数组返回
 *
 * @author Yananart
 */
@Getter
public class BaseListRes<T> extends BaseRes {
    /**
     * 总条数
     */
    private final Integer total;

    /**
     * 数组
     */
    private final List<T> data;

    public BaseListRes(Integer total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public BaseListRes(List<T> data) {
        this.total = data.size();
        this.data = data;
    }
}
