package top.thesky341.blogapi.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaginationDto {
    @NotNull(message = "每页大小必须存在")
    @Min(value = 1, message = "每页大小不能小于1")
    private int pageSize;
    @NotNull(message = "所在页数必须存在")
    @Min(value = 1, message = "所在页数必须大于1")
    private int position;
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
}
