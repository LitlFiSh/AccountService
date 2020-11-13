package com.fishpound.accountservice.result;

/**
 * 统一 JSON 返回结果构造器
 */
public class ResultTool {
    public static JsonResult success(){
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data){
        return new JsonResult(true, data);
    }

    public static JsonResult fail(){
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode res){
        return new JsonResult(false, res);
    }
}
