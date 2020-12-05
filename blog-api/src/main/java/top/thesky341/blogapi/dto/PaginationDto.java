package top.thesky341.blogapi.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 对分页查询参数的封装
 * @author thesky
 * @date 2020/12/5
 */
public class PaginationDto {
    @NotNull(message = "每页大小必须存在")
    @Min(value = 1, message = "每页大小不能小于1")
    private int pageSize;
    @NotNull(message = "所在页数必须存在")
    @Min(value = 1, message = "所在页数必须大于1")
    private int position;
    /**
     * 分页查询时可以指定所查询的分类
     * 初始值 -1 表示在所有文章中查找
     */
    private int categoryId = -1;

    public PaginationDto() {
    }

    public PaginationDto(@NotNull(message = "每页大小必须存在") @Min(value = 1, message = "每页大小不能小于1") int pageSize, @NotNull(message = "所在页数必须存在") @Min(value = 1, message = "所在页数必须大于1") int position, int categoryId) {
        this.pageSize = pageSize;
        this.position = position;
        this.categoryId = categoryId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "PaginationDto{" +
                "pageSize=" + pageSize +
                ", position=" + position +
                ", categoryId=" + categoryId +
                '}';
    }
}
