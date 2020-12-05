package top.thesky341.blogapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thesky341.blogapi.entity.Category;
import top.thesky341.blogapi.mapper.CategoryMapper;
import top.thesky341.blogapi.service.CategoryService;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/5
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public Category addCategory(Category category) {
        categoryMapper.addCategory(category);
        int id = category.getId();
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryMapper.getCategoryByName(name);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryMapper.getCategoryById(id);
    }
}
