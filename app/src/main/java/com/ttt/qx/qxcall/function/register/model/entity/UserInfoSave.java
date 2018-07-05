package com.ttt.qx.qxcall.function.register.model.entity;

/**
 * Created by 王亚东 on 2017/10/14.
 */
public class UserInfoSave {
    /**
     * message : 保存成功！
     * status_code : 200
     * data : {"user_info":{"nick_name":"zyl-hello2","sex":"男"},"im_info":{"token":"3991583479b3ef28c1b62d0d84afa82b","accid":"15","name":"zyl-hello2"}}
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
         * user_info : {"nick_name":"zyl-hello2","sex":"男"}
         * im_info : {"token":"3991583479b3ef28c1b62d0d84afa82b","accid":"15","name":"zyl-hello2"}
         */

        private UserInfoBean user_info;
        private ImInfoBean im_info;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public ImInfoBean getIm_info() {
            return im_info;
        }

        public void setIm_info(ImInfoBean im_info) {
            this.im_info = im_info;
        }

        public static class UserInfoBean {
            /**
             * nick_name : zyl-hello2
             * sex : 男
             */

            private String nick_name;
            private String sex;

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

        public static class ImInfoBean {
            /**
             * token : 3991583479b3ef28c1b62d0d84afa82b
             * accid : 15
             * name : zyl-hello2
             */

            private String token;
            private String accid;
            private String name;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getAccid() {
                return accid;
            }

            public void setAccid(String accid) {
                this.accid = accid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
