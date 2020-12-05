package top.thesky341.blogapi.vo;

import top.thesky341.blogapi.entity.Article;
import top.thesky341.blogapi.entity.ArticleContent;

import java.util.Date;

/**
 * 封装了后端返回给前端的的文章信息
 * @author thesky
 * @date 2020/12/5
 */
public class ArticleInfoVo {
    private int id;
    private String title;
    private String author;
    private Date publishDate;
    private Date reviseDate;
    private String category;

    public ArticleInfoVo() {
    }

    public ArticleInfoVo(int id, String title, String author, Date publishDate, Date reviseDate, String category, ArticleContent content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.reviseDate = reviseDate;
        this.category = category;
    }

    public ArticleInfoVo(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.author = article.getAuthor().getName();
        this.category = article.getCategory().getName();
        this.publishDate = article.getCreateDate();
        this.reviseDate = article.getReviseDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "articleInfoVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", reviseDate=" + reviseDate +
                ", category='" + category + '\'' +
                '}';
    }
}
