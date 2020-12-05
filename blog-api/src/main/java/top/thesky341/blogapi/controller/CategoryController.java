package top.thesky341.blogapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.blogapi.entity.Category;
import top.thesky341.blogapi.service.CategoryService;
import top.thesky341.blogapi.util.result.Result;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

@RestController
public class CategoryController {
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;

    @GetMapping("/categorylist")
    public Result getCategoryList() {
        List<Category> categoryList = categoryService.getAllCategory();
        return Result.success("categoryList", categoryList);
    }

    @PostMapping("category")
    public Result addCategory(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
        return Result.success();
    }
}
