package com.ttt.qx.qxcall.function.login.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/11/4.
 */

public class UserTypeSkillList {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 声优聊天
         * color : #000000
         */

        private int id;
        private String name;
        private String color;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
