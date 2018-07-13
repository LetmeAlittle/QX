package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetHuaTiListResponse  extends BaseResponse{

    /**
     * data : {"current_page":1,"data":[{"num":14,"title":"和异性如何聊天展开话题","gid":19,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"大庭广众之下摔倒了怎么办？","gid":18,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"如果你在图书馆看书，正入迷时，对面的异性用脚碰了你三次，你会？","gid":17,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"和异性如何聊天展开话题","gid":16,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"没有感情的婚姻还有没有必要？","gid":15,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"喜欢上好朋友怎么办？","gid":14,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"哈喽","gid":13,"is_vip":0,"user_id":78,"nick_name":"hello","icon":"http://116.62.217.183/storage/avatar/2017/12/12/avatar_1513053977_78.jpg"},{"num":1,"title":"什么样的两个人在一起才是最合适的呢？","gid":9,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"什么样的两个人才是合适的呢？","gid":8,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"你好吗👋","gid":2,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"}],"first_page_url":"http://116.62.217.183/api/gambitList?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/gambitList?page=1","next_page_url":null,"path":"http://116.62.217.183/api/gambitList","per_page":15,"prev_page_url":null,"to":10,"total":10}
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
         * data : [{"num":14,"title":"和异性如何聊天展开话题","gid":19,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"大庭广众之下摔倒了怎么办？","gid":18,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"如果你在图书馆看书，正入迷时，对面的异性用脚碰了你三次，你会？","gid":17,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"和异性如何聊天展开话题","gid":16,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"没有感情的婚姻还有没有必要？","gid":15,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"喜欢上好朋友怎么办？","gid":14,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"num":1,"title":"哈喽","gid":13,"is_vip":0,"user_id":78,"nick_name":"hello","icon":"http://116.62.217.183/storage/avatar/2017/12/12/avatar_1513053977_78.jpg"},{"num":1,"title":"什么样的两个人在一起才是最合适的呢？","gid":9,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"什么样的两个人才是合适的呢？","gid":8,"is_vip":0,"user_id":10182,"nick_name":"在一起","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"},{"num":1,"title":"你好吗👋","gid":2,"is_vip":0,"user_id":10148,"nick_name":"魔","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"}]
         * first_page_url : http://116.62.217.183/api/gambitList?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://116.62.217.183/api/gambitList?page=1
         * next_page_url : null
         * path : http://116.62.217.183/api/gambitList
         * per_page : 15
         * prev_page_url : null
         * to : 10
         * total : 10
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
             * num : 14
             * title : 和异性如何聊天展开话题
             * gid : 19
             * is_vip : 0
             * user_id : 10148
             * nick_name : 魔
             * icon : http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
             */

            private int num;
            private String title;
            private int gid;
            private int is_vip;
            private int user_id;
            private String nick_name;
            private String icon;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getGid() {
                return gid;
            }

            public void setGid(int gid) {
                this.gid = gid;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
