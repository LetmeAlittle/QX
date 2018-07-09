package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetNoticeListResponse extends BaseResponse {

    /**
     * data : {"current_page":1,"data":[{"id":17,"title":"32121","content":"231312321","addtime":1530354409,"del":0,"type":1},{"id":16,"title":"话题五","content":"   ","addtime":1530354390,"del":0,"type":1},{"id":15,"title":"话题五","content":"   ","addtime":1530354350,"del":0,"type":1},{"id":14,"title":"话题五","content":"   ","addtime":1530354350,"del":0,"type":1},{"id":6,"title":"随机话题4","content":null,"addtime":null,"del":0,"type":1},{"id":5,"title":"随机话题3","content":null,"addtime":null,"del":0,"type":1},{"id":4,"title":"随机话题2","content":null,"addtime":null,"del":0,"type":1},{"id":3,"title":"随机话题1","content":null,"addtime":null,"del":0,"type":1}],"first_page_url":"http://116.62.217.183/api/getNotice?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/getNotice?page=1","next_page_url":null,"path":"http://116.62.217.183/api/getNotice","per_page":15,"prev_page_url":null,"to":8,"total":8}
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
         * data : [{"id":17,"title":"32121","content":"231312321","addtime":1530354409,"del":0,"type":1},{"id":16,"title":"话题五","content":"   ","addtime":1530354390,"del":0,"type":1},{"id":15,"title":"话题五","content":"   ","addtime":1530354350,"del":0,"type":1},{"id":14,"title":"话题五","content":"   ","addtime":1530354350,"del":0,"type":1},{"id":6,"title":"随机话题4","content":null,"addtime":null,"del":0,"type":1},{"id":5,"title":"随机话题3","content":null,"addtime":null,"del":0,"type":1},{"id":4,"title":"随机话题2","content":null,"addtime":null,"del":0,"type":1},{"id":3,"title":"随机话题1","content":null,"addtime":null,"del":0,"type":1}]
         * first_page_url : http://116.62.217.183/api/getNotice?page=1
         * from : 1
         * last_page : 1
         * last_page_url : http://116.62.217.183/api/getNotice?page=1
         * next_page_url : null
         * path : http://116.62.217.183/api/getNotice
         * per_page : 15
         * prev_page_url : null
         * to : 8
         * total : 8
         */

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
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

        public void setNext_page_url(Object next_page_url) {
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

        public void setPrev_page_url(Object prev_page_url) {
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
             * id : 17
             * title : 32121
             * content : 231312321
             * addtime : 1530354409
             * del : 0
             * type : 1
             */

            private int id;
            private String title;
            private String content;
            private int addtime;
            private int del;
            private int type;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAddtime() {
                return addtime;
            }

            public void setAddtime(int addtime) {
                this.addtime = addtime;
            }

            public int getDel() {
                return del;
            }

            public void setDel(int del) {
                this.del = del;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
