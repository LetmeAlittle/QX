package com.ttt.qx.qxcall.function.login.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/12/16.
 */

public class InvitedRecord {

    /**
     * message : success!
     * status_code : 200
     * data : {"current_page":1,"data":[{"mobile":"13700862358","nick_name":" ","create_time":"2017-12-16 16:44:40"},{"mobile":"13700862356","nick_name":" ","create_time":"2017-12-16 16:42:55"}],"first_page_url":"http://116.62.217.183/api/share/list/1079?page=1","from":1,"next_page_url":null,"path":"http://116.62.217.183/api/share/list/1079","per_page":10,"prev_page_url":null,"to":2}
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
         * data : [{"mobile":"13700862358","nick_name":" ","create_time":"2017-12-16 16:44:40"},{"mobile":"13700862356","nick_name":" ","create_time":"2017-12-16 16:42:55"}]
         * first_page_url : http://116.62.217.183/api/share/list/1079?page=1
         * from : 1
         * next_page_url : null
         * path : http://116.62.217.183/api/share/list/1079
         * per_page : 10
         * prev_page_url : null
         * to : 2
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * mobile : 13700862358
             * nick_name :
             * create_time : 2017-12-16 16:44:40
             */

            private String mobile;
            private String nick_name;
            private String create_time;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
