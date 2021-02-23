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
import top.thesky341.blogapi.util.RedisUtil;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/5
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisUtil redisUtil;

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
        String key = "article:id:" + id;
        if (redisUtil.hasKey(key)) {
            redisUtil.updateExpire(key);
            Object value = redisUtil.getKey(key);
            if(value instanceof Article) {
                return (Article)value;
            }
        }
        Article article = articleMapper.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        redisUtil.setKey(key, article);
        return article;
    }

    @Override
    public List<Article> getAllArticleInformation() {
        List<Article> articles = articleMapper.getAllArticle();
        return articles;
    }

    @Override
    public int getAllArticleNumber() {
        String key = "article:sum";
        if (redisUtil.hasKey(key)) {
            Object value = redisUtil.getKey(key);
            if(value instanceof Integer) {
                return (Integer) value;
            }
        }
        int sum = articleMapper.getAllArticleNumber();
        redisUtil.setKey(key, sum, -1);
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
        String key = "article:id:" + id;
        if (redisUtil.hasKey(key)) {
            redisUtil.updateExpire(key);
            Object value = redisUtil.getKey(key);
            if(value instanceof Article) {
                return (Article)value;
            }
        }
        Article article = articleMapper.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        redisUtil.setKey(key, article);
        return article;
    }

    @Transactional
    @Override
    public void deleteArticleById(int id) {
        Article article = articleMapper.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        ArticleContent articleContent = article.getContent();
        articleMapper.deleteArticleById(article.getId());

        String key = "article:id" + id;
        if(redisUtil.hasKey(key)) {
            redisUtil.delKey(key);
        }

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

        String key = "article:id" + id;
        if(redisUtil.hasKey(key)) {
            redisUtil.delKey(key);
        }

        return articleMapper.getArticleById(article.getId());
    }
}
