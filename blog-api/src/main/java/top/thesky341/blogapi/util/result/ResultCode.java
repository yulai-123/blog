package top.thesky341.blogapi.util.result;

public enum ResultCode {
    SUCCESS(0, "成功"),
    ERROR(1, "错误"),
    DuplicateKeyException(101, "插入的唯一性字段数据已存在"),
    BindException(111, "参数校验失败"),
    NeedLogin(121, "该操作需要先登录"),
    IncorrectCredentialsException(131, "密码不正确"),
    UnknownAccountException(132, "此账号不存在"),
    LockedAccountException(133, "此账号已被锁定"),
    AuthenticationException(134, "认证异常"),
    UnauthenticatedException(141, "用户没有登录"),
    IllegalArgumentException(151, "参数不合法"),
    NotExistArticle(161, "博客不存在"),
    NotExistCategory(162, "分类不存在");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
