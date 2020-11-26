package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.entity.Article;
import top.thesky341.blogapi.entity.Pagination;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    void addArticle(Article article);
    void deleteArticleById(int id);
    void updateArticle(Article article);
    Article getArticleById(int id);
    List<Article> getAllArticleByAuthor(Admin author);
    List<Article> getAllArticle();
    List<Article> getArticleListBypagination(Pagination pagination);
    int getAllArticleNumber();
    int getArticleNumberByCategoryId(int categoryId);
}
