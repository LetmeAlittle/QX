package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetCardListResponse extends BaseResponse {


    /**
     * data : {"list":{"current_page":1,"data":[{"dates":"2018-06-15 11:57:32","user_id":10148,"imgs":[{"img":"http://116.62.217.183/storage/dog/6451152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/8969152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/3710152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/1754152903505210148.png"}],"looks":34,"likes":2,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"你好，未来","content":"当爱情融入柴米油盐酱醋茶这些生活琐事中，两个人依旧相看两不厌，依然认定彼此就是自己想一生相伴的人，这样的爱情，要比那些你浓我浓时的甜言蜜语来得真诚可贵。","tid":50,"imgss":"http://116.62.217.183/storage/dog/1754152903505210148.png","com_num":0,"is_like":"0"},{"dates":"2018-06-14 16:15:10","user_id":10148,"imgs":[],"looks":5,"likes":0,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"we","content":null,"tid":46,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-14 16:14:35","user_id":10148,"imgs":[],"looks":0,"likes":0,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"we","content":"Www","tid":45,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":79,"likes":1,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":31,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":5,"likes":1,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":30,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":1,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":29,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":0,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":28,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":1,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":27,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:33","user_id":10157,"imgs":[],"looks":2,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":25,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:33","user_id":10157,"imgs":[],"looks":3,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":24,"imgss":"","com_num":0,"is_like":"0"}],"first_page_url":"http://116.62.217.183/api/friend/cardList?page=1","from":1,"last_page":4,"last_page_url":"http://116.62.217.183/api/friend/cardList?page=4","next_page_url":"http://116.62.217.183/api/friend/cardList?page=2","path":"http://116.62.217.183/api/friend/cardList","per_page":10,"prev_page_url":null,"to":10,"total":31}}
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
         * list : {"current_page":1,"data":[{"dates":"2018-06-15 11:57:32","user_id":10148,"imgs":[{"img":"http://116.62.217.183/storage/dog/6451152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/8969152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/3710152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/1754152903505210148.png"}],"looks":34,"likes":2,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"你好，未来","content":"当爱情融入柴米油盐酱醋茶这些生活琐事中，两个人依旧相看两不厌，依然认定彼此就是自己想一生相伴的人，这样的爱情，要比那些你浓我浓时的甜言蜜语来得真诚可贵。","tid":50,"imgss":"http://116.62.217.183/storage/dog/1754152903505210148.png","com_num":0,"is_like":"0"},{"dates":"2018-06-14 16:15:10","user_id":10148,"imgs":[],"looks":5,"likes":0,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"we","content":null,"tid":46,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-14 16:14:35","user_id":10148,"imgs":[],"looks":0,"likes":0,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"we","content":"Www","tid":45,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":79,"likes":1,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":31,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":5,"likes":1,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":30,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":1,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":29,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":0,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":28,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":1,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":27,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:33","user_id":10157,"imgs":[],"looks":2,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":25,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:33","user_id":10157,"imgs":[],"looks":3,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":24,"imgss":"","com_num":0,"is_like":"0"}],"first_page_url":"http://116.62.217.183/api/friend/cardList?page=1","from":1,"last_page":4,"last_page_url":"http://116.62.217.183/api/friend/cardList?page=4","next_page_url":"http://116.62.217.183/api/friend/cardList?page=2","path":"http://116.62.217.183/api/friend/cardList","per_page":10,"prev_page_url":null,"to":10,"total":31}
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
             * data : [{"dates":"2018-06-15 11:57:32","user_id":10148,"imgs":[{"img":"http://116.62.217.183/storage/dog/6451152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/8969152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/3710152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/1754152903505210148.png"}],"looks":34,"likes":2,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"你好，未来","content":"当爱情融入柴米油盐酱醋茶这些生活琐事中，两个人依旧相看两不厌，依然认定彼此就是自己想一生相伴的人，这样的爱情，要比那些你浓我浓时的甜言蜜语来得真诚可贵。","tid":50,"imgss":"http://116.62.217.183/storage/dog/1754152903505210148.png","com_num":0,"is_like":"0"},{"dates":"2018-06-14 16:15:10","user_id":10148,"imgs":[],"looks":5,"likes":0,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"we","content":null,"tid":46,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-14 16:14:35","user_id":10148,"imgs":[],"looks":0,"likes":0,"nick_name":"魔","avatar":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","title":"we","content":"Www","tid":45,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":79,"likes":1,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":31,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":5,"likes":1,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":30,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":1,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":29,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":0,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":28,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:34","user_id":10157,"imgs":[],"looks":1,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":27,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:33","user_id":10157,"imgs":[],"looks":2,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":25,"imgss":"","com_num":0,"is_like":"0"},{"dates":"2018-06-13 10:03:33","user_id":10157,"imgs":[],"looks":3,"likes":0,"nick_name":null,"avatar":null,"title":"321321s234234 121aa1","content":"12321321432safdasdsad221212321321asdfdsafdsfdsaa123123a","tid":24,"imgss":"","com_num":0,"is_like":"0"}]
             * first_page_url : http://116.62.217.183/api/friend/cardList?page=1
             * from : 1
             * last_page : 4
             * last_page_url : http://116.62.217.183/api/friend/cardList?page=4
             * next_page_url : http://116.62.217.183/api/friend/cardList?page=2
             * path : http://116.62.217.183/api/friend/cardList
             * per_page : 10
             * prev_page_url : null
             * to : 10
             * total : 31
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
                 * dates : 2018-06-15 11:57:32
                 * user_id : 10148
                 * imgs : [{"img":"http://116.62.217.183/storage/dog/6451152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/8969152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/3710152903505210148.png"},{"img":"http://116.62.217.183/storage/dog/1754152903505210148.png"}]
                 * looks : 34
                 * likes : 2
                 * nick_name : 魔
                 * avatar : http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
                 * title : 你好，未来
                 * content : 当爱情融入柴米油盐酱醋茶这些生活琐事中，两个人依旧相看两不厌，依然认定彼此就是自己想一生相伴的人，这样的爱情，要比那些你浓我浓时的甜言蜜语来得真诚可贵。
                 * tid : 50
                 * imgss : http://116.62.217.183/storage/dog/1754152903505210148.png
                 * com_num : 0
                 * is_like : 0
                 */

                private String dates;
                private int user_id;
                private int looks;
                private String likes;
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

                public String getLikes() {
                    return likes;
                }

                public void setLikes(String likes) {
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
                     * img : http://116.62.217.183/storage/dog/6451152903505210148.png
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
