package com.ttt.qx.qxcall.function.login.model.entity;

import java.io.Serializable;

/**
 * 用户实体类
 * Created by fina on 2017/1/13.
 */

public class User implements Serializable {
    private String status;
    private DataBean data;
    private Object errorMessage;
    private boolean isSuccess;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 3543
         * loginIP : 123.160.133.27
         * loginName : 13298368219
         * realName : null
         * nickName : 萌萌萌萌   昵称正则 ^[A-Za-z0-9\u4E00-\u9FA5]+$
         * headImg : http://139.219.224.246:5587/fileData/Picture/2017/04/fa5d19ab-ce1d-41ca-b28b-5d3d5aebe5fd.jpg
         * mobile : 13298368219
         * email : null
         * loginTime : 2017-07-20 14:51:04
         * passportUpdateTime : 0001-01-01 00:00:00
         * passportType : 0
         * loginSession : 20a92927d1e5ae1b914afaa9e998f827
         * createOn : 2017-04-20 14:51:04
         */

        private int id;
        private String loginIP;
        private String loginName;
        private Object realName;
        private String nickName;
        private String headImg;
        private String mobile;
        private Object email;
        private String loginTime;
        private String passportUpdateTime;
        private int passportType;
        private String loginSession;
        private String createOn;

        public String getCreateOn() {
            return createOn;
        }

        public void setCreateOn(String createOn) {
            this.createOn = createOn;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginIP() {
            return loginIP;
        }

        public void setLoginIP(String loginIP) {
            this.loginIP = loginIP;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getPassportUpdateTime() {
            return passportUpdateTime;
        }

        public void setPassportUpdateTime(String passportUpdateTime) {
            this.passportUpdateTime = passportUpdateTime;
        }

        public int getPassportType() {
            return passportType;
        }

        public void setPassportType(int passportType) {
            this.passportType = passportType;
        }

        public String getLoginSession() {
            return loginSession;
        }

        public void setLoginSession(String loginSession) {
            this.loginSession = loginSession;
        }
    }
}
