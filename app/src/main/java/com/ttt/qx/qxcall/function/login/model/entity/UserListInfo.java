package com.ttt.qx.qxcall.function.login.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 首页用户列表
 * Created by wyd on 2017/10/18.
 */
public class UserListInfo implements Serializable{

    /**
     * message : success
     * status_code : 200
     * data : {"list":[{"id":41,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509120102_41.jpg","level":0,"member_account":0,"member_sex":"0","member_age":"","member_price":0,"fans_num":0,"zan_num":0,"member_signature":"哈喽","member_img_1":"http://116.62.217.183/storage/user/20171027/41_img_1.jpg","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}],"wy_acid":"41","wy_token":"88f5030d0468a1832e5c75be7592d41d","visitor_num":0,"listen_state":1}],"page":{"current_page":3,"last_page":4,"prev_page_url":"http://116.62.217.183/api/home?page=4","next_page_url":"http://116.62.217.183/api/home?page=4","total":33}}
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

    public static class DataBean implements Serializable{
        /**
         * list : [{"id":41,"nick_name":"a","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509120102_41.jpg","level":0,"member_account":0,"member_sex":"0","member_age":"","member_price":0,"fans_num":0,"zan_num":0,"member_signature":"哈喽","member_img_1":"http://116.62.217.183/storage/user/20171027/41_img_1.jpg","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}],"wy_acid":"41","wy_token":"88f5030d0468a1832e5c75be7592d41d","visitor_num":0,"listen_state":1}]
         * page : {"current_page":3,"last_page":4,"prev_page_url":"http://116.62.217.183/api/home?page=4","next_page_url":"http://116.62.217.183/api/home?page=4","total":33}
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * current_page : 3
             * last_page : 4
             * prev_page_url : http://116.62.217.183/api/home?page=4
             * next_page_url : http://116.62.217.183/api/home?page=4
             * total : 33
             */

            private int current_page;
            private int last_page;
            private String prev_page_url;
            private String next_page_url;
            private int total;

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
            }

            public String getPrev_page_url() {
                return prev_page_url;
            }

            public void setPrev_page_url(String prev_page_url) {
                this.prev_page_url = prev_page_url;
            }

            public String getNext_page_url() {
                return next_page_url;
            }

            public void setNext_page_url(String next_page_url) {
                this.next_page_url = next_page_url;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class ListBean implements Serializable {
            /**
             * id : 41
             * nick_name : a
             * member_avatar : http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509120102_41.jpg
             * level : 0
             * member_account : 0
             * member_sex : 0
             * member_age :
             * member_price : 0
             * fans_num : 0
             * zan_num : 0
             * member_signature : 哈喽
             * member_img_1 : http://116.62.217.183/storage/user/20171027/41_img_1.jpg
             * member_img_2 :
             * member_img_3 :
             * member_img_4 :
             * birthday :
             * member_tag : [{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}]
             * wy_acid : 41
             * wy_token : 88f5030d0468a1832e5c75be7592d41d
             * visitor_num : 0
             * listen_state : 1
             */

            private int id;
            private String nick_name;
            private String member_avatar;
            private int level;
            private int member_account;
            private String member_sex;
            private String member_age;
            private int member_price;
            private int is_fans;
            private int fans_num;
            private int zan_num;
            private String member_signature;
            private String member_img_1;
            private String member_img_2;
            private String member_img_3;
            private String member_img_4;
            private String birthday;
            private String wy_acid;
            private String wy_token;
            private int visitor_num;
            private int listen_state;
            private List<MemberTagBean> member_tag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getMember_account() {
                return member_account;
            }

            public void setMember_account(int member_account) {
                this.member_account = member_account;
            }

            public String getMember_sex() {
                if (member_sex == null || member_sex.isEmpty()) {
                    return "0";
                }
                return member_sex;
            }

            public void setMember_sex(String member_sex) {
                this.member_sex = member_sex;
            }

            public String getMember_age() {
                if (member_age == null || member_age.isEmpty()) {
                    return "未知";
                }
                return member_age + "岁";
            }

            public void setMember_age(String member_age) {
                this.member_age = member_age;
            }

            public int getMember_price() {
                return member_price;
            }

            public void setMember_price(int member_price) {
                this.member_price = member_price;
            }

            public int getFans_num() {
                return fans_num;
            }

            public void setFans_num(int fans_num) {
                this.fans_num = fans_num;
            }

            public int getZan_num() {
                return zan_num;
            }

            public void setZan_num(int zan_num) {
                this.zan_num = zan_num;
            }

            public int getIs_fans() {
                return is_fans;
            }

            public void setIs_fans(int is_fans) {
                this.is_fans = is_fans;
            }

            public String getMember_signature() {
                return member_signature;
            }

            public void setMember_signature(String member_signature) {
                this.member_signature = member_signature;
            }

            public String getMember_img_1() {
                return member_img_1;
            }

            public void setMember_img_1(String member_img_1) {
                this.member_img_1 = member_img_1;
            }

            public String getMember_img_2() {
                return member_img_2;
            }

            public void setMember_img_2(String member_img_2) {
                this.member_img_2 = member_img_2;
            }

            public String getMember_img_3() {
                return member_img_3;
            }

            public void setMember_img_3(String member_img_3) {
                this.member_img_3 = member_img_3;
            }

            public String getMember_img_4() {
                return member_img_4;
            }

            public void setMember_img_4(String member_img_4) {
                this.member_img_4 = member_img_4;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getWy_acid() {
                return wy_acid;
            }

            public void setWy_acid(String wy_acid) {
                this.wy_acid = wy_acid;
            }

            public String getWy_token() {
                return wy_token;
            }

            public void setWy_token(String wy_token) {
                this.wy_token = wy_token;
            }

            public int getVisitor_num() {
                return visitor_num;
            }

            public void setVisitor_num(int visitor_num) {
                this.visitor_num = visitor_num;
            }

            public int getListen_state() {
                return listen_state;
            }

            public void setListen_state(int listen_state) {
                this.listen_state = listen_state;
            }

            public List<MemberTagBean> getMember_tag() {
                return member_tag;
            }

            public void setMember_tag(List<MemberTagBean> member_tag) {
                this.member_tag = member_tag;
            }

            public static class MemberTagBean implements Serializable{
                /**
                 * id : 1
                 * text : 情感聊天
                 * color : #f59c9e
                 */

                private int id;
                private String text;
                private String color;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }
            }
        }
    }
}
