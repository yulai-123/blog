package top.thesky341.blogapi.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.thesky341.blogapi.util.result.Result;
import top.thesky341.blogapi.util.result.ResultCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author thesky
 * @date 2020/12/5
 */
@RestControllerAdvice
public class GlobleExceptionHandler {
    /**
     * 对全局抛出的异常进行了处理
     * 思考如何进行优化
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println(e);
        if (e instanceof BindException) {                   //参数校验错误
            BindException be = (BindException) e;
            FieldError error = be.getFieldError();
            String message = error.getDefaultMessage();
            Result result = new Result(ResultCode.BindException);
            result.setMessage(message);
            return result;
        } else if (e instanceof MethodArgumentNotValidException) {        //参数校验错误
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
            String message = manve.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            Result result = new Result(ResultCode.BindException);
            result.setMessage(message);
            return result;
        } else if (e instanceof DuplicateKeyException) {                        //唯一键插入重复数据
            return new Result(ResultCode.DuplicateKeyException);
        } else if (e instanceof IncorrectCredentialsException) {                           //密码不正确
            return new Result(ResultCode.IncorrectCredentialsException);
        } else if (e instanceof UnknownAccountException) {                 //此账号不存在
            return new Result(ResultCode.UnknownAccountException);
        } else if (e instanceof LockedAccountException) {           //此账号已被锁定
            return new Result(ResultCode.LockedAccountException);
        } else if (e instanceof AuthenticationException) {          //认证异常
            return new Result(ResultCode.AuthenticationException);
        } else if (e instanceof IllegalArgumentException) {         //参数不合法
            Result result = new Result(ResultCode.IllegalArgumentException);
            result.setMessage(e.getMessage());
            return result;
        } else if (e instanceof UnauthenticatedException) {        //用户没有登录
            return new Result(ResultCode.UnauthenticatedException);
        } else {
            return Result.error();                             //其它异常
        }
    }
}
