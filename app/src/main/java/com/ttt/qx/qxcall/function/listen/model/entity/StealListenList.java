package com.ttt.qx.qxcall.function.listen.model.entity;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/24.
 */

public class StealListenList {

    /**
     * message : success
     * status_code : 200
     * data : {"current_page":1,"first_page_url":"http://116.62.217.183/api/listen/list?page=1","from":1,"last_page":5,"last_page_url":"http://116.62.217.183/api/listen/list?page=5","next_page_url":"http://116.62.217.183/api/listen/list?page=2","path":"http://116.62.217.183/api/listen/list","per_page":10,"prev_page_url":null,"to":10,"total":41,"list":[{"id":42,"created_at":"2017-11-10 22:47:54","channelid":"193321625036929","duration":"8","acid_call":"24","acid_reply":"25","call_member":{"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","nick_name":"123ceshi","sex":"1"},"reply_member":{"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508925603_25.jpg","nick_name":"测110","sex":"0"},"zan_num":0,"price":6,"tag_list":[{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"}],"file":"http://116.62.217.183/storage/sound/2017/11/10/193321625036929_mix.aac"}]}
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
         * first_page_url : http://116.62.217.183/api/listen/list?page=1
         * from : 1
         * last_page : 5
         * last_page_url : http://116.62.217.183/api/listen/list?page=5
         * next_page_url : http://116.62.217.183/api/listen/list?page=2
         * path : http://116.62.217.183/api/listen/list
         * per_page : 10
         * prev_page_url : null
         * to : 10
         * total : 41
         * list : [{"id":42,"created_at":"2017-11-10 22:47:54","channelid":"193321625036929","duration":"8","acid_call":"24","acid_reply":"25","call_member":{"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","nick_name":"123ceshi","sex":"1"},"reply_member":{"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508925603_25.jpg","nick_name":"测110","sex":"0"},"zan_num":0,"price":6,"tag_list":[{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"}],"file":"http://116.62.217.183/storage/sound/2017/11/10/193321625036929_mix.aac"}]
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
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 42
             * created_at : 2017-11-10 22:47:54
             * channelid : 193321625036929
             * duration : 8
             * acid_call : 24
             * acid_reply : 25
             * call_member : {"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg","nick_name":"123ceshi","sex":"1"}
             * reply_member : {"member_avatar":"http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508925603_25.jpg","nick_name":"测110","sex":"0"}
             * zan_num : 0
             * price : 6
             * tag_list : [{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"}]
             * file : http://116.62.217.183/storage/sound/2017/11/10/193321625036929_mix.aac
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
            private List<TagListBean> tag_list;

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

            public List<TagListBean> getTag_list() {
                return tag_list;
            }

            public void setTag_list(List<TagListBean> tag_list) {
                this.tag_list = tag_list;
            }

            public static class CallMemberBean {
                /**
                 * member_avatar : http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508904597_24.jpg
                 * nick_name : 123ceshi
                 * sex : 1
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
                 * member_avatar : http://116.62.217.183/storage/avatar/2017/10/25/avatar_1508925603_25.jpg
                 * nick_name : 测110
                 * sex : 0
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

            public static class TagListBean {
                /**
                 * id : 3
                 * text : 知性御姐
                 * color : #b483a3
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
