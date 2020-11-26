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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;

    @GetMapping("/categorylist")
    public Result getCategoryList() {
        Map<String, Object> data = new HashMap<>();
        List<Category> categoryList = categoryService.getAllCategory();
        data.put("categoryList", categoryList);
        return Result.success(data);
    }

    @PostMapping("category")
    public Result addCategory(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
        return Result.success();
    }
}
