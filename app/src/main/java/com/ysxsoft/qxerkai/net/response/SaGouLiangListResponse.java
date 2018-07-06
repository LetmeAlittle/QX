package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class SaGouLiangListResponse extends BaseResponse {
    /**
     * message : success!
     * status_code : 200
     * data : {"list":[{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":17,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:00","uid":10157,"sid":16,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/5603152879094010157.png"},{"img":"http://116.62.217.183/storage/dog/4835152879094010157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":12,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:00","uid":10157,"sid":11,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/5603152879094010157.png"},{"img":"http://116.62.217.183/storage/dog/4835152879094010157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":7,"content":null,"imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:00","uid":10157,"sid":6,"content":null,"imgs":[{"img":"http://116.62.217.183/storage/dog/5603152879094010157.png"},{"img":"http://116.62.217.183/storage/dog/4835152879094010157.png"}]}],"last_page":2,"top":{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":27,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]}}
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
         * list : [{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":17,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:00","uid":10157,"sid":16,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/5603152879094010157.png"},{"img":"http://116.62.217.183/storage/dog/4835152879094010157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":12,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:00","uid":10157,"sid":11,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/5603152879094010157.png"},{"img":"http://116.62.217.183/storage/dog/4835152879094010157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":7,"content":null,"imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]},{"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:00","uid":10157,"sid":6,"content":null,"imgs":[{"img":"http://116.62.217.183/storage/dog/5603152879094010157.png"},{"img":"http://116.62.217.183/storage/dog/4835152879094010157.png"}]}]
         * last_page : 2
         * top : {"icon":"http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg","username":"8965","dates":"2018-06-12 16:09:24","uid":10157,"sid":27,"content":"","imgs":[{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]}
         */

        private int last_page;
        private TopBean top;
        private List<ListBean> list;

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public TopBean getTop() {
            return top;
        }

        public void setTop(TopBean top) {
            this.top = top;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class TopBean {
            /**
             * icon : http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg
             * username : 8965
             * dates : 2018-06-12 16:09:24
             * uid : 10157
             * sid : 27
             * content :
             * imgs : [{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]
             */

            private String icon;
            private String username;
            private String dates;
            private int uid;
            private int sid;
            private String content;
            private String likes;
            private List<ImgsBean> imgs;

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

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

            public String getDates() {
                return dates;
            }

            public void setDates(String dates) {
                this.dates = dates;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<ImgsBean> getImgs() {
                return imgs;
            }

            public void setImgs(List<ImgsBean> imgs) {
                this.imgs = imgs;
            }

            public static class ImgsBean {
                /**
                 * img : http://116.62.217.183/storage/dog/7817152879096410157.png
                 */

                private String img;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
        }

        public static class ListBean {
            /**
             * icon : http://116.62.217.183/storage/avatar/2018/05/31/avatar_1527748160_10157.jpg
             * username : 8965
             * dates : 2018-06-12 16:09:24
             * uid : 10157
             * sid : 17
             * content :
             * imgs : [{"img":"http://116.62.217.183/storage/dog/7817152879096410157.png"},{"img":"http://116.62.217.183/storage/dog/2137152879096410157.png"}]
             */

            private String icon;
            private String username;
            private String dates;
            private int uid;
            private int sid;
            private String content;
            private List<ImgsBeanX> imgs;
            private String likes;

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

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

            public String getDates() {
                return dates;
            }

            public void setDates(String dates) {
                this.dates = dates;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<ImgsBeanX> getImgs() {
                return imgs;
            }

            public void setImgs(List<ImgsBeanX> imgs) {
                this.imgs = imgs;
            }

            public static class ImgsBeanX {
                /**
                 * img : http://116.62.217.183/storage/dog/7817152879096410157.png
                 */

                private String img;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
        }
    }
}
