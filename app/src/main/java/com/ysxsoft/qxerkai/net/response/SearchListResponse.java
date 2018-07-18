package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class SearchListResponse extends BaseResponse {

    /**
     * data : {"list":[{"dog":5,"id":1063,"nick_name":"SWAT","member_avatar":"http://116.62.217.183/storage/avatar/2017/12/08/avatar_1512693276_1063.png","level":0,"member_account":"30","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":0,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"1063","wy_token":"2d4b4a8fcd120f6ddf8010023a364570","member_cate_id":0,"member_cate_name":"小姐姐","visitor_num":7,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":1118,"nick_name":"Sinance","member_avatar":"http://116.62.217.183/storage/avatar/2017/12/17/avatar_1513524749_1118.png","level":0,"member_account":"0","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":1,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"1118","wy_token":"9e74d1aa57c3d016df6dcc5be73ea9fc","member_cate_id":0,"member_cate_name":"小姐姐","visitor_num":2,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":10017,"nick_name":"SS","member_avatar":"http://116.62.217.183/storage/avatar/2018/01/06/avatar_1515174362_10017.jpg","level":0,"member_account":"0","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":0,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10017","wy_token":"6954f4df12f62b668b27174cdd477261","member_cate_id":0,"member_cate_name":"小姐姐","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":10194,"nick_name":"Sunny_hx","member_avatar":"http://116.62.217.183/storage/avatar/2018/07/17/avatar_1531811868_10194.png","level":0,"member_account":"80","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":2,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10194","wy_token":"b9304e02b00f2eb91e432576b8664df5","member_cate_id":45,"member_cate_name":"小哥哥","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":1,"xiangce":[]},{"dog":5,"id":10195,"nick_name":"Sincerly","member_avatar":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png","level":0,"member_account":"99876","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":0,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10195","wy_token":"3ad2adf0e221bfece84950841f82d003","member_cate_id":45,"member_cate_name":"小哥哥","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":10196,"nick_name":"Sunny","member_avatar":"http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png","level":0,"member_account":"928.6","member_sex":"2","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":2,"flow_num":4,"is_fans":0,"zan_num":1,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10196","wy_token":"d8862b6a2c248c44bc592f042951d650","member_cate_id":45,"member_cate_name":"小哥哥","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":3,"xiangce":[]}],"page":{"current_page":1,"last_page":1,"prev_page_url":null,"next_page_url":null,"total":6}}
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
         * list : [{"dog":5,"id":1063,"nick_name":"SWAT","member_avatar":"http://116.62.217.183/storage/avatar/2017/12/08/avatar_1512693276_1063.png","level":0,"member_account":"30","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":0,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"1063","wy_token":"2d4b4a8fcd120f6ddf8010023a364570","member_cate_id":0,"member_cate_name":"小姐姐","visitor_num":7,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":1118,"nick_name":"Sinance","member_avatar":"http://116.62.217.183/storage/avatar/2017/12/17/avatar_1513524749_1118.png","level":0,"member_account":"0","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":1,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"1118","wy_token":"9e74d1aa57c3d016df6dcc5be73ea9fc","member_cate_id":0,"member_cate_name":"小姐姐","visitor_num":2,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":10017,"nick_name":"SS","member_avatar":"http://116.62.217.183/storage/avatar/2018/01/06/avatar_1515174362_10017.jpg","level":0,"member_account":"0","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":0,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10017","wy_token":"6954f4df12f62b668b27174cdd477261","member_cate_id":0,"member_cate_name":"小姐姐","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":10194,"nick_name":"Sunny_hx","member_avatar":"http://116.62.217.183/storage/avatar/2018/07/17/avatar_1531811868_10194.png","level":0,"member_account":"80","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":2,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10194","wy_token":"b9304e02b00f2eb91e432576b8664df5","member_cate_id":45,"member_cate_name":"小哥哥","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":1,"xiangce":[]},{"dog":5,"id":10195,"nick_name":"Sincerly","member_avatar":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531476895_10195.png","level":0,"member_account":"99876","member_sex":"1","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":0,"flow_num":0,"is_fans":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10195","wy_token":"3ad2adf0e221bfece84950841f82d003","member_cate_id":45,"member_cate_name":"小哥哥","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":0,"xiangce":[]},{"dog":5,"id":10196,"nick_name":"Sunny","member_avatar":"http://116.62.217.183/storage/avatar/2018/07/07/avatar_1530935253_10196.png","level":0,"member_account":"928.6","member_sex":"2","member_age":"","member_price":3,"member_province":"","member_city":"","fans_num":2,"flow_num":4,"is_fans":0,"zan_num":1,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","is_online":1,"birthday":"","member_tag":[],"wy_acid":"10196","wy_token":"d8862b6a2c248c44bc592f042951d650","member_cate_id":45,"member_cate_name":"小哥哥","visitor_num":0,"listen_state":1,"sound_file":"","is_true":0,"is_vip":0,"member_info_is_post":0,"paohuati":2,"zs":10,"js":10,"suo1":5,"suo2":20,"jss":3,"sckq":10,"guard":3,"xiangce":[]}]
         * page : {"current_page":1,"last_page":1,"prev_page_url":null,"next_page_url":null,"total":6}
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * current_page : 1
             * last_page : 1
             * prev_page_url : null
             * next_page_url : null
             * total : 6
             */

            private int current_page;
            private int last_page;

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
            }
        }

        public static class ListBean {
            /**
             * dog : 5
             * id : 1063
             * nick_name : SWAT
             * member_avatar : http://116.62.217.183/storage/avatar/2017/12/08/avatar_1512693276_1063.png
             * level : 0
             * member_account : 30
             * member_sex : 1
             * member_age :
             * member_price : 3
             * member_province :
             * member_city :
             * fans_num : 0
             * flow_num : 0
             * is_fans : 0
             * zan_num : 0
             * member_signature :
             * member_img_1 :
             * member_img_2 :
             * member_img_3 :
             * member_img_4 :
             * is_online : 1
             * birthday :
             * member_tag : []
             * wy_acid : 1063
             * wy_token : 2d4b4a8fcd120f6ddf8010023a364570
             * member_cate_id : 0
             * member_cate_name : 小姐姐
             * visitor_num : 7
             * listen_state : 1
             * sound_file :
             * is_true : 0
             * is_vip : 0
             * member_info_is_post : 0
             * paohuati : 2
             * zs : 10
             * js : 10
             * suo1 : 5
             * suo2 : 20
             * jss : 3
             * sckq : 10
             * guard : 0
             * xiangce : []
             */

            private String dog;
            private String id;
            private String nick_name;
            private String member_avatar;
            private int level;
            private String member_account;
            private int is_online;
            private int is_vip;

            public String getDog() {
                return dog;
            }

            public void setDog(String dog) {
                this.dog = dog;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getMember_account() {
                return member_account;
            }

            public void setMember_account(String member_account) {
                this.member_account = member_account;
            }

            public int getIs_online() {
                return is_online;
            }

            public void setIs_online(int is_online) {
                this.is_online = is_online;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }
        }
    }
}
