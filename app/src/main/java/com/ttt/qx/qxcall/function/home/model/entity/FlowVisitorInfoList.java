package com.ttt.qx.qxcall.function.home.model.entity;

import java.util.List;

/**
 * 用户关注 粉丝以及访客数据
 * Created by 王亚东 on 2017/11/3.
 */

public class FlowVisitorInfoList {

    /**
     * message : success
     * status_code : 200
     * data : {"current_page":1,"data":[{"id":34,"member_id":59,"member_info":{"id":59,"nick_name":"没看","sex":"1","avatar":"http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509129205_59.jpg"},"create_at":"2017-10-30 23:09"},{"id":55,"member_id":20,"member_info":{"id":20,"nick_name":"晓暧","sex":"男","avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1509020764_20.png"},"create_at":"2017-11-03 23:39"}],"first_page_url":"http://116.62.217.183/api/user/flow_list?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/user/flow_list?page=1","next_page_url":null,"path":"http://116.62.217.183/api/user/flow_list","per_page":10,"prev_page_url":null,"to":2,"total":2}
     */

    private String message;
    private int status_code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":34,"member_id":59,"member_info":{"id":59,"nick_name":"没看","sex":"1","avatar":"http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509129205_59.jpg"},"create_at":"2017-10-30 23:09"},{"id":55,"member_id":20,"member_info":{"id":20,"nick_name":"晓暧","sex":"男","avatar":"http://116.62.217.183/storage/avatar/2017/10/26/avatar_1509020764_20.png"},"create_at":"2017-11-03 23:39"}]
         * first_page_url : http://116.62.217.183/api/user/flow_list?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://116.62.217.183/api/user/flow_list?page=1
         * next_page_url : null
         * path : http://116.62.217.183/api/user/flow_list
         * per_page : 10
         * prev_page_url : null
         * to : 2
         * total : 2
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

        public Object getNext_page_url() {
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
             * id : 34
             * member_id : 59
             * member_info : {"id":59,"nick_name":"没看","sex":"1","avatar":"http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509129205_59.jpg"}
             * create_at : 2017-10-30 23:09
             */

            private int id;
            private int member_id;
            private MemberInfoBean member_info;
            private String create_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public static class MemberInfoBean {
                /**
                 * id : 59
                 * nick_name : 没看
                 * sex : 1
                 * avatar : http://116.62.217.183/storage/avatar/2017/10/28/avatar_1509129205_59.jpg
                 */

                private int id;
                private int level;
                private String nick_name;
                private String member_signature;
                private String sex;
                private String avatar;
                private String age;

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public String getMember_signature() {
                    return member_signature;
                }

                public void setMember_signature(String member_signature) {
                    this.member_signature = member_signature;
                }

                public String getAge() {
                    return age;
                }

                public void setAge(String age) {
                    this.age = age;
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
        }
    }
}
