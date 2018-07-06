package com.ttt.qx.qxcall.function.home.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/28.
 */

public class CommonTagList {

    /**
     * message : success
     * status_code : 200
     * data : [{"name":"精选","id":"best"},{"name":"在线","id":"online"},{"id":1,"name":"哄你睡觉"},{"id":2,"name":"情感聊天"}]
     */

    private String message;
    private int status_code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 精选
         * id : best
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommonTagList{" +
                "message='" + message + '\'' +
                ", status_code=" + status_code +
                ", data=" + data +
                '}';
    }
}
