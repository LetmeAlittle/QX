package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * app 网络请求返回状态实体类
 * Created by wyd on 2017/7/27.
 */
public class ResponseStatus {
    /**
     * status : 200
     * data : message content
     * errorMessage : null
     * isSuccess : true
     */

    private String status;
    private String data;
    private Object errorMessage;
    private boolean isSuccess;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
