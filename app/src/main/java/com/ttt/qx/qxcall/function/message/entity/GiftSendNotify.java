package com.ttt.qx.qxcall.function.message.entity;

/**
 * Created by 王亚东 on 2017/11/13.
 */

public class GiftSendNotify {

    /**
     * member_id : 18
     * member_info : {"member_id":18,"nick_name":"等不等","sex":"男","avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508944368_18.png"}
     * gift_info : {"id":13,"gift_name":"么么哒","gift_img":"http://116.62.217.183/storage/gifts/November2017/gift_03@3x.png","gift_price":96,"gift_desc":0,"git_num":"1"}
     * time : 2017-11-12 20:37
     * msg : 等不等赠送给你了一个么么哒
     * msg_type : 2
     */

    private int member_id;
    private MemberInfoBean member_info;
    private GiftInfoBean gift_info;
    private String time;
    private String msg;
    private int msg_type;

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

    public GiftInfoBean getGift_info() {
        return gift_info;
    }

    public void setGift_info(GiftInfoBean gift_info) {
        this.gift_info = gift_info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public static class MemberInfoBean {
        /**
         * member_id : 18
         * nick_name : 等不等
         * sex : 男
         * avatar : http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508944368_18.png
         */

        private int member_id;
        private String nick_name;
        private String sex;
        private String avatar;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class GiftInfoBean {
        /**
         * id : 13
         * gift_name : 么么哒
         * gift_img : http://116.62.217.183/storage/gifts/November2017/gift_03@3x.png
         * gift_price : 96
         * gift_desc : 0
         * git_num : 1
         */

        private int id;
        private String gift_name;
        private String gift_img;
        private int gift_price;
        private int gift_desc;
        private String git_num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getGift_img() {
            return gift_img;
        }

        public void setGift_img(String gift_img) {
            this.gift_img = gift_img;
        }

        public int getGift_price() {
            return gift_price;
        }

        public void setGift_price(int gift_price) {
            this.gift_price = gift_price;
        }

        public int getGift_desc() {
            return gift_desc;
        }

        public void setGift_desc(int gift_desc) {
            this.gift_desc = gift_desc;
        }

        public String getGit_num() {
            return git_num;
        }

        public void setGit_num(String git_num) {
            this.git_num = git_num;
        }
    }
}
