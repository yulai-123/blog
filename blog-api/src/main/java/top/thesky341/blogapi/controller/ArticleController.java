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
import top.thesky341.blogapi.util.result.Result;
import top.thesky341.blogapi.util.result.ResultCode;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
public class ArticleController {
    @Resource(name = "articleServiceImpl")
    ArticleService articleService;
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;

    @RequiresAuthentication
    @PostMapping("/article")
    public Result addNewArticle(@Valid @RequestBody ArticleDto articleDto) {
        System.out.println(articleDto.getId() + " fjasdk");
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDto.getContent());
        Category category = new Category();
        category.setName(articleDto.getCategoryName());
        Admin admin = new Admin();
        admin.setName(SecurityUtils.getSubject().getPrincipal().toString());
//        admin.setName("thesky1");
        articleService.addNewArticle(article, articleContent,
                category, admin);
        return Result.success();
    }

    @GetMapping("/article/information/{id}")
    public Result getArticleInformation(@PathVariable int id) {
        Article article = articleService.getArticleInformation(id);
        Map<String, Object> data = new HashMap<>();
        data.put("id", article.getId());
        data.put("title", article.getTitle());
        data.put("author", article.getAuthor().getName());
        data.put("publishDate", article.getCreateDate());
        if(article.getReviseDate() != null) {
            data.put("reviseDate", article.getReviseDate());
        }
        data.put("category", article.getCategory().getName());
        Result result = Result.success(data);
        return result;
    }

    @GetMapping("/article/information/all")
    public Result getAllArticleInformation() {
        List<Map<String, Object>> articlesResult = new ArrayList<>();
        List<Article> articles = articleService.getAllArticleInformation();
        for(Article article : articles) {
            Map<String, Object> articleResult = new HashMap<>();
            articleResult.put("id", article.getId());
            articleResult.put("title", article.getTitle());
            articleResult.put("author", article.getAuthor().getName());
            articleResult.put("publishDate", article.getCreateDate());
            if(article.getReviseDate() != null) {
                articleResult.put("reviseDate", article.getReviseDate());
            }
            articleResult.put("category", article.getCategory().getName());
            articlesResult.add(articleResult);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("articles", articlesResult);
        return Result.success(data);
    }

    @PostMapping("/article/information/list")
    public Result getArticleList(@Valid @RequestBody PaginationDto paginationDto) {
        List<Map<String, Object>> articlesResult = new ArrayList<>();
        Pagination pagination = new Pagination();
        pagination.setFrom(paginationDto.getPageSize() * (paginationDto.getPosition() - 1));
        pagination.setNum(paginationDto.getPageSize());
        System.out.println(paginationDto.getCategoryId());
        if(paginationDto.getCategoryId() == -1) {
            pagination.setCategoryId(null);
        } else if(categoryService.getCategoryById(paginationDto.getCategoryId()) != null) {
            pagination.setCategoryId(paginationDto.getCategoryId());
        } else {
            System.out.println(paginationDto.getCategoryId());
            System.out.println(categoryService.getCategoryById(paginationDto.getCategoryId()));
            return new Result(ResultCode.NotExistCategory);
        }
        List<Article> articles = articleService.getArticleListByPagination(pagination);
        for (Article article : articles) {
            Map<String, Object> articleResult = new HashMap<>();
            articleResult.put("id", article.getId());
            articleResult.put("title", article.getTitle());
            articleResult.put("author", article.getAuthor().getName());
            articleResult.put("publishDate", article.getCreateDate());
            if (article.getReviseDate() != null) {
                articleResult.put("reviseDate", article.getReviseDate());
            }
            articleResult.put("category", article.getCategory().getName());
            articlesResult.add(articleResult);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("articles", articlesResult);
        return Result.success(data);
    }

    @PostMapping("/article/count/all")
    public Result getAllArticleNumber() {
        int sum = articleService.getAllArticleNumber();
        Map<String, Object> data = new HashMap<>();
        data.put("sum", sum);
        return Result.success(data);
    }

    @PostMapping("/article/count")
    public Result getArticleNumberByCategory(@RequestBody Category category) {
        System.out.println(category);
        category = categoryService.getCategoryById(category.getId());
        Assert.notNull(category, "分类不存在");
        int sum = articleService.getArticleNumberByCategoryId(category.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("sum", sum);
        System.out.println(sum);
        return Result.success(data);
    }

    @GetMapping("/article/{id}")
    public Result getArticleById(@PathVariable int id) {
        Article article = articleService.getArticleById(id);
        Assert.notNull(article, "文章不存在");
        Map<String, Object> data = new HashMap<>();
//        data.put("article", article);
        data.put("id", article.getId());
        data.put("title", article.getTitle());
        data.put("createDate", article.getCreateDate());
        if(article.getReviseDate() != null) {
            data.put("reviseDate", article.getReviseDate());
        }
        data.put("categoryName", article.getCategory().getName());
        data.put("articleContent", article.getContent().getContent());
        data.put("authorName", article.getAuthor().getName());
        return Result.success(data);
    }

    @RequiresAuthentication
    @DeleteMapping("/article/{id}")
    public Result DeleteArticleById(@PathVariable int id) {
        articleService.deleteArticleById(id);
        System.out.println("删除" + id + "成功");
        return Result.success();
    }

    @RequiresAuthentication
    @PutMapping("/article/revise")
    public Result ReviseArticleById(@Valid @RequestBody ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDto.getContent());
        Category category = new Category();
        category.setName(articleDto.getCategoryName());
        Admin admin = new Admin();
        admin.setName(SecurityUtils.getSubject().getPrincipal().toString());
//        admin.setName("thesky1");
        articleService.reviseArticle(article, articleContent,
                category, admin);
        return Result.success();
    }

}
