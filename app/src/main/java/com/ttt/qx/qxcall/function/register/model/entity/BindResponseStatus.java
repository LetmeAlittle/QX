package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * 绑定成功处理
 * Created by wyd on 2017/8/15.
 */
public class BindResponseStatus {
    /**
     * status : 200
     * data : {"message":"绑定成功","userInfo":null}
     * errorMessage : null
     * isSuccess : true
     */

    private String status;
    private DataBean data;
    private Object errorMessage;
    private boolean isSuccess;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * message : 绑定成功
         * userInfo : null
         */

        private String message;
        private Object userInfo;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(Object userInfo) {
            this.userInfo = userInfo;
        }
    }
}
