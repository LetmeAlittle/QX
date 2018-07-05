package com.ttt.qx.qxcall.function.login.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/11/4.
 */

public class VIPBuyList {

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
         * text : 1天VIP
         * price : 1
         * price_text : ￥1
         * day : 1
         */

        private int id;
        private String text;
        private int price;
        private String price_text;
        private int day;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getPrice_text() {
            return price_text;
        }

        public void setPrice_text(String price_text) {
            this.price_text = price_text;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
}
