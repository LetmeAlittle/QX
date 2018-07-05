package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * 版本更新
 * Created by wyd on 2017/9/7.
 */
public class VersionUpdate {
    /**
     * status : 200
     * data : {"id":1,"version":"1.0","publishTime":"2017-09-07 15:51:00","content":"内测上线","linkAndriod":"http://www.vipcai.com/packages/andriod.1.0.apk","linkApple":"http://www.vipcai.com/packages/ios.1.0.ios","createOn":"2017-09-07 15:54:56"}
     * errorMessage : null
     * isSuccess : true
     */

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

    public static class DataBean {
        /**
         * id : 1
         * version : 1.0
         * publishTime : 2017-09-07 15:51:00
         * content : 内测上线
         * linkAndriod : http://www.vipcai.com/packages/andriod.1.0.apk
         * linkApple : http://www.vipcai.com/packages/ios.1.0.ios
         * createOn : 2017-09-07 15:54:56
         */

        private int id;
        private String version;
        private String publishTime;
        private String content;
        private String linkAndriod;
        private String linkApple;
        private String createOn;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLinkAndriod() {
            return linkAndriod;
        }

        public void setLinkAndriod(String linkAndriod) {
            this.linkAndriod = linkAndriod;
        }

        public String getLinkApple() {
            return linkApple;
        }

        public void setLinkApple(String linkApple) {
            this.linkApple = linkApple;
        }

        public String getCreateOn() {
            return createOn;
        }

        public void setCreateOn(String createOn) {
            this.createOn = createOn;
        }
    }
}
