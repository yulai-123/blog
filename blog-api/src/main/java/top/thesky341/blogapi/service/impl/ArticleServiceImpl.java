package top.thesky341.blogapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.thesky341.blogapi.entity.*;
import top.thesky341.blogapi.mapper.AdminMapper;
import top.thesky341.blogapi.mapper.ArticleContentMapper;
import top.thesky341.blogapi.mapper.ArticleMapper;
import top.thesky341.blogapi.mapper.CategoryMapper;
import top.thesky341.blogapi.service.ArticleService;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/5
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleContentMapper articleContentMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    AdminMapper adminMapper;

    @Transactional
    @Override
    public Article addNewArticle(Article article, ArticleContent articleContent,
                                 Category category, Admin admin) {
        category = categoryMapper.getCategoryByName(category.getName());
        admin = adminMapper.getAdminByName(admin.getName());
        Assert.notNull(category, "分类不存在");
        Assert.notNull(admin, "用户不存在");
        articleContentMapper.addArticleContent(articleContent);
        articleContent = articleContentMapper.getArticleContentById(articleContent.getId());
        article.setAuthor(admin);
        article.setCategory(category);
        article.setContent(articleContent);
        articleMapper.addArticle(article);
        return articleMapper.getArticleById(article.getId());
    }

    @Override
    public Article getArticleInformation(int id) {
        Article article = articleMapper.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        return article;
    }

    @Override
    public List<Article> getAllArticleInformation() {
        List<Article> articles = articleMapper.getAllArticle();
        return articles;
    }

    @Override
    public int getAllArticleNumber() {
        return articleMapper.getAllArticleNumber();
    }

    @Override
    public int getArticleNumberByCategoryId(int categoryId) {
        return articleMapper.getArticleNumberByCategoryId(categoryId);
    }

    @Override
    public List<Article> getArticleListByPagination(Pagination pagination) {
        return articleMapper.getArticleListBypagination(pagination);
    }

    @Override
    public Article getArticleById(int id) {
        return articleMapper.getArticleById(id);
    }

    @Transactional
    @Override
    public void deleteArticleById(int id) {
        Article article = articleMapper.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        ArticleContent articleContent = article.getContent();
        articleMapper.deleteArticleById(article.getId());
        articleContentMapper.deleteArticleContentById(articleContent.getId());
    }

    @Transactional
    @Override
    public Article reviseArticle(Article article, ArticleContent articleContent,
                                 Category category, Admin admin) {
        int id = article.getId();
        Article oldArticle = articleMapper.getArticleById(id);
        Assert.notNull(oldArticle, "博客不存在");
        category = categoryMapper.getCategoryByName(category.getName());
        admin = adminMapper.getAdminByName(admin.getName());
        Assert.notNull(category, "分类不存在");
        Assert.notNull(admin, "用户不存在");
        articleContentMapper.addArticleContent(articleContent);

        articleContent = articleContentMapper.getArticleContentById(articleContent.getId());
        article.setAuthor(admin);
        article.setCategory(category);
        article.setContent(articleContent);
        articleMapper.updateArticle(article);
        articleContentMapper.deleteArticleContentById(oldArticle.getContent().getId());
        return articleMapper.getArticleById(article.getId());
    }
}
