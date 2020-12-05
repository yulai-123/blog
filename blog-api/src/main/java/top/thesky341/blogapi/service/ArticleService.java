package top.thesky341.blogapi.service;

import top.thesky341.blogapi.entity.*;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/5
 */
public interface ArticleService {
    Article addNewArticle(Article article,
                          ArticleContent articleContent,
                          Category category,
                          Admin admin);

    Article getArticleInformation(int id);

    List<Article> getAllArticleInformation();

    /**
     * 分页查询文章列表
     * @param pagination
     * @return
     */
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
