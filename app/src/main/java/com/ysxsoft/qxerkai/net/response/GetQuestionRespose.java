package com.ysxsoft.qxerkai.net.response;

import java.util.ArrayList;
import java.util.List;

public class GetQuestionRespose extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tid : 1
         * name : 1.你在爱情里最在意的是什么？
         * list : [{"id":1,"title":"三观","zid":1},{"id":2,"title":"物质","zid":1},{"id":3,"title":"安全感","zid":1},{"id":4,"title":"性和谐","zid":1}]
         */

        private String tid;
        private String name;
        private List<ListBean> list;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * title : 三观
             * zid : 1
             */

            private String id;
            private String title;
            private String zid;


            public String getId() {
                return id == null ? "" : id;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public String getZid() {
                return zid == null ? "" : zid;
            }

            public void setId(String id) {
                this.id = id;
            }


            public void setTitle(String title) {
                this.title = title;
            }


            public void setZid(String zid) {
                this.zid = zid;
            }
        }
    }
}
