package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GuardsListResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * icon : http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
         * username : 15713823323
         * dates : 45
         * uid : 10148
         */

        private String icon;
        private String username;
        private int dates;
        private int uid;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getDates() {
            return dates;
        }

        public void setDates(int dates) {
            this.dates = dates;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
