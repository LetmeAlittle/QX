package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetLiaoRenListResponse extends BaseResponse{


    /**
     * data : {"list":{"current_page":1,"data":[{"dates":"2018-07-11 09:12:38","imgs":[{"img":"http://116.62.217.183/storage/dog/5726153127155810195.png"},{"img":"http://116.62.217.183/storage/dog/4983153127155810195.png"}],"looks":7,"likes":1,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":"早","content":"新的一天3","tid":77,"imgss":"http://116.62.217.183/storage/dog/4983153127155810195.png","com_num":1,"is_like":"1"},{"dates":"2018-07-10 18:36:24","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":76,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:52","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":75,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:51","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":74,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:06","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":73,"imgss":"","com_num":0,"is_like":"0"}],"first_page_url":"http://116.62.217.183/api/friend/myCard?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/friend/myCard?page=1","next_page_url":null,"path":"http://116.62.217.183/api/friend/myCard","per_page":10,"prev_page_url":null,"to":5,"total":5}}
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
         * list : {"current_page":1,"data":[{"dates":"2018-07-11 09:12:38","imgs":[{"img":"http://116.62.217.183/storage/dog/5726153127155810195.png"},{"img":"http://116.62.217.183/storage/dog/4983153127155810195.png"}],"looks":7,"likes":1,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":"早","content":"新的一天3","tid":77,"imgss":"http://116.62.217.183/storage/dog/4983153127155810195.png","com_num":1,"is_like":"1"},{"dates":"2018-07-10 18:36:24","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":76,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:52","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":75,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:51","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":74,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:06","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":73,"imgss":"","com_num":0,"is_like":"0"}],"first_page_url":"http://116.62.217.183/api/friend/myCard?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/friend/myCard?page=1","next_page_url":null,"path":"http://116.62.217.183/api/friend/myCard","per_page":10,"prev_page_url":null,"to":5,"total":5}
         */

        private ListBean list;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * current_page : 1
             * data : [{"dates":"2018-07-11 09:12:38","imgs":[{"img":"http://116.62.217.183/storage/dog/5726153127155810195.png"},{"img":"http://116.62.217.183/storage/dog/4983153127155810195.png"}],"looks":7,"likes":1,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":"早","content":"新的一天3","tid":77,"imgss":"http://116.62.217.183/storage/dog/4983153127155810195.png","com_num":1,"is_like":"1"},{"dates":"2018-07-10 18:36:24","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":76,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:52","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":75,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:51","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":74,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-07-10 18:35:06","imgs":[],"looks":0,"likes":0,"nick_name":"Sincerly","avatar":"http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png","title":null,"content":null,"tid":73,"imgss":"","com_num":0,"is_like":"0"}]
             * first_page_url : http://116.62.217.183/api/friend/myCard?page=1
             * from : 1
             * last_page : 1
             * last_page_url : http://116.62.217.183/api/friend/myCard?page=1
             * next_page_url : null
             * path : http://116.62.217.183/api/friend/myCard
             * per_page : 10
             * prev_page_url : null
             * to : 5
             * total : 5
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
                 * dates : 2018-07-11 09:12:38
                 * imgs : [{"img":"http://116.62.217.183/storage/dog/5726153127155810195.png"},{"img":"http://116.62.217.183/storage/dog/4983153127155810195.png"}]
                 * looks : 7
                 * likes : 1
                 * nick_name : Sincerly
                 * avatar : http://116.62.217.183/storage/avatar/2018/07/09/avatar_1531126162_10195.png
                 * title : 早
                 * content : 新的一天3
                 * tid : 77
                 * imgss : http://116.62.217.183/storage/dog/4983153127155810195.png
                 * com_num : 1
                 * is_like : 1
                 */

                private String dates;
                private int looks;
                private int likes;
                private String nick_name;
                private String avatar;
                private String title;
                private String content;
                private int tid;
                private String imgss;
                private int com_num;
                private String is_like;
                private List<ImgsBean> imgs;

                public String getDates() {
                    return dates;
                }

                public void setDates(String dates) {
                    this.dates = dates;
                }

                public int getLooks() {
                    return looks;
                }

                public void setLooks(int looks) {
                    this.looks = looks;
                }

                public int getLikes() {
                    return likes;
                }

                public void setLikes(int likes) {
                    this.likes = likes;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getTid() {
                    return tid;
                }

                public void setTid(int tid) {
                    this.tid = tid;
                }

                public String getImgss() {
                    return imgss;
                }

                public void setImgss(String imgss) {
                    this.imgss = imgss;
                }

                public int getCom_num() {
                    return com_num;
                }

                public void setCom_num(int com_num) {
                    this.com_num = com_num;
                }

                public String getIs_like() {
                    return is_like;
                }

                public void setIs_like(String is_like) {
                    this.is_like = is_like;
                }

                public List<ImgsBean> getImgs() {
                    return imgs;
                }

                public void setImgs(List<ImgsBean> imgs) {
                    this.imgs = imgs;
                }

                public static class ImgsBean {
                    /**
                     * img : http://116.62.217.183/storage/dog/5726153127155810195.png
                     */

                    private String img;

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }
                }
            }
        }
    }
}
