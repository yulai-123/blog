package top.thesky341.blogapi.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Article {
    private int id;
    private Date createDate;
    private Date reviseDate;
    private Category category;
    private Admin author;
    private ArticleContent content;
    @NotNull(message = "标题必须存在")
    @Length(min = 1, max = 30, message = "标题长度在1到30个字符之间")
    private String title;

    public Article() {
    }

    public Article(int id, Date createDate, Date reviseDate, Category category, Admin author, ArticleContent content, String title) {
        this.id = id;
        this.createDate = createDate;
        this.reviseDate = reviseDate;
        this.category = category;
        this.author = author;
        this.content = content;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getReviseDate() {
        return reviseDate;
    }

    public void setReviseDate(Date reviseDate) {
        this.reviseDate = reviseDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Admin getAuthor() {
        return author;
    }

    public void setAuthor(Admin author) {
        this.author = author;
    }

    public ArticleContent getContent() {
        return content;
    }

    public void setContent(ArticleContent content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
