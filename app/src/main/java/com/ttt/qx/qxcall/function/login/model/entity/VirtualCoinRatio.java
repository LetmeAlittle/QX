package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * Created by 王亚东 on 2017/10/17.
 */

public class VirtualCoinRatio {

    /**
     * message : 成功
     * status_code : 200
     * data : {"ratio":10}
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
         * ratio : 10
         */

        private int ratio;

        public int getRatio() {
            return ratio;
        }

        public void setRatio(int ratio) {
            this.ratio = ratio;
        }
    }
}
