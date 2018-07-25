package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class ShouFeiResponse extends BaseResponse {

    /**
     * data : {"list":[],"flag":"0"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : []
         * flag : 0
         */

        private String flag;
        private List<Object> list;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public List<Object> getList() {
            return list;
        }

        public void setList(List<Object> list) {
            this.list = list;
        }
    }
}
