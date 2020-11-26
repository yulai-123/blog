package top.thesky341.blogapi.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.service.AdminService;
import top.thesky341.blogapi.util.encrypt.MD5SaltEncryption;
import top.thesky341.blogapi.util.result.Result;
import top.thesky341.blogapi.util.result.ResultCode;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    @RequiresAuthentication
    @PostMapping("/register")
    public Result register(@Valid @RequestBody Admin admin) {
//        System.out.println(admin);
        System.out.println(SecurityUtils.getSubject().isAuthenticated());
        String encryptedPasswd = MD5SaltEncryption.encrypt(admin.getPasswd(), admin.getName());
//        System.out.println(encryptedPasswd);
        admin.setPasswd(encryptedPasswd);
        adminService.addAdmin(admin);
        Map<String, String> data = new HashMap<>();
        data.put("name", admin.getName());
        Result result = Result.success(data);
        return result;
    }

    @PostMapping("/login")
    public Result login(@Valid @RequestBody Admin admin) {
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getName(), admin.getPasswd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        Map<String, String> data = new HashMap<>();
        Result result = Result.success();
        return result;
    }

    @RequestMapping("/shiro/login")
    public Result shiroLogin() {
        return new Result(ResultCode.NeedLogin);
    }

    @PostMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success();
    }

    @PostMapping("/checkloginstate")
    public Result checkLoginState() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            String name = subject.getPrincipal().toString();
            Map<String, String> data = new HashMap<>();
            data.put("name", name);
            return Result.success(data);
        } else {
            return new Result(ResultCode.UnauthenticatedException);
        }
    }
}
