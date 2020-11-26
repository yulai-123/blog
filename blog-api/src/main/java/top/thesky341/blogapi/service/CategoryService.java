package top.thesky341.blogapi.service;

import top.thesky341.blogapi.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category getCategoryByName(String name);
    Category getCategoryById(int id);
}
