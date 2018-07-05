package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * 上传图片
 *
 * Created by wyd on 2017/10/10.
 */
public class UploadImgResponse {
    /**
     * message : success
     * status_code : 200
     * data : {"avatar":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE1MDc2MjY2OTQsImV4cCI6MTUwNzYzMDI5NCwibmJmIjoxNTA3NjI2Njk0LCJqdGkiOiJ1eDJ0bTFLZUZRZkJqaTZCIiwic3ViIjo0LCJwcnYiOiJhZmQwZmQ5YmRkOWFjNzJmZjM5ODM0MWYxZDYyODQwY2JmNGM3MTY3In0.WIIFjj37zX1DZGVu6DwS1Ru6BXxMl1mj-CSzXGJXbSU"}
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
         * avatar : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvcmVnaXN0ZXIiLCJpYXQiOjE1MDc2MjY2OTQsImV4cCI6MTUwNzYzMDI5NCwibmJmIjoxNTA3NjI2Njk0LCJqdGkiOiJ1eDJ0bTFLZUZRZkJqaTZCIiwic3ViIjo0LCJwcnYiOiJhZmQwZmQ5YmRkOWFjNzJmZjM5ODM0MWYxZDYyODQwY2JmNGM3MTY3In0.WIIFjj37zX1DZGVu6DwS1Ru6BXxMl1mj-CSzXGJXbSU
         */

        private String avatar;
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
