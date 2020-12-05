package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.Category;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    /**
     * 添加分类
     * 新添加的分类的 id 将设置在传入的 category 中
     * @param category 将被添加的分类
     * @exception DuplicateKeyException 字段设置为唯一,待添加内容已存在
     */
    void addCategory(Category category);

    void deleteCategoryById(int id);

    void updateCategory(Category category);

    Category getCategoryById(int id);

    Category getCategoryByName(String name);

    List<Category> getAllCategory();
}
