package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class LiaoRenResponse  extends BaseResponse{


    /**
     * data : {"list":{"current_page":2,"data":[{"dates":"2018-06-13 10:04:54","imgs":"storage/dog/6390152885567510157.png,storage/dog/7340152885567510157.png","likes":0,"nick_name":"8965","avatar":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":33,"imgss":"http://116.62.217.183/storage/dog/7340152885567510157.png","com_num":0},{"dates":"2018-06-13 10:04:53","imgs":null,"likes":0,"nick_name":"8965","avatar":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":32,"imgss":"","com_num":0}],"first_page_url":"http://116.62.217.183/api/friend/cardList?page=1","from":11,"last_page":2,"last_page_url":"http://116.62.217.183/api/friend/cardList?page=2","next_page_url":null,"path":"http://116.62.217.183/api/friend/cardList","per_page":10,"prev_page_url":"http://116.62.217.183/api/friend/cardList?page=1","to":12,"total":12}}
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
         * list : {"current_page":2,"data":[{"dates":"2018-06-13 10:04:54","imgs":"storage/dog/6390152885567510157.png,storage/dog/7340152885567510157.png","likes":0,"nick_name":"8965","avatar":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":33,"imgss":"http://116.62.217.183/storage/dog/7340152885567510157.png","com_num":0},{"dates":"2018-06-13 10:04:53","imgs":null,"likes":0,"nick_name":"8965","avatar":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":32,"imgss":"","com_num":0}],"first_page_url":"http://116.62.217.183/api/friend/cardList?page=1","from":11,"last_page":2,"last_page_url":"http://116.62.217.183/api/friend/cardList?page=2","next_page_url":null,"path":"http://116.62.217.183/api/friend/cardList","per_page":10,"prev_page_url":"http://116.62.217.183/api/friend/cardList?page=1","to":12,"total":12}
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
             * current_page : 2
             * data : [{"dates":"2018-06-13 10:04:54","imgs":"storage/dog/6390152885567510157.png,storage/dog/7340152885567510157.png","likes":0,"nick_name":"8965","avatar":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":33,"imgss":"http://116.62.217.183/storage/dog/7340152885567510157.png","com_num":0},{"dates":"2018-06-13 10:04:53","imgs":null,"likes":0,"nick_name":"8965","avatar":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":32,"imgss":"","com_num":0}]
             * first_page_url : http://116.62.217.183/api/friend/cardList?page=1
             * from : 11
             * last_page : 2
             * last_page_url : http://116.62.217.183/api/friend/cardList?page=2
             * next_page_url : null
             * path : http://116.62.217.183/api/friend/cardList
             * per_page : 10
             * prev_page_url : http://116.62.217.183/api/friend/cardList?page=1
             * to : 12
             * total : 12
             */

            private int current_page;
            private String first_page_url;
            private int from;
            private int last_page;
            private String last_page_url;
            private Object next_page_url;
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
                 * dates : 2018-06-13 10:04:54
                 * imgs : storage/dog/6390152885567510157.png,storage/dog/7340152885567510157.png
                 * likes : 0
                 * nick_name : 8965
                 * avatar : http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg
                 * title : 321321s234234 121aa1
                 * content : 12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a
                 * tid : 33
                 * imgss : http://116.62.217.183/storage/dog/7340152885567510157.png
                 * com_num : 0
                 */

                private String dates;
                private String imgs;
                private int likes;
                private String nick_name;
                private String avatar;
                private String title;
                private String content;
                private int tid;
                private String imgss;
                private int com_num;
                private String looks;
                private String is_like;

                public String getIs_like() {
                    return is_like;
                }

                public void setIs_like(String is_like) {
                    this.is_like = is_like;
                }

                public String getLooks() {
                    return looks;
                }

                public void setLooks(String looks) {
                    this.looks = looks;
                }

                public String getDates() {
                    return dates;
                }

                public void setDates(String dates) {
                    this.dates = dates;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
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
            }
        }
    }
}
