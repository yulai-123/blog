package top.thesky341.blogapi.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import top.thesky341.blogapi.dto.ArticleDto;
import top.thesky341.blogapi.dto.PaginationDto;
import top.thesky341.blogapi.entity.*;
import top.thesky341.blogapi.service.ArticleService;
import top.thesky341.blogapi.service.CategoryService;
import top.thesky341.blogapi.util.Common;
import top.thesky341.blogapi.util.result.Result;
import top.thesky341.blogapi.util.result.ResultCode;
import top.thesky341.blogapi.vo.ArticleInfoVo;
import top.thesky341.blogapi.vo.ArticleVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 *@author thesky
 *@date 2020/12/5
 */
@RestController
public class ArticleController {
    @Resource(name = "articleServiceImpl")
    ArticleService articleService;
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;

    @RequiresAuthentication
    @PostMapping("/article")
    public Result addNewArticle(@Valid @RequestBody ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDto.getContent());
        Category category = new Category();
        category.setName(articleDto.getCategoryName());
        Admin admin = new Admin();
        admin.setName(Common.getCurrentUserName());
        articleService.addNewArticle(article, articleContent,
                category, admin);
        return Result.success();
    }

    /**
     * 注意前端还未更改这个接口
     */
    @GetMapping("/article/information/{id}")
    public Result getArticleInformation(@PathVariable int id) {
        Article article = articleService.getArticleInformation(id);
        return Result.success(new ArticleInfoVo(article));
    }

    /**
     * 注意前端还未更改这个接口
     */
    @GetMapping("/article/information/all")
    public Result getAllArticleInformation() {
        List<ArticleInfoVo> articlesResult = new ArrayList<>();
        List<Article> articles = articleService.getAllArticleInformation();
        for(Article article : articles) {
            articlesResult.add(new ArticleInfoVo(article));
        }
        return Result.success("articles", articlesResult);
    }

    /**
     * 以分页的方式获取文章列表
     * @return 封装了文章列表的 Result
     * 如果未指定分类或者指定分类不存在，则在所有文章中获取文章列表
     * 否则在对应分类中获取文章列表
     */
    @PostMapping("/article/information/list")
    public Result getArticleList(@Valid @RequestBody PaginationDto paginationDto) {
        List<ArticleInfoVo> articlesResult = new ArrayList<>();
        Pagination pagination = new Pagination();
        pagination.setFrom(paginationDto.getPageSize() * (paginationDto.getPosition() - 1));
        pagination.setNum(paginationDto.getPageSize());
        System.out.println(paginationDto.getCategoryId());
        if(categoryService.getCategoryById(paginationDto.getCategoryId()) != null) {
            pagination.setCategoryId(paginationDto.getCategoryId());
        }
        List<Article> articles = articleService.getArticleListByPagination(pagination);
        for (Article article : articles) {
            articlesResult.add(new ArticleInfoVo(article));
        }
        return Result.success("articles", articlesResult);
    }

    @PostMapping("/article/count/all")
    public Result getAllArticleNumber() {
        int sum = articleService.getAllArticleNumber();
        return Result.success("sum", sum);
    }

    @PostMapping("/article/count")
    public Result getArticleNumberByCategory(@RequestBody Category category) {
        category = categoryService.getCategoryById(category.getId());
        Assert.notNull(category, "分类不存在");
        int sum = articleService.getArticleNumberByCategoryId(category.getId());
        return Result.success("sum", sum);
    }

    @GetMapping("/article/{id}")
    public Result getArticleById(@PathVariable int id) {
        Article article = articleService.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        return Result.success("article", new ArticleVo(article));
    }

    @RequiresAuthentication
    @DeleteMapping("/article/{id}")
    public Result DeleteArticleById(@PathVariable int id) {
        articleService.deleteArticleById(id);
        return Result.success();
    }

    @RequiresAuthentication
    @PutMapping("/article")
    public Result ReviseArticleById(@Valid @RequestBody ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDto.getContent());
        Category category = new Category();
        category.setName(articleDto.getCategoryName());
        Admin admin = new Admin();
        admin.setName(Common.getCurrentUserName());
        articleService.reviseArticle(article, articleContent,
                category, admin);
        return Result.success();
    }

}
