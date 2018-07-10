package com.ttt.qx.qxcall.function.register.model.entity;

public class CommitPyqBgResponse {


    /**
     * message : 获取成功！！
     * status_code : 200
     * data : http://q2wqw.com/pic.png
     */

    private String message;
    private int status_code;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
