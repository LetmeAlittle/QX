package com.ttt.qx.qxcall.function.login.model.entity;

/**
 * Created by 王亚东 on 2017/12/16.
 */

public class UserShareInfo {

    /**
     * message : success
     * status_code : 200
     * data : {"url":"http://116.62.217.183/share/20","title":"确幸-遇见合适的你","description":"注册送钻石，快来下载吧！","img":"http://116.62.217.183/images/logo.png"}
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
         * url : http://116.62.217.183/share/20
         * title : 确幸-遇见合适的你
         * description : 注册送钻石，快来下载吧！
         * img : http://116.62.217.183/images/logo.png
         */

        private String url;
        private String title;
        private String description;
        private String img;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
