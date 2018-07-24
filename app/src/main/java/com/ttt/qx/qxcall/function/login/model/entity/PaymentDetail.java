package com.ttt.qx.qxcall.function.login.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/11/12.
 */

public class PaymentDetail {

    /**
     * message : success
     * status_code : 200
     * data : {"current_page":1,"data":[{"id":6,"create_at":"2017-10-23 19:17","amount":20,"log_desc":"20 20 钻石"},{"id":9,"create_at":"2017-10-23 19:17","amount":3,"log_desc":"偷听 3 钻石"},{"id":12,"create_at":"2017-10-23 19:20","amount":3,"log_desc":"偷听 3 钻石"},{"id":15,"create_at":"2017-10-25 10:04","amount":0,"log_desc":"偷听 0 钻石"},{"id":16,"create_at":"2017-10-25 21:03","amount":0,"log_desc":"偷听 0 钻石"},{"id":19,"create_at":"2017-10-25 22:06","amount":0,"log_desc":"偷听 0 钻石"},{"id":22,"create_at":"2017-10-25 22:06","amount":0,"log_desc":"偷听 0 钻石"},{"id":25,"create_at":"2017-10-25 22:07","amount":0,"log_desc":"偷听 0 钻石"},{"id":28,"create_at":"2017-10-25 22:07","amount":0,"log_desc":"偷听 0 钻石"},{"id":31,"create_at":"2017-10-25 22:07","amount":0,"log_desc":"偷听 0 钻石"}],"first_page_url":"http://116.62.217.183/api/user/account_log?page=1","from":1,"last_page":54,"last_page_url":"http://116.62.217.183/api/user/account_log?page=54","next_page_url":"http://116.62.217.183/api/user/account_log?page=2","path":"http://116.62.217.183/api/user/account_log","per_page":10,"prev_page_url":null,"to":10,"total":532}
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
         * data : [{"id":6,"create_at":"2017-10-23 19:17","amount":20,"log_desc":"20 20 钻石"},{"id":9,"create_at":"2017-10-23 19:17","amount":3,"log_desc":"偷听 3 钻石"},{"id":12,"create_at":"2017-10-23 19:20","amount":3,"log_desc":"偷听 3 钻石"},{"id":15,"create_at":"2017-10-25 10:04","amount":0,"log_desc":"偷听 0 钻石"},{"id":16,"create_at":"2017-10-25 21:03","amount":0,"log_desc":"偷听 0 钻石"},{"id":19,"create_at":"2017-10-25 22:06","amount":0,"log_desc":"偷听 0 钻石"},{"id":22,"create_at":"2017-10-25 22:06","amount":0,"log_desc":"偷听 0 钻石"},{"id":25,"create_at":"2017-10-25 22:07","amount":0,"log_desc":"偷听 0 钻石"},{"id":28,"create_at":"2017-10-25 22:07","amount":0,"log_desc":"偷听 0 钻石"},{"id":31,"create_at":"2017-10-25 22:07","amount":0,"log_desc":"偷听 0 钻石"}]
         * first_page_url : http://116.62.217.183/api/user/account_log?page=1
         * from : 1
         * last_page : 54
         * last_page_url : http://116.62.217.183/api/user/account_log?page=54
         * next_page_url : http://116.62.217.183/api/user/account_log?page=2
         * path : http://116.62.217.183/api/user/account_log
         * per_page : 10
         * prev_page_url : null
         * to : 10
         * total : 532
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private String next_page_url;
        private String path;
        private int per_page;
        private String prev_page_url;
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

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
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

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
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
             * id : 6
             * create_at : 2017-10-23 19:17
             * amount : 20
             * log_desc : 20 20 钻石
             */

            private int id;
            private String create_at;
            private String amount;
            private String log_desc;

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

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getLog_desc() {
                return log_desc;
            }

            public void setLog_desc(String log_desc) {
                this.log_desc = log_desc;
            }
        }
    }
}
