package com.ysxsoft.qxerkai.net.response;

import java.util.ArrayList;
import java.util.List;

public class GetLuYinTagListResponse extends BaseResponse{


    /**
     * data : {"current_page":1,"data":[{"id":2,"title":"声音甜美","del":0},{"id":1,"title":"不可言语","del":0}],"first_page_url":"http://116.62.217.183/api/tingtingtag?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/tingtingtag?page=1","next_page_url":null,"path":"http://116.62.217.183/api/tingtingtag","per_page":15,"prev_page_url":null,"to":2,"total":2}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":2,"title":"声音甜美","del":0},{"id":1,"title":"不可言语","del":0}]
         * first_page_url : http://116.62.217.183/api/tingtingtag?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://116.62.217.183/api/tingtingtag?page=1
         * next_page_url : null
         * path : http://116.62.217.183/api/tingtingtag
         * per_page : 15
         * prev_page_url : null
         * to : 2
         * total : 2
         */

        private int last_page;
        private int per_page;
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

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public static class DataBean {
            /**
             * id : 2
             * title : 声音甜美
             * del : 0
             */

            private int id;
            private String title;
            private int del;
            private boolean isSelected =false;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getDel() {
                return del;
            }

            public void setDel(int del) {
                this.del = del;
            }
        }
    }
}
