package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * Created by 王亚东 on 2017/10/15.
 */

public class LoginedResponse {


    /**
     * message : 登录成功！
     * status_code : 200
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1MDgwNDgzMTksImV4cCI6MTUwODEzNDcxOSwibmJmIjoxNTA4MDQ4MzE5LCJqdGkiOiJtd1I3cWNvMURGQk1BcU13Iiwic3ViIjoyMCwicHJ2IjoiYWZkMGZkOWJkZDlhYzcyZmYzOTgzNDFmMWQ2Mjg0MGNiZjRjNzE2NyJ9.7rYwjpmktWcJplIcxAANSbYg9zxkfkp15nXf6K8hims","im_info":{},"need_info":0}
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
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1MDgwNDgzMTksImV4cCI6MTUwODEzNDcxOSwibmJmIjoxNTA4MDQ4MzE5LCJqdGkiOiJtd1I3cWNvMURGQk1BcU13Iiwic3ViIjoyMCwicHJ2IjoiYWZkMGZkOWJkZDlhYzcyZmYzOTgzNDFmMWQ2Mjg0MGNiZjRjNzE2NyJ9.7rYwjpmktWcJplIcxAANSbYg9zxkfkp15nXf6K8hims
         * im_info : {}
         * need_info : 0
         */

        private String token;
        private ImInfoBean im_info;
        private int need_info;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public ImInfoBean getIm_info() {
            return im_info;
        }

        public void setIm_info(ImInfoBean im_info) {
            this.im_info = im_info;
        }

        public int getNeed_info() {
            return need_info;
        }

        public void setNeed_info(int need_info) {
            this.need_info = need_info;
        }

        public static class ImInfoBean {
        }
    }
}
