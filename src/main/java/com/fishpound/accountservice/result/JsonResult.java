package com.fishpound.accountservice.result;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {
    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public JsonResult() {
    }

    public JsonResult(Boolean success) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode();
        this.msg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.FAIL.getMessage();
    }

    public JsonResult(Boolean success, T data) {
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() : ResultCode.FAIL.getCode();
        this.msg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.FAIL.getMessage();
        this.data = data;
    }

    public JsonResult(Boolean success, ResultCode res){
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() :
                                (res == null ? ResultCode.FAIL.getCode() : res.getCode());
        this.msg = success ? ResultCode.SUCCESS.getMessage() :
                                (res == null ? ResultCode.FAIL.getMessage() : res.getMessage());
    }

    public JsonResult(Boolean success, ResultCode res, T data){
        this.success = success;
        this.code = success ? ResultCode.SUCCESS.getCode() :
                (res == null ? ResultCode.FAIL.getCode() : res.getCode());
        this.msg = success ? ResultCode.SUCCESS.getMessage() :
                (res == null ? ResultCode.FAIL.getMessage() : res.getMessage());
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
