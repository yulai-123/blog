package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.Category;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    void addCategory(Category category);
    void deleteCategoryById(int id);
    void updateCategory(Category category);
    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
}
