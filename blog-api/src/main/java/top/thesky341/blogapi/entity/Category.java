package top.thesky341.blogapi.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *@author thesky
 *@date 2020/12/5
 */
public class Category {
    private int id = -1;
    @NotNull(message = "分类名必须存在")
    @Length(min = 2, max = 10, message = "分类名长度应该在2至10之间")
    @Pattern(regexp = "^[^\\s]+$", message = "分类名不能包含空白字符")
    private String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
