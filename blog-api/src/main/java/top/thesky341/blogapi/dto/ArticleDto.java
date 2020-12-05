package top.thesky341.blogapi.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 前端传输过来的文章
 * @author thesky
 * @date 2020/12/5
 */
public class ArticleDto {
    private int id = -1;
    @NotNull(message = "标题必须存在")
    @Length(min = 1, max = 30, message = "标题长度在1到30个字符之间")
    private String title;
    @NotNull(message = "博客内容必须存在")
    @Length(min = 1, message = "博客内容长度必须大于1")
    private String content;
    @NotNull(message = "分类名必须存在")
    @Length(min = 2, max = 10, message = "分类名长度应该在2至10之间")
    @Pattern(regexp = "^[^\\s]+$", message = "分类名不能包含空白字符")
    private String categoryName;

    public ArticleDto() {
    }

    public ArticleDto(int id, String title, String content, String categoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
