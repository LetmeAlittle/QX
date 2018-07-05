package com.ttt.qx.qxcall.function.find.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/29.
 */

public class DynamicDetail {

    /**
     * message : success!
     * status_code : 200
     * data : {"id":20,"create_at":"4天前","member_id":17,"nick_name":"影舞叶","member_avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg","member_area":"","content":"如果老农斤斤计较南窖路口太可怜了了","img_list":["http://116.62.217.183/storage/image/20171028/15091286728783.jpg","http://116.62.217.183/storage/image/20171028/15091286722473.jpg","http://116.62.217.183/storage/image/20171028/15091286721211.jpg","http://116.62.217.183/storage/image/20171028/15091286721411.jpg","http://116.62.217.183/storage/image/20171028/15091286735674.jpg","http://116.62.217.183/storage/image/20171028/15091286737614.jpg"],"zan_num":4,"click_num":37,"gift_num":0,"is_zan":0,"reply_list":[{"id":44,"say_id":20,"content":"哈额哈哈额好伐","create_at":"2017-10-29 21:41:46","member_id":67,"member_info":{"nick_name":"wyd","id":67,"avatar":"http://116.62.217.183/storage/avatar/2017/10/29/avatar_1509263487_67.png"},"to_member_id":17,"to_member_info":{"nick_name":"影舞叶","id":17,"avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg"}},{"id":43,"say_id":20,"content":"乐视","create_at":"2017-10-28 02:25:10","member_id":17,"member_info":{"nick_name":"影舞叶","id":17,"avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg"},"to_member_id":0,"to_member_info":{}}],"reply_num":2}
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
         * id : 20
         * create_at : 4天前
         * member_id : 17
         * nick_name : 影舞叶
         * member_avatar : http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg
         * member_area :
         * content : 如果老农斤斤计较南窖路口太可怜了了
         * img_list : ["http://116.62.217.183/storage/image/20171028/15091286728783.jpg","http://116.62.217.183/storage/image/20171028/15091286722473.jpg","http://116.62.217.183/storage/image/20171028/15091286721211.jpg","http://116.62.217.183/storage/image/20171028/15091286721411.jpg","http://116.62.217.183/storage/image/20171028/15091286735674.jpg","http://116.62.217.183/storage/image/20171028/15091286737614.jpg"]
         * zan_num : 4
         * click_num : 37
         * gift_num : 0
         * is_zan : 0
         * reply_list : [{"id":44,"say_id":20,"content":"哈额哈哈额好伐","create_at":"2017-10-29 21:41:46","member_id":67,"member_info":{"nick_name":"wyd","id":67,"avatar":"http://116.62.217.183/storage/avatar/2017/10/29/avatar_1509263487_67.png"},"to_member_id":17,"to_member_info":{"nick_name":"影舞叶","id":17,"avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg"}},{"id":43,"say_id":20,"content":"乐视","create_at":"2017-10-28 02:25:10","member_id":17,"member_info":{"nick_name":"影舞叶","id":17,"avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg"},"to_member_id":0,"to_member_info":{}}]
         * reply_num : 2
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
             * id : 44
             * say_id : 20
             * content : 哈额哈哈额好伐
             * create_at : 2017-10-29 21:41:46
             * member_id : 67
             * member_info : {"nick_name":"wyd","id":67,"avatar":"http://116.62.217.183/storage/avatar/2017/10/29/avatar_1509263487_67.png"}
             * to_member_id : 17
             * to_member_info : {"nick_name":"影舞叶","id":17,"avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg"}
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
                 * nick_name : wyd
                 * id : 67
                 * avatar : http://116.62.217.183/storage/avatar/2017/10/29/avatar_1509263487_67.png
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
                /**
                 * nick_name : 影舞叶
                 * id : 17
                 * avatar : http://116.62.217.183/storage/avatar/2017/10/26/avatar_1508978800_17.jpg
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
        }
    }
}
