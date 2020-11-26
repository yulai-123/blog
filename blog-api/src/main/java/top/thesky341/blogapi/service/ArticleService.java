package top.thesky341.blogapi.service;

import top.thesky341.blogapi.entity.*;

import java.util.List;

public interface ArticleService {
    Article addNewArticle(Article article,
                          ArticleContent articleContent,
                          Category category,
                          Admin admin);
    Article getArticleInformation(int id);
    List<Article> getAllArticleInformation();
    List<Article> getArticleListByPagination(Pagination pagination);
    int getAllArticleNumber();
    int getArticleNumberByCategoryId(int categoryId);
    Article getArticleById(int id);
    void deleteArticleById(int id);
    Article reviseArticle(Article article,
                          ArticleContent articleContent,
                          Category category,
                          Admin admin);
}
