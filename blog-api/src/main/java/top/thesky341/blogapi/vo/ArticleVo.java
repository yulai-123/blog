package top.thesky341.blogapi.vo;

import org.hibernate.validator.constraints.Length;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.entity.Article;
import top.thesky341.blogapi.entity.ArticleContent;
import top.thesky341.blogapi.entity.Category;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 封装了后端返回给前端的文章
 * @author thesky
 * @date 2020/12/5
 */
public class ArticleVo {
    private int id;
    private Date publishDate;
    private Date reviseDate;
    private String category;
    private String author;
    private String content;
    private String title;

    public ArticleVo() {
    }

    public ArticleVo(int id, Date publishDate, Date reviseDate, String category, String author, String content, String title) {
        this.id = id;
        this.publishDate = publishDate;
        this.reviseDate = reviseDate;
        this.category = category;
        this.author = author;
        this.content = content;
        this.title = title;
    }

    public ArticleVo(Article article) {
        this.id = article.getId();
        this.publishDate = article.getCreateDate();
        this.reviseDate = article.getReviseDate();
        this.category = article.getCategory().getName();
        this.author = article.getAuthor().getName();
        this.content = article.getContent().getContent();
        this.title = article.getTitle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getReviseDate() {
        return reviseDate;
    }

    public void setReviseDate(Date reviseDate) {
        this.reviseDate = reviseDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
