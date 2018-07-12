package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class MyLiWuResponse extends BaseResponse{

    /**
     * data : {"current_page":1,"data":[{"id":21,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":13140,"gift_num":1,"gift_amount":13140,"gift_id":21,"created_at":"2017-11-02 22:00:51","updated_at":"2017-11-02 22:00:51","gift_name":"别墅","gift_img":"gifts/November2017/gift_11@3x.png","gift_desc":0,"cnt":2},{"id":18,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":1314,"gift_num":1,"gift_amount":1314,"gift_id":18,"created_at":"2017-11-02 21:59:53","updated_at":"2017-11-02 21:59:53","gift_name":"跑车","gift_img":"gifts/November2017/gift_08@3x.png","gift_desc":0,"cnt":4},{"id":14,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":188,"gift_num":1,"gift_amount":188,"gift_id":14,"created_at":"2017-11-02 21:58:33","updated_at":"2017-11-02 21:58:33","gift_name":"小熊","gift_img":"gifts/November2017/gift_04@3x.png","gift_desc":0,"cnt":1},{"id":13,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":96,"gift_num":1,"gift_amount":96,"gift_id":13,"created_at":"2017-11-02 21:58:18","updated_at":"2017-11-02 21:58:18","gift_name":"么么哒","gift_img":"gifts/November2017/gift_03@3x.png","gift_desc":0,"cnt":1},{"id":12,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":52,"gift_num":1,"gift_amount":52,"gift_id":12,"created_at":"2017-11-02 21:57:58","updated_at":"2017-11-02 21:57:58","gift_name":"玫瑰花","gift_img":"gifts/November2017/gift_02@3x.png","gift_desc":0,"cnt":1},{"id":11,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":10,"gift_num":1,"gift_amount":10,"gift_id":11,"created_at":"2017-11-02 21:57:38","updated_at":"2017-11-02 21:57:38","gift_name":"荧光棒","gift_img":"gifts/November2017/gift_01@3x.png","gift_desc":0,"cnt":1}],"first_page_url":"http://116.62.217.183/api/friend/gift_top_my?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/friend/gift_top_my?page=1","next_page_url":null,"path":"http://116.62.217.183/api/friend/gift_top_my","per_page":15,"prev_page_url":null,"to":6,"total":6}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":21,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":13140,"gift_num":1,"gift_amount":13140,"gift_id":21,"created_at":"2017-11-02 22:00:51","updated_at":"2017-11-02 22:00:51","gift_name":"别墅","gift_img":"gifts/November2017/gift_11@3x.png","gift_desc":0,"cnt":2},{"id":18,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":1314,"gift_num":1,"gift_amount":1314,"gift_id":18,"created_at":"2017-11-02 21:59:53","updated_at":"2017-11-02 21:59:53","gift_name":"跑车","gift_img":"gifts/November2017/gift_08@3x.png","gift_desc":0,"cnt":4},{"id":14,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":188,"gift_num":1,"gift_amount":188,"gift_id":14,"created_at":"2017-11-02 21:58:33","updated_at":"2017-11-02 21:58:33","gift_name":"小熊","gift_img":"gifts/November2017/gift_04@3x.png","gift_desc":0,"cnt":1},{"id":13,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":96,"gift_num":1,"gift_amount":96,"gift_id":13,"created_at":"2017-11-02 21:58:18","updated_at":"2017-11-02 21:58:18","gift_name":"么么哒","gift_img":"gifts/November2017/gift_03@3x.png","gift_desc":0,"cnt":1},{"id":12,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":52,"gift_num":1,"gift_amount":52,"gift_id":12,"created_at":"2017-11-02 21:57:58","updated_at":"2017-11-02 21:57:58","gift_name":"玫瑰花","gift_img":"gifts/November2017/gift_02@3x.png","gift_desc":0,"cnt":1},{"id":11,"member_id":10148,"item_id":0,"item_type":3,"recive_member_id":10157,"gift_price":10,"gift_num":1,"gift_amount":10,"gift_id":11,"created_at":"2017-11-02 21:57:38","updated_at":"2017-11-02 21:57:38","gift_name":"荧光棒","gift_img":"gifts/November2017/gift_01@3x.png","gift_desc":0,"cnt":1}]
         * first_page_url : http://116.62.217.183/api/friend/gift_top_my?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://116.62.217.183/api/friend/gift_top_my?page=1
         * next_page_url : null
         * path : http://116.62.217.183/api/friend/gift_top_my
         * per_page : 15
         * prev_page_url : null
         * to : 6
         * total : 6
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 21
             * member_id : 10148
             * item_id : 0
             * item_type : 3
             * recive_member_id : 10157
             * gift_price : 13140
             * gift_num : 1
             * gift_amount : 13140
             * gift_id : 21
             * created_at : 2017-11-02 22:00:51
             * updated_at : 2017-11-02 22:00:51
             * gift_name : 别墅
             * gift_img : gifts/November2017/gift_11@3x.png
             * gift_desc : 0
             * cnt : 2
             */

            private int id;
            private int member_id;
            private int item_id;
            private int item_type;
            private int recive_member_id;
            private int gift_price;
            private int gift_num;
            private int gift_amount;
            private int gift_id;
            private String created_at;
            private String updated_at;
            private String gift_name;
            private String gift_img;
            private int gift_desc;
            private int cnt;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public int getItem_type() {
                return item_type;
            }

            public void setItem_type(int item_type) {
                this.item_type = item_type;
            }

            public int getRecive_member_id() {
                return recive_member_id;
            }

            public void setRecive_member_id(int recive_member_id) {
                this.recive_member_id = recive_member_id;
            }

            public int getGift_price() {
                return gift_price;
            }

            public void setGift_price(int gift_price) {
                this.gift_price = gift_price;
            }

            public int getGift_num() {
                return gift_num;
            }

            public void setGift_num(int gift_num) {
                this.gift_num = gift_num;
            }

            public int getGift_amount() {
                return gift_amount;
            }

            public void setGift_amount(int gift_amount) {
                this.gift_amount = gift_amount;
            }

            public int getGift_id() {
                return gift_id;
            }

            public void setGift_id(int gift_id) {
                this.gift_id = gift_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
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

            public int getGift_desc() {
                return gift_desc;
            }

            public void setGift_desc(int gift_desc) {
                this.gift_desc = gift_desc;
            }

            public int getCnt() {
                return cnt;
            }

            public void setCnt(int cnt) {
                this.cnt = cnt;
            }
        }
    }
}
