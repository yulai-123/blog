package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.entity.Article;
import top.thesky341.blogapi.entity.Pagination;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    /**
     * 添加文章
     * 新添加的文章的 id 将设置在传入的 article 中
     * @param article 将被添加的文章
     * @exception DuplicateKeyException 字段设置为唯一,待添加内容已存在
     */
    void addArticle(Article article);

    void deleteArticleById(int id);

    void updateArticle(Article article);

    Article getArticleById(int id);

    List<Article> getAllArticleByAuthor(Admin author);

    List<Article> getAllArticle();

    List<Article> getArticleListBypagination(Pagination pagination);

    /**
     * 获取文章总数
     */
    int getAllArticleNumber();

    /**
     * 获取某个分类的文章总数
     * @param categoryId 分类的 id
     */
    int getArticleNumberByCategoryId(int categoryId);
}
