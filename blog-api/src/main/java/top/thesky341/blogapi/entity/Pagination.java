package top.thesky341.blogapi.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 封装了分页查询文章的开始位置和数量
 * 可以指定查询文章的类别
 *@author thesky
 *@date 2020/12/5
 */
public class Pagination {
    private int from;
    private int num;
    private Integer categoryId;

    public Pagination() {
    }

    public Pagination(int from, int num, Integer categoryId) {
        this.from = from;
        this.num = num;
        this.categoryId = categoryId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
