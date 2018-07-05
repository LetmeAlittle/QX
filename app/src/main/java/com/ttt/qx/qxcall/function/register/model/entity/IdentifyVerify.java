package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * Created by 王亚东 on 2017/11/5.
 */

public class IdentifyVerify {

    /**
     * message : 真实姓名不能为空！
     * status_code : 400
     * data : {}
     */

    private String message;
    private int status_code;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
