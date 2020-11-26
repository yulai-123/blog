package top.thesky341.blogapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.blogapi.entity.Admin;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    void addAdmin(Admin admin);
    void deleteAdminById(int id);
    Admin getAdminByName(String name);
    Admin getAdminById(int id);
    List<Admin> getAllAdmin();
}
