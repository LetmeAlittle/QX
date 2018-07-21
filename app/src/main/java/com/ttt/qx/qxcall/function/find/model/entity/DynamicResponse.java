package com.ttt.qx.qxcall.function.find.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王亚东 on 2017/10/27.
 */

public class DynamicResponse {

    /**
     * message : success!
     * status_code : 200
     * data : {"current_page":1,"first_page_url":"http://116.62.217.183/api/friend/list?page=1","from":1,"last_page":5,"last_page_url":"http://116.62.217.183/api/friend/list?page=5","next_page_url":"http://116.62.217.183/api/friend/list?page=2","path":"http://116.62.217.183/api/friend/list","per_page":10,"prev_page_url":null,"to":10,"total":47,"list":[{"id":47,"create_at":"4天前","member_id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","member_area":"北京市","content":"天气好冷","img_list":["http://116.62.217.183/storage/image/20171119/15110957627451.png"],"zan_num":2,"click_num":20,"gift_num":2,"is_zan":0,"reply_list":[{"id":110,"say_id":47,"content":"是啊","create_at":"2017-11-19 22:14:00","member_id":69,"member_info":{"nick_name":"零度","id":69,"avatar":"http://116.62.217.183/storage/avatar/2017/11/22/avatar_1511333422_69.png"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":46,"create_at":"1周前","member_id":73,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/07/avatar_1510068696_73.jpg","member_area":"北京市","content":"哈哈","img_list":["http://116.62.217.183/storage/image/20171112/15104842136436.jpg"],"zan_num":2,"click_num":9,"gift_num":2,"is_zan":0,"reply_list":[{"id":109,"say_id":46,"content":"是啊","create_at":"2017-11-19 20:50:11","member_id":24,"member_info":{"nick_name":"123ceshi","id":24,"avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":45,"create_at":"1周前","member_id":72,"nick_name":"孤芳不自赏","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg","member_area":"","content":"吓着我了","img_list":["http://116.62.217.183/storage/image/20171112/15104815994905.jpg"],"zan_num":2,"click_num":0,"gift_num":2,"is_zan":0,"reply_list":[],"reply_num":0},{"id":44,"create_at":"1周前","member_id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","member_area":"北京市","content":"测试多图","img_list":["http://116.62.217.183/storage/image/20171112/15104663355091.jpg","http://116.62.217.183/storage/image/20171112/15104663351860.jpg","http://116.62.217.183/storage/image/20171112/15104663368854.jpg"],"zan_num":2,"click_num":9,"gift_num":7,"is_zan":0,"reply_list":[{"id":108,"say_id":44,"content":"好棒","create_at":"2017-11-12 13:59:28","member_id":24,"member_info":{"nick_name":"123ceshi","id":24,"avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":43,"create_at":"1周前","member_id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","member_area":"北京市","content":"测试结果","img_list":["http://116.62.217.183/storage/image/20171112/15104663125785.jpg"],"zan_num":1,"click_num":2,"gift_num":0,"is_zan":0,"reply_list":[],"reply_num":0},{"id":42,"create_at":"1周前","member_id":66,"nick_name":"我是昵称","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/03/avatar_1509692096_66.jpg","member_area":"北京市","content":"CF跑酷","img_list":[],"zan_num":1,"click_num":7,"gift_num":24,"is_zan":0,"reply_list":[],"reply_num":0},{"id":41,"create_at":"2周前","member_id":73,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/07/avatar_1510068696_73.jpg","member_area":"北京市","content":"哈喽哈喽","img_list":[],"zan_num":1,"click_num":5,"gift_num":2,"is_zan":0,"reply_list":[],"reply_num":0},{"id":40,"create_at":"2周前","member_id":72,"nick_name":"孤芳不自赏","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg","member_area":"","content":"好玩不","img_list":["http://116.62.217.183/storage/image/20171109/15102239496357.jpg"],"zan_num":1,"click_num":3,"gift_num":0,"is_zan":0,"reply_list":[],"reply_num":0},{"id":39,"create_at":"2周前","member_id":72,"nick_name":"孤芳不自赏","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg","member_area":"","content":"掐会儿腰，可把我牛逼坏了","img_list":[],"zan_num":0,"click_num":7,"gift_num":6,"is_zan":0,"reply_list":[{"id":106,"say_id":39,"content":"六六六","create_at":"2017-11-09 02:19:45","member_id":72,"member_info":{"nick_name":"孤芳不自赏","id":72,"avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":38,"create_at":"2周前","member_id":73,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/07/avatar_1510068696_73.jpg","member_area":"北京市","content":"哈喽","img_list":["http://116.62.217.183/storage/image/20171109/15101604203695.jpg"],"zan_num":2,"click_num":2,"gift_num":1,"is_zan":0,"reply_list":[],"reply_num":0}]}
     */

    private String message;
    private int status_code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * current_page : 1
         * first_page_url : http://116.62.217.183/api/friend/list?page=1
         * from : 1
         * last_page : 5
         * last_page_url : http://116.62.217.183/api/friend/list?page=5
         * next_page_url : http://116.62.217.183/api/friend/list?page=2
         * path : http://116.62.217.183/api/friend/list
         * per_page : 10
         * prev_page_url : null
         * to : 10
         * total : 47
         * list : [{"id":47,"create_at":"4天前","member_id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","member_area":"北京市","content":"天气好冷","img_list":["http://116.62.217.183/storage/image/20171119/15110957627451.png"],"zan_num":2,"click_num":20,"gift_num":2,"is_zan":0,"reply_list":[{"id":110,"say_id":47,"content":"是啊","create_at":"2017-11-19 22:14:00","member_id":69,"member_info":{"nick_name":"零度","id":69,"avatar":"http://116.62.217.183/storage/avatar/2017/11/22/avatar_1511333422_69.png"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":46,"create_at":"1周前","member_id":73,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/07/avatar_1510068696_73.jpg","member_area":"北京市","content":"哈哈","img_list":["http://116.62.217.183/storage/image/20171112/15104842136436.jpg"],"zan_num":2,"click_num":9,"gift_num":2,"is_zan":0,"reply_list":[{"id":109,"say_id":46,"content":"是啊","create_at":"2017-11-19 20:50:11","member_id":24,"member_info":{"nick_name":"123ceshi","id":24,"avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":45,"create_at":"1周前","member_id":72,"nick_name":"孤芳不自赏","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg","member_area":"","content":"吓着我了","img_list":["http://116.62.217.183/storage/image/20171112/15104815994905.jpg"],"zan_num":2,"click_num":0,"gift_num":2,"is_zan":0,"reply_list":[],"reply_num":0},{"id":44,"create_at":"1周前","member_id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","member_area":"北京市","content":"测试多图","img_list":["http://116.62.217.183/storage/image/20171112/15104663355091.jpg","http://116.62.217.183/storage/image/20171112/15104663351860.jpg","http://116.62.217.183/storage/image/20171112/15104663368854.jpg"],"zan_num":2,"click_num":9,"gift_num":7,"is_zan":0,"reply_list":[{"id":108,"say_id":44,"content":"好棒","create_at":"2017-11-12 13:59:28","member_id":24,"member_info":{"nick_name":"123ceshi","id":24,"avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":43,"create_at":"1周前","member_id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","member_area":"北京市","content":"测试结果","img_list":["http://116.62.217.183/storage/image/20171112/15104663125785.jpg"],"zan_num":1,"click_num":2,"gift_num":0,"is_zan":0,"reply_list":[],"reply_num":0},{"id":42,"create_at":"1周前","member_id":66,"nick_name":"我是昵称","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/03/avatar_1509692096_66.jpg","member_area":"北京市","content":"CF跑酷","img_list":[],"zan_num":1,"click_num":7,"gift_num":24,"is_zan":0,"reply_list":[],"reply_num":0},{"id":41,"create_at":"2周前","member_id":73,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/07/avatar_1510068696_73.jpg","member_area":"北京市","content":"哈喽哈喽","img_list":[],"zan_num":1,"click_num":5,"gift_num":2,"is_zan":0,"reply_list":[],"reply_num":0},{"id":40,"create_at":"2周前","member_id":72,"nick_name":"孤芳不自赏","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg","member_area":"","content":"好玩不","img_list":["http://116.62.217.183/storage/image/20171109/15102239496357.jpg"],"zan_num":1,"click_num":3,"gift_num":0,"is_zan":0,"reply_list":[],"reply_num":0},{"id":39,"create_at":"2周前","member_id":72,"nick_name":"孤芳不自赏","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg","member_area":"","content":"掐会儿腰，可把我牛逼坏了","img_list":[],"zan_num":0,"click_num":7,"gift_num":6,"is_zan":0,"reply_list":[{"id":106,"say_id":39,"content":"六六六","create_at":"2017-11-09 02:19:45","member_id":72,"member_info":{"nick_name":"孤芳不自赏","id":72,"avatar":"http://116.62.217.183/storage/avatar/2017/11/12/avatar_1510486452_72.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":1},{"id":38,"create_at":"2周前","member_id":73,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/11/07/avatar_1510068696_73.jpg","member_area":"北京市","content":"哈喽","img_list":["http://116.62.217.183/storage/image/20171109/15101604203695.jpg"],"zan_num":2,"click_num":2,"gift_num":1,"is_zan":0,"reply_list":[],"reply_num":0}]
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
        private List<ListBean> list;
        private String background_pic;

        public String getBackground_pic() {
            return background_pic;
        }

        public void setBackground_pic(String background_pic) {
            this.background_pic = background_pic;
        }

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

        public List<ListBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 47
             * create_at : 4天前
             * member_id : 24
             * nick_name : 123ceshi
             * member_avatar : http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg
             * member_area : 北京市
             * content : 天气好冷
             * img_list : ["http://116.62.217.183/storage/image/20171119/15110957627451.png"]
             * zan_num : 2
             * click_num : 20
             * gift_num : 2
             * is_zan : 0
             * reply_list : [{"id":110,"say_id":47,"content":"是啊","create_at":"2017-11-19 22:14:00","member_id":69,"member_info":{"nick_name":"零度","id":69,"avatar":"http://116.62.217.183/storage/avatar/2017/11/22/avatar_1511333422_69.png"},"to_member_id":0,"to_member_info":{}}]
             * reply_num : 1
             */

            private int id;
            private String create_at;
            private int member_id;
            private String nick_name;
            private String member_avatar;
            private String member_area;
            private String content;
            private int zan_num;
            private int click_num;
            private int gift_num;
            private int is_zan;
            private int reply_num;
            private List<String> img_list;
            private List<ReplyListBean> reply_list;

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

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
            }

            public String getMember_area() {
                return member_area;
            }

            public void setMember_area(String member_area) {
                this.member_area = member_area;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getZan_num() {
                return zan_num;
            }

            public void setZan_num(int zan_num) {
                this.zan_num = zan_num;
            }

            public int getClick_num() {
                return click_num;
            }

            public void setClick_num(int click_num) {
                this.click_num = click_num;
            }

            public int getGift_num() {
                return gift_num;
            }

            public void setGift_num(int gift_num) {
                this.gift_num = gift_num;
            }

            public int getIs_zan() {
                return is_zan;
            }

            public void setIs_zan(int is_zan) {
                this.is_zan = is_zan;
            }

            public int getReply_num() {
                return reply_num;
            }

            public void setReply_num(int reply_num) {
                this.reply_num = reply_num;
            }

            public List<String> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<String> img_list) {
                this.img_list = img_list;
            }

            public List<ReplyListBean> getReply_list() {
                return reply_list;
            }

            public void setReply_list(List<ReplyListBean> reply_list) {
                this.reply_list = reply_list;
            }

            public static class ReplyListBean {
                /**
                 * id : 110
                 * say_id : 47
                 * content : 是啊
                 * create_at : 2017-11-19 22:14:00
                 * member_id : 69
                 * member_info : {"nick_name":"零度","id":69,"avatar":"http://116.62.217.183/storage/avatar/2017/11/22/avatar_1511333422_69.png"}
                 * to_member_id : 0
                 * to_member_info : {}
                 */

                private int id;
                private int say_id;
                private String content;
                private String create_at;
                private int member_id;
                private MemberInfoBean member_info;
                private int to_member_id;
                private ToMemberInfoBean to_member_info;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSay_id() {
                    return say_id;
                }

                public void setSay_id(int say_id) {
                    this.say_id = say_id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
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

                public MemberInfoBean getMember_info() {
                    return member_info;
                }

                public void setMember_info(MemberInfoBean member_info) {
                    this.member_info = member_info;
                }

                public int getTo_member_id() {
                    return to_member_id;
                }

                public void setTo_member_id(int to_member_id) {
                    this.to_member_id = to_member_id;
                }

                public ToMemberInfoBean getTo_member_info() {
                    return to_member_info;
                }

                public void setTo_member_info(ToMemberInfoBean to_member_info) {
                    this.to_member_info = to_member_info;
                }

                public static class MemberInfoBean {
                    /**
                     * nick_name : 零度
                     * id : 69
                     * avatar : http://116.62.217.183/storage/avatar/2017/11/22/avatar_1511333422_69.png
                     */

                    private String nick_name;
                    private int id;
                    private String avatar;

                    public String getNick_name() {
                        return nick_name;
                    }

                    public void setNick_name(String nick_name) {
                        this.nick_name = nick_name;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getAvatar() {
                        return avatar;
                    }

                    public void setAvatar(String avatar) {
                        this.avatar = avatar;
                    }
                }

                public static class ToMemberInfoBean {
                    private String nick_name;
                    private int id;
                    private String avatar;

                    public String getNick_name() {
                        return nick_name;
                    }

                    public void setNick_name(String nick_name) {
                        this.nick_name = nick_name;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getAvatar() {
                        return avatar;
                    }

                    public void setAvatar(String avatar) {
                        this.avatar = avatar;
                    }
                }
            }
        }
    }
}
