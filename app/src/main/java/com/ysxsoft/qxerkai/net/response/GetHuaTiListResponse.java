package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetHuaTiListResponse  extends BaseResponse{


    /**
     * data : {"current_page":1,"data":[{"num":23,"title":"你会介意TA有非常要好的异性朋友吗？","gid":50,"is_vip":0,"user_id":10196,"nick_name":"Sunny","icon":"http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png"},{"num":7,"title":"你觉得自己善良吗？","gid":49,"is_vip":0,"user_id":10196,"nick_name":"Sunny","icon":"http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png"},{"num":7,"title":"如果你在图书馆看书，正入迷时，对面的异性用脚碰了你三次，你会？","gid":46,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"121123123...........","gid":45,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":12,"title":"如果你在图书馆看书，正入迷时，对面的异性用脚碰了你三次，你会？","gid":44,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"3","gid":41,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"11111还","gid":40,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"大庭广众之下摔倒了怎么办？","gid":38,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"111","gid":37,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"tets","gid":36,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"你觉得自己善良吗？11111111","gid":35,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":9,"title":"y7777","gid":34,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"435443","gid":33,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"没有感情的婚姻还有没有必要？","gid":32,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"大庭广众之下摔倒了怎么办？","gid":30,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"}],"first_page_url":"http://116.62.217.183/api/gambitList?page=1","from":1,"last_page":3,"last_page_url":"http://116.62.217.183/api/gambitList?page=3","next_page_url":"http://116.62.217.183/api/gambitList?page=2","path":"http://116.62.217.183/api/gambitList","per_page":15,"prev_page_url":null,"to":15,"total":35}
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
         * data : [{"num":23,"title":"你会介意TA有非常要好的异性朋友吗？","gid":50,"is_vip":0,"user_id":10196,"nick_name":"Sunny","icon":"http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png"},{"num":7,"title":"你觉得自己善良吗？","gid":49,"is_vip":0,"user_id":10196,"nick_name":"Sunny","icon":"http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png"},{"num":7,"title":"如果你在图书馆看书，正入迷时，对面的异性用脚碰了你三次，你会？","gid":46,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"121123123...........","gid":45,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":12,"title":"如果你在图书馆看书，正入迷时，对面的异性用脚碰了你三次，你会？","gid":44,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"3","gid":41,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"11111还","gid":40,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":7,"title":"大庭广众之下摔倒了怎么办？","gid":38,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"111","gid":37,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"tets","gid":36,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"你觉得自己善良吗？11111111","gid":35,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":9,"title":"y7777","gid":34,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"435443","gid":33,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"没有感情的婚姻还有没有必要？","gid":32,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"},{"num":53,"title":"大庭广众之下摔倒了怎么办？","gid":30,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png"}]
         * first_page_url : http://116.62.217.183/api/gambitList?page=1
         * from : 1
         * last_page : 3
         * last_page_url : http://116.62.217.183/api/gambitList?page=3
         * next_page_url : http://116.62.217.183/api/gambitList?page=2
         * path : http://116.62.217.183/api/gambitList
         * per_page : 15
         * prev_page_url : null
         * to : 15
         * total : 35
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private String next_page_url;
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
             * num : 23
             * title : 你会介意TA有非常要好的异性朋友吗？
             * gid : 50
             * is_vip : 0
             * user_id : 10196
             * nick_name : Sunny
             * icon : http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png
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
