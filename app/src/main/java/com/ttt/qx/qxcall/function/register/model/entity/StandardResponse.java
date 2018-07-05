package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * 后台数据标准返回
 * <p>
 * Created by wyd on 2017/10/10.
 */
public class StandardResponse {
    /**
     * message : success
     * status_code : 200
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE1MDc2MjY2OTQsImV4cCI6MTUwNzYzMDI5NCwibmJmIjoxNTA3NjI2Njk0LCJqdGkiOiJ1eDJ0bTFLZUZRZkJqaTZCIiwic3ViIjo0LCJwcnYiOiJhZmQwZmQ5YmRkOWFjNzJmZjM5ODM0MWYxZDYyODQwY2JmNGM3MTY3In0.WIIFjj37zX1DZGVu6DwS1Ru6BXxMl1mj-CSzXGJXbSU"}
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
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE1MDc2MjY2OTQsImV4cCI6MTUwNzYzMDI5NCwibmJmIjoxNTA3NjI2Njk0LCJqdGkiOiJ1eDJ0bTFLZUZRZkJqaTZCIiwic3ViIjo0LCJwcnYiOiJhZmQwZmQ5YmRkOWFjNzJmZjM5ODM0MWYxZDYyODQwY2JmNGM3MTY3In0.WIIFjj37zX1DZGVu6DwS1Ru6BXxMl1mj-CSzXGJXbSU
         */

        private String token;
        private String path;
        private String state;
        private double amount;
        private int is_allow;
        private int member_account;
        //会员到期结束时间
        private Long vip_end_time;
        private int is_black;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Long getVip_end_time() {
            return vip_end_time;
        }

        public void setVip_end_time(Long vip_end_time) {
            this.vip_end_time = vip_end_time;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getIs_black() {
            return is_black;
        }

        public void setIs_black(int is_black) {
            this.is_black = is_black;
        }

        public int getIs_allow() {
            return is_allow;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setIs_allow(int is_allow) {
            this.is_allow = is_allow;
        }

        public int getMember_account() {
            return member_account;
        }

        public void setMember_account(int member_account) {
            this.member_account = member_account;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
