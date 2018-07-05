package com.ttt.qx.qxcall.function.login.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/16.
 */

public class UserTagListResponse {

    /**
     * message : success
     * status_code : 200
     * data : [{"id":1,"text":"萝莉","color":"#dca3bf"}]
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
         * id : 1
         * text : 萝莉
         * color : #dca3bf
         */

        private int id;
        private String text;
        private String color;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
