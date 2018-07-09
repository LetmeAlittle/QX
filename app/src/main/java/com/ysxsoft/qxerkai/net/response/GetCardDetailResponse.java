package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetCardDetailResponse extends BaseResponse {

    /**
     * data : {"top":{"dates":"2018-06-13 09:59:32","user_id":10157,"looks":0,"likes":0,"tid":9,"nick_name":null,"imgs":[],"avatar":null,"title":"发表的标题2112212321321sdfds 121aa","content":"发表的呃逆荣1221212321321asdfdsafdsfdsaa123123a","is_like":"0","com_num":1},"list":{"current_page":1,"data":[{"dates":"2018-06-13 09:59:52","user_id":10157,"likes":0,"nick_name":null,"avatar":null,"content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","cid":1}],"first_page_url":"http://116.62.217.183/api/friend/carddetail?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/friend/carddetail?page=1","next_page_url":null,"path":"http://116.62.217.183/api/friend/carddetail","per_page":10,"prev_page_url":null,"to":1,"total":1}}
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
         * top : {"dates":"2018-06-13 09:59:32","user_id":10157,"looks":0,"likes":0,"tid":9,"nick_name":null,"imgs":[],"avatar":null,"title":"发表的标题2112212321321sdfds 121aa","content":"发表的呃逆荣1221212321321asdfdsafdsfdsaa123123a","is_like":"0","com_num":1}
         * list : {"current_page":1,"data":[{"dates":"2018-06-13 09:59:52","user_id":10157,"likes":0,"nick_name":null,"avatar":null,"content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","cid":1}],"first_page_url":"http://116.62.217.183/api/friend/carddetail?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/friend/carddetail?page=1","next_page_url":null,"path":"http://116.62.217.183/api/friend/carddetail","per_page":10,"prev_page_url":null,"to":1,"total":1}
         */

        private TopBean top;
        private ListBean list;

        public TopBean getTop() {
            return top;
        }

        public void setTop(TopBean top) {
            this.top = top;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class TopBean {
            /**
             * dates : 2018-06-13 09:59:32
             * user_id : 10157
             * looks : 0
             * likes : 0
             * tid : 9
             * nick_name : null
             * imgs : []
             * avatar : null
             * title : 发表的标题2112212321321sdfds 121aa
             * content : 发表的呃逆荣1221212321321asdfdsafdsfdsaa123123a
             * is_like : 0
             * com_num : 1
             */

            private String dates;
            private int user_id;
            private int looks;
            private int likes;
            private int tid;
            private String nick_name;
            private String avatar;
            private String title;
            private String content;
            private String is_like;
            private int com_num;
            private List<ImageBean> imgs;

            public String getDates() {
                return dates;
            }

            public void setDates(String dates) {
                this.dates = dates;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
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

            public String getIs_like() {
                return is_like;
            }

            public void setIs_like(String is_like) {
                this.is_like = is_like;
            }

            public int getCom_num() {
                return com_num;
            }

            public void setCom_num(int com_num) {
                this.com_num = com_num;
            }

            public List<ImageBean> getImgs() {
                return imgs;
            }

            public void setImgs(List<ImageBean> imgs) {
                this.imgs = imgs;
            }
        }

        public static class ImageBean {
            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class ListBean {
            /**
             * current_page : 1
             * data : [{"dates":"2018-06-13 09:59:52","user_id":10157,"likes":0,"nick_name":null,"avatar":null,"content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","cid":1}]
             * first_page_url : http://116.62.217.183/api/friend/carddetail?page=1
             * from : 1
             * last_page : 1
             * last_page_url : http://116.62.217.183/api/friend/carddetail?page=1
             * next_page_url : null
             * path : http://116.62.217.183/api/friend/carddetail
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
                 * dates : 2018-06-13 09:59:52
                 * user_id : 10157
                 * likes : 0
                 * nick_name : null
                 * avatar : null
                 * content : 12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a
                 * cid : 1
                 */

                private String dates;
                private int user_id;
                private int likes;
                private String nick_name;
                private String avatar;
                private String content;
                private int cid;

                public String getDates() {
                    return dates;
                }

                public void setDates(String dates) {
                    this.dates = dates;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
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

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getCid() {
                    return cid;
                }

                public void setCid(int cid) {
                    this.cid = cid;
                }
            }
        }
    }
}
