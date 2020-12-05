package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.Admin;

import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/5
 */
@Mapper
@Repository
public interface AdminMapper {
    /**
     * 实现添加用户的功能
     * 新添加的用户的 id 将设置在传入的 admin 中
     * @param admin 即将创建的用户
     * @exception DuplicateKeyException 字段设置为唯一,待添加内容已存在
     */
    void addAdmin(Admin admin);

    void deleteAdminById(int id);

    Admin getAdminByName(String name);

    Admin getAdminById(int id);

    List<Admin> getAllAdmin();
}
