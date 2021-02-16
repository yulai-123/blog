package top.thesky341.blogapi.service;

import top.thesky341.blogapi.entity.Admin;

/**
 * @author thesky
 * @date 2020/12/5
 */
public interface AdminService {
    Admin addAdmin(Admin admin);
    Admin getAdminByName(String name);
}
