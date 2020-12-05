package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.ArticleContent;

@Mapper
@Repository
public interface ArticleContentMapper {
    /**
     * 添加文章内容到 article_content 表中
     * 新添加的文章内容的 id 将设置在传入的 articleContent 中
     * @param articleContent 封装的文章内容
     * @exception DuplicateKeyException 字段设置为唯一,待添加内容已存在
     */
    void addArticleContent(ArticleContent articleContent);

    void deleteArticleContentById(int id);

    void updateArticleContent(ArticleContent articleContent);

    ArticleContent getArticleContentById(int id);
}
