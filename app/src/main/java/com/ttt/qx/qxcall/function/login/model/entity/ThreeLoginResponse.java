package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * Created by 王亚东 on 2017/10/29.
 */

public class ThreeLoginResponse {

    /**
     * message : 登录成功！
     * status_code : 200
     * data : {"type":"wx","openid":"oajRhw0YCLkk-KpFUaYVxd5Q6kj4","need_info":0,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvd3hfbG9naW4iLCJpYXQiOjE1MTI4MDMxODMsIm5iZiI6MTUxMjgwMzE4MywianRpIjoiSU9tRjBhdEVPVWlhemUxWCIsInN1YiI6NjcsInBydiI6ImFmZDBmZDliZGQ5YWM3MmZmMzk4MzQxZjFkNjI4NDBjYmY0YzcxNjcifQ.zTptOgd1Xg3abGEV1ifaJvdZENN93nGKiO73H3D2Vnc","im_info":{"acid":"67","token":"0af1b90a21c5b13bf2ba992b7c13bd85"}}
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
         * type : wx
         * openid : oajRhw0YCLkk-KpFUaYVxd5Q6kj4
         * need_info : 0
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvd3hfbG9naW4iLCJpYXQiOjE1MTI4MDMxODMsIm5iZiI6MTUxMjgwMzE4MywianRpIjoiSU9tRjBhdEVPVWlhemUxWCIsInN1YiI6NjcsInBydiI6ImFmZDBmZDliZGQ5YWM3MmZmMzk4MzQxZjFkNjI4NDBjYmY0YzcxNjcifQ.zTptOgd1Xg3abGEV1ifaJvdZENN93nGKiO73H3D2Vnc
         * im_info : {"acid":"67","token":"0af1b90a21c5b13bf2ba992b7c13bd85"}
         */

        private String type;
        private String openid;
        private int need_info;
        private String token;
        private ImInfoBean im_info;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public int getNeed_info() {
            return need_info;
        }

        public void setNeed_info(int need_info) {
            this.need_info = need_info;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public ImInfoBean getIm_info() {
            return im_info;
        }

        public void setIm_info(ImInfoBean im_info) {
            this.im_info = im_info;
        }

        public static class ImInfoBean {
            /**
             * acid : 67
             * token : 0af1b90a21c5b13bf2ba992b7c13bd85
             */

            private String acid;
            private String token;

            public String getAcid() {
                return acid;
            }

            public void setAcid(String acid) {
                this.acid = acid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
