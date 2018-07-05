package com.ttt.qx.qxcall.function.listen.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/25.
 */

public class StealDetailResponse {

    /**
     * message : success
     * status_code : 200
     * data : {"id":14,"created_at":"2017-10-28 14:56:15","channelid":"193144962533761","duration":"50","acid_call":"18","acid_reply":"20","call_member":{"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508944368_18.png","nick_name":"等不等","sex":null},"reply_member":{"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1509020764_20.png","nick_name":"晓暧","sex":null},"zan_num":0,"price":6,"tag_list":[],"file":"http://116.62.217.183/storage/sound/2017/10/28/193144962533761_mix.aac"}
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
         * id : 14
         * created_at : 2017-10-28 14:56:15
         * channelid : 193144962533761
         * duration : 50
         * acid_call : 18
         * acid_reply : 20
         * call_member : {"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508944368_18.png","nick_name":"等不等","sex":null}
         * reply_member : {"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1509020764_20.png","nick_name":"晓暧","sex":null}
         * zan_num : 0
         * price : 6
         * tag_list : []
         * file : http://116.62.217.183/storage/sound/2017/10/28/193144962533761_mix.aac
         */

        private int id;
        private String created_at;
        private String channelid;
        private String duration;
        private String acid_call;
        private String acid_reply;
        private CallMemberBean call_member;
        private ReplyMemberBean reply_member;
        private int zan_num;
        private int price;
        private String file;
        private List<?> tag_list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getChannelid() {
            return channelid;
        }

        public void setChannelid(String channelid) {
            this.channelid = channelid;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getAcid_call() {
            return acid_call;
        }

        public void setAcid_call(String acid_call) {
            this.acid_call = acid_call;
        }

        public String getAcid_reply() {
            return acid_reply;
        }

        public void setAcid_reply(String acid_reply) {
            this.acid_reply = acid_reply;
        }

        public CallMemberBean getCall_member() {
            return call_member;
        }

        public void setCall_member(CallMemberBean call_member) {
            this.call_member = call_member;
        }

        public ReplyMemberBean getReply_member() {
            return reply_member;
        }

        public void setReply_member(ReplyMemberBean reply_member) {
            this.reply_member = reply_member;
        }

        public int getZan_num() {
            return zan_num;
        }

        public void setZan_num(int zan_num) {
            this.zan_num = zan_num;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public List<?> getTag_list() {
            return tag_list;
        }

        public void setTag_list(List<?> tag_list) {
            this.tag_list = tag_list;
        }

        public static class CallMemberBean {
            /**
             * member_avatar : http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508944368_18.png
             * nick_name : 等不等
             * sex : null
             */

            private String member_avatar;
            private String nick_name;
            private String sex;

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
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
        }

        public static class ReplyMemberBean {
            /**
             * member_avatar : http://116.62.217.183/storage/avatar/2017/10/26/avatar_1509020764_20.png
             * nick_name : 晓暧
             * sex : null
             */

            private String member_avatar;
            private String nick_name;
            private String sex;

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
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
        }
    }
}
