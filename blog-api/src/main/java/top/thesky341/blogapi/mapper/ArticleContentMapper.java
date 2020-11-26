package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.ArticleContent;

@Mapper
@Repository
public interface ArticleContentMapper {
    void addArticleContent(ArticleContent articleContent);
    void deleteArticleContentById(int id);
    void updateArticleContent(ArticleContent articleContent);
    ArticleContent getArticleContentById(int id);
}
