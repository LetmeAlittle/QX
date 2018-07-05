package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * 后台数据标准返回
 * <p>
 * Created by wyd on 2017/10/10.
 */
public class StandardResponse2 {

    /**
     * message : 保存成功！
     * status_code : 200
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
