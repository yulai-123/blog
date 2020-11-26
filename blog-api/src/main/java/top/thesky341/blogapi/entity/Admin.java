package top.thesky341.blogapi.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Admin {
    private int id;
    @NotNull(message = "用户名必须存在")
    @Length(min = 3, max = 16, message = "用户名长度应该在3至16之间")
    @Pattern(regexp = "^[^\\s]+$", message = "用户名不能包含空白字符")
    private String name;
    @NotNull(message = "密码必须存在")
    @Length(min = 6, max = 26, message = "密码长度应该在6至26之间")
    @Pattern(regexp = "^[^\\s]+$", message = "密码不能包含空白字符")
    private String passwd;

    public Admin() {
    }

    public Admin(int id, String name, String passwd) {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
