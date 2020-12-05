package top.thesky341.blogapi.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *@author thesky
 *@date 2020/12/5
 */
public class ArticleContent {
    private int id;
    @NotNull(message = "博客内容必须存在")
    @Length(min = 1, message = "博客内容长度必须大于1")
    private String content;


    public ArticleContent() {
    }

    public ArticleContent(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
