package com.ttt.qx.qxcall.function.home.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王亚东 on 2017/10/15.
 */

public class UserDetailInfo {


    /**
     * message : success
     * status_code : 200
     * data : {"id":24,"nick_name":"123ceshi","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","level":0,"member_account":1232,"member_sex":"1","member_age":"","member_price":35,"fans_num":1,"flow_num":1,"is_fans":0,"zan_num":11,"member_signature":"测试","member_img_1":"http://116.62.217.183/storage/user/20171028/24_img_1.jpg","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"1998-01-01","member_tag":[{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}],"wy_acid":"24","wy_token":"140dd45c45e697634771ca1cf6235827","visitor_num":0,"listen_state":1}
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

    public static class DataBean{
        /**
         * id : 24
         * nick_name : 123ceshi
         * member_avatar : http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg
         * level : 0
         * member_account : 1232
         * member_sex : 1
         * member_age :
         * member_price : 35
         * fans_num : 1
         * flow_num : 1
         * is_fans : 0
         * zan_num : 11
         * member_signature : 测试
         * member_img_1 : http://116.62.217.183/storage/user/20171028/24_img_1.jpg
         * member_img_2 :
         * member_img_3 :
         * member_img_4 :
         * birthday : 1998-01-01
         * member_tag : [{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}]
         * wy_acid : 24
         * wy_token : 140dd45c45e697634771ca1cf6235827
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
        private int fans_num;
        private int flow_num;
        private int is_fans;
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
        private int is_online;
        private int member_cate_id;
        private int is_true;
        private int member_info_is_post;
        private String member_city;
        private String member_province;
        private String member_cate_name;
        private String sound_file;
        private List<MemberTagBean> member_tag;
        private ArrayList<XiangCheBean> xiangce;
        private String dog;     //狗粮数量
        private String guard;   //守护数量

        public ArrayList<XiangCheBean> getXiangce() {
            return xiangce;
        }

        public void setXiangce(ArrayList<XiangCheBean> xiangce) {
            this.xiangce = xiangce;
        }

        public String getDog() {
            return dog;
        }

        public void setDog(String dog) {
            this.dog = dog;
        }

        public String getGuard() {
            return guard;
        }

        public void setGuard(String guard) {
            this.guard = guard;
        }

        public String getMember_city() {
            if(member_city==null||member_city.isEmpty()||member_city.equals("null")){
                return "";
            }
            return member_city;
        }

        public void setMember_city(String member_city) {
            this.member_city = member_city;
        }

        public String getMember_province() {
            if(member_province==null||member_province.isEmpty()||member_province.equals("null")){
                return "暂无地址信息";
            }
            return member_province;
        }

        public void setMember_province(String member_province) {
            this.member_province = member_province;
        }

        public String getMember_cate_name() {
            return member_cate_name;
        }

        public void setMember_cate_name(String member_cate_name) {
            this.member_cate_name = member_cate_name;
        }

        public int getMember_info_is_post() {
            return member_info_is_post;
        }

        public void setMember_info_is_post(int member_info_is_post) {
            this.member_info_is_post = member_info_is_post;
        }

        public int getIs_online() {
            return is_online;
        }

        public void setIs_online(int is_online) {
            this.is_online = is_online;
        }

        public int getMember_cate_id() {
            return member_cate_id;
        }

        public void setMember_cate_id(int member_cate_id) {
            this.member_cate_id = member_cate_id;
        }

        public String getSound_file() {
            return sound_file;
        }

        public int getIs_true() {
            return member_info_is_post;
        }

        public void setIs_true(int is_true) {
            this.is_true = is_true;
        }

        public void setSound_file(String sound_file) {
            this.sound_file = sound_file;
        }

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
            return member_sex;
        }

        public void setMember_sex(String member_sex) {
            this.member_sex = member_sex;
        }

        public String getMember_age() {
            if(member_age==null||member_age.isEmpty()||member_age.equals("null")){
                return "未知";
            }
            return member_age;
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

        public int getFlow_num() {
            return flow_num;
        }

        public void setFlow_num(int flow_num) {
            this.flow_num = flow_num;
        }

        public int getIs_fans() {
            return is_fans;
        }

        public void setIs_fans(int is_fans) {
            this.is_fans = is_fans;
        }

        public int getZan_num() {
            return zan_num;
        }

        public void setZan_num(int zan_num) {
            this.zan_num = zan_num;
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

        public static class MemberTagBean {
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

        public static class XiangCheBean implements Serializable {
            private String icon;
            private String icon_id;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getIcon_id() {
                return icon_id;
            }

            public void setIcon_id(String icon_id) {
                this.icon_id = icon_id;
            }
        }

    }
}
