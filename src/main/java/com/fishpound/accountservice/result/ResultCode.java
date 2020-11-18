package com.fishpound.accountservice.result;

/**
 * 返回结果枚举类型
 */
public enum ResultCode {
    SUCCESS(1, "成功"),
    FAIL(0, "失败"),

    /*token错误*/
    TOKEN_NOT_VALID(11, "token失效"),
    TOKEN_IS_NULL(12, "token为空"),

    /*用户错误*/
    USER_NOT_LOGIN(21, "用户未登录"),
    USER_ACCOUNT_EXPIRED(22, "用户账户过期"),
    USER_CREDENTIALS_ERROR(23, "账号密码错误"),
    USER_CREDENTIALS_EXPIRED(24, "密码过期"),
    USER_ACCOUNT_DISABLE(25, "账号不可用"),
    USER_ACCOUNT_LOCKED(26, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(27, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(28, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(29, "账号下线"),

    /*业务错误*/
    NO_PERMISSION(31, "没有权限");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessageByCode(Integer code){
        for(ResultCode resc : values()){
            if(resc.getCode().equals(code)){
                return resc.getMessage();
            }
        }
        return null;
    }
}
