package com.ttt.qx.qxcall.function.login.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

    public class MentionRecordList {

    /**
     * message : success
     * status_code : 200
     * data : {"current_page":1,"data":[{"id":3,"create_at":"2017-11-06 09:18","member_id":20,"member_truename":"猫头鹰","member_alipay":"13700862355","member_id_front":"http://116.62.217.183/storage/image/20171105/15098942036843.png","member_id_back":"http://116.62.217.183/storage/image/20171105/15098942158570.png","amount":100,"state":"待处理","check_state":"已取消","pay_state":"待付款"}],"first_page_url":"http://116.62.217.183/api/user/cash_list?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/user/cash_list?page=1","next_page_url":null,"path":"http://116.62.217.183/api/user/cash_list","per_page":10,"prev_page_url":null,"to":1,"total":1}
     */

    private String message;
    private int status_code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":3,"create_at":"2017-11-06 09:18","member_id":20,"member_truename":"猫头鹰","member_alipay":"13700862355","member_id_front":"http://116.62.217.183/storage/image/20171105/15098942036843.png","member_id_back":"http://116.62.217.183/storage/image/20171105/15098942158570.png","amount":100,"state":"待处理","check_state":"已取消","pay_state":"待付款"}]
         * first_page_url : http://116.62.217.183/api/user/cash_list?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://116.62.217.183/api/user/cash_list?page=1
         * next_page_url : null
         * path : http://116.62.217.183/api/user/cash_list
         * per_page : 10
         * prev_page_url : null
         * to : 1
         * total : 1
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
             * id : 3
             * create_at : 2017-11-06 09:18
             * member_id : 20
             * member_truename : 猫头鹰
             * member_alipay : 13700862355
             * member_id_front : http://116.62.217.183/storage/image/20171105/15098942036843.png
             * member_id_back : http://116.62.217.183/storage/image/20171105/15098942158570.png
             * amount : 100
             * state : 待处理
             * check_state : 已取消
             * pay_state : 待付款
             */

            private int id;
            private String create_at;
            private int member_id;
            private String member_truename;
            private String member_alipay;
            private String member_id_front;
            private String member_id_back;
            private int amount;
            private String state;
            private String check_state;
            private String pay_state;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public String getMember_truename() {
                return member_truename;
            }

            public void setMember_truename(String member_truename) {
                this.member_truename = member_truename;
            }

            public String getMember_alipay() {
                return member_alipay;
            }

            public void setMember_alipay(String member_alipay) {
                this.member_alipay = member_alipay;
            }

            public String getMember_id_front() {
                return member_id_front;
            }

            public void setMember_id_front(String member_id_front) {
                this.member_id_front = member_id_front;
            }

            public String getMember_id_back() {
                return member_id_back;
            }

            public void setMember_id_back(String member_id_back) {
                this.member_id_back = member_id_back;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCheck_state() {
                return check_state;
            }

            public void setCheck_state(String check_state) {
                this.check_state = check_state;
            }

            public String getPay_state() {
                return pay_state;
            }

            public void setPay_state(String pay_state) {
                this.pay_state = pay_state;
            }
        }
    }
}
