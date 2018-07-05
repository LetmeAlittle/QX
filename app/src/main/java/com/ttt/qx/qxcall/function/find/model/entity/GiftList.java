package com.ttt.qx.qxcall.function.find.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/11/2.
 */

public class GiftList {

    /**
     * message : success
     * status_code : 200
     * data : [{"id":1,"gift_name":"鲜花","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_01.png","gift_price":10,"gift_desc":0},{"id":2,"gift_name":"棒棒糖","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_02.png","gift_price":20,"gift_desc":0},{"id":3,"gift_name":"钻戒","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_03.png","gift_price":50,"gift_desc":0},{"id":4,"gift_name":"魔镜","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_04.png","gift_price":50,"gift_desc":0},{"id":5,"gift_name":"金话筒","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_05.png","gift_price":100,"gift_desc":0},{"id":6,"gift_name":"啤酒炸鸡","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_06.png","gift_price":100,"gift_desc":0},{"id":7,"gift_name":"飞吻","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_07.png","gift_price":100,"gift_desc":0},{"id":8,"gift_name":"宝箱","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_08.png","gift_price":200,"gift_desc":0},{"id":9,"gift_name":"保时捷","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_09.png","gift_price":500,"gift_desc":0},{"id":10,"gift_name":"法拉利","gift_img":"http://116.62.217.183/storage/gifts/October2017/gift_10.png","gift_price":500,"gift_desc":0}]
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
         * gift_name : 鲜花
         * gift_img : http://116.62.217.183/storage/gifts/October2017/gift_01.png
         * gift_price : 10
         * gift_desc : 0
         */

        private int id;
        private String gift_name;
        private String gift_img;
        private int gift_price;
        private int gift_desc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getGift_img() {
            return gift_img;
        }

        public void setGift_img(String gift_img) {
            this.gift_img = gift_img;
        }

        public int getGift_price() {
            return gift_price;
        }

        public void setGift_price(int gift_price) {
            this.gift_price = gift_price;
        }

        public int getGift_desc() {
            return gift_desc;
        }

        public void setGift_desc(int gift_desc) {
            this.gift_desc = gift_desc;
        }
    }
}
