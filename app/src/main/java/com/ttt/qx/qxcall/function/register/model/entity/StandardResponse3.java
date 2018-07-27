package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * 后台数据标准返回
 * <p>
 * Created by wyd on 2017/10/10.
 */
public class StandardResponse3 {

    /**
     * message : success
     * status_code : 200
     * data : {"is_allow":1,"member_account":11523.799999999963}
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
        /**
         * is_allow : 1
         * member_account : 11523.799999999963
         */

        private int is_allow;
        private double member_account;

        public int getIs_allow() {
            return is_allow;
        }

        public void setIs_allow(int is_allow) {
            this.is_allow = is_allow;
        }

        public double getMember_account() {
            return member_account;
        }

        public void setMember_account(double member_account) {
            this.member_account = member_account;
        }
    }
}
