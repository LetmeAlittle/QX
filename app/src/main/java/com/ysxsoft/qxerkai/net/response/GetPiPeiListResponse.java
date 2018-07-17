package com.ysxsoft.qxerkai.net.response;

import java.util.List;

/**
 * 一键匹配 接口列表
 */
public class GetPiPeiListResponse extends BaseResponse {


	/**
	 * data : {"current_page":1,"data":[{"id":10149,"member_name":"qq_1525328475","member_age":"","member_province":"河南省","member_city":"郑州市","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"A12641AA740441F6C794DF0612F6CE99","level":0,"member_price":3,"member_account":"9903460.6","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":3,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"/storage/user/20180503/10149_img_1.png","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"比尔","created_at":"2018-05-03 14:21:15","updated_at":"2018-07-13 15:45:37","password":"$2y$10$tCEBbFBgSpVEdDDi4jIKhubaCLI7Pa2m4IourgPsEkh5RKfzY8TnS","remember_token":"","wy_acid":"10149","wy_token":"3beaf973e19c7a60806bad5484fc992b","member_avatar":"/storage/avatar/2018/07/13/avatar_1531462019_10149.png","member_area":"","listen_state":1,"visitor_num":4,"is_online":1,"receive":0,"member_cate_id":0,"member_sound":"","member_is_true":0,"member_true_sex":1,"member_true_name":"限制去","member_id_num":"420522199308138114","member_id_front":"http://116.62.217.183/storage/image/20180607/15283420669965.png","member_id_back":"http://116.62.217.183/storage/image/20180607/15283420731408.png","is_vip":0,"member_info_is_post":1,"talk_time":91,"order_sort":0,"lat":"34.801765","lng":"113.611325","background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531462019_10149.png"},{"id":10192,"member_name":"wx_1530711972","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"oajRhw0oGH2mjiittyrRQxVx4pDA","qq_openid":"","level":0,"member_price":3,"member_account":"0","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"宿舍","created_at":"2018-07-04 21:46:12","updated_at":"2018-07-13 14:07:48","password":"$2y$10$GbQMa2Aty75nl3k0sEVn8e8UgUNLZjNoYmn.VplFqezH6al5X1cjq","remember_token":"","wy_acid":"10192","wy_token":"a6a02cea1beb8bde5435d9a35e872c93","member_avatar":"/storage/avatar/2018/07/04/avatar_1530711998_10192.jpg","member_area":"","listen_state":1,"visitor_num":0,"is_online":1,"receive":0,"member_cate_id":45,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.797192","lng":"113.598944","background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/07/04/avatar_1530711998_10192.jpg"},{"id":10156,"member_name":"13937253685","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"2702.5","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"隔音","created_at":"2018-05-04 17:28:41","updated_at":"2018-07-13 14:06:35","password":"$2y$10$RIXcs7pf5RirJNZo4eqzre/X..oibZUU5ZeuSUajgGRF3.GW3E7tq","remember_token":"","wy_acid":"10156","wy_token":"1f86cb64ca88a40dc8cc28ff5f0d8d35","member_avatar":"/storage/avatar/2018/05/04/avatar_1525426154_10156.png","member_area":"","listen_state":1,"visitor_num":2,"is_online":1,"receive":0,"member_cate_id":46,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.801765","lng":"113.611325","background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/05/04/avatar_1525426154_10156.png"},{"id":10140,"member_name":"qq_1523607643","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"E969DC2C1EDB7156C1349A6DB77EEAB4","level":0,"member_price":3,"member_account":"25156.9","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"你在闹，我在笑","created_at":"2018-04-13 16:20:43","updated_at":"2018-07-13 12:04:02","password":"$2y$10$I/Sw9ocuQPStGkmnbYQm1OX1v/IDXKH1SRBFM357HQLqvaVf946v2","remember_token":"","wy_acid":"10140","wy_token":"f1ebc1366dabe9963a5a73b0ac3f6779","member_avatar":"/storage/avatar/2018/07/12/avatar_1531358772_10140.png","member_area":"","listen_state":1,"visitor_num":3,"is_online":1,"receive":0,"member_cate_id":45,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":1,"order_sort":0,"lat":"34.797408","lng":"113.598843","background_pic":null,"wents":"1","icon":"http://116.62.217.183/storage/avatar/2018/07/12/avatar_1531358772_10140.png"},{"id":10148,"member_name":"15713823323","member_age":"17","member_province":"上海","member_city":"上海市","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"991923.3999999986","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":7,"fans_num":2,"zan_num":5,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"2001-01-01","member_tag":[{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"魔","created_at":"2018-05-02 12:29:57","updated_at":"2018-07-11 17:10:34","password":"$2y$10$xgV1ZkWJzURizPZYSrZuteygiAeoIurdmRBX4o0qdU/jaQS7tgiju","remember_token":"","wy_acid":"10148","wy_token":"4aa100f21fa6b3529c3db71d344f87c3","member_avatar":"/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","member_area":"","listen_state":1,"visitor_num":14,"is_online":1,"receive":0,"member_cate_id":49,"member_sound":"http://116.62.217.183/storage/sound/2018/07/05/E6YzG4lifxHdhPPrwTgpCZs472JAzhRPF9uDxTa5.","member_is_true":0,"member_true_sex":1,"member_true_name":"贾名","member_id_num":"410726199904264586","member_id_front":"http://116.62.217.183/storage/image/20180530/15276430507116.jpg","member_id_back":"http://116.62.217.183/storage/image/20180530/15276430765379.jpg","is_vip":0,"member_info_is_post":1,"talk_time":21,"order_sort":0,"lat":"34.801765","lng":"113.611325","background_pic":"/storage/dog/5953153119445010148file.jpg","wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"id":10164,"member_name":"wx_1527049692","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"oajRhw8_bWEqBc5ttEt7p9k66Xlg","qq_openid":"","level":0,"member_price":3,"member_account":"99923","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":1,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"321654","created_at":"2018-05-23 12:28:12","updated_at":"2018-07-11 17:10:34","password":"$2y$10$.2S/YIm7W0cRrZNZMzXb5ONcwxmZd.cxl1bw1CEnO18QLmN4vDvwS","remember_token":"","wy_acid":"10164","wy_token":"056023281c2a59c306af670e290b1e61","member_avatar":"/storage/avatar/2018/05/23/avatar_1527049720_10164.jpg","member_area":"","listen_state":1,"visitor_num":4,"is_online":1,"receive":0,"member_cate_id":0,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":20,"order_sort":0,"lat":null,"lng":null,"background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/05/23/avatar_1527049720_10164.jpg"},{"id":10194,"member_name":"qq_1530777555","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"112A3F6D6B7BF76EE5ECEA07CC753B34","level":0,"member_price":3,"member_account":"0","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"安卓－何晓妮","created_at":"2018-07-05 15:59:15","updated_at":"2018-07-10 17:47:34","password":"$2y$10$hIOJqX0J6qUtjiVBJK8/bO4cwwllY.F3Qmb75fk6fZ.YAbJKqxjaG","remember_token":"","wy_acid":"10194","wy_token":"b9304e02b00f2eb91e432576b8664df5","member_avatar":"/storage/avatar/2018/07/06/avatar_1530873318_10194.png","member_area":"","listen_state":1,"visitor_num":0,"is_online":1,"receive":0,"member_cate_id":45,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":null,"lng":null,"background_pic":null,"wents":"1,5,9,13","icon":"http://116.62.217.183/storage/avatar/2018/07/06/avatar_1530873318_10194.png"}],"first_page_url":"http://116.62.217.183/api/pipei?page=1","from":1,"last_page":36,"last_page_url":"http://116.62.217.183/api/pipei?page=36","next_page_url":"http://116.62.217.183/api/pipei?page=2","path":"http://116.62.217.183/api/pipei","per_page":7,"prev_page_url":null,"to":7,"total":246}
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
		 * data : [{"id":10149,"member_name":"qq_1525328475","member_age":"","member_province":"河南省","member_city":"郑州市","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"A12641AA740441F6C794DF0612F6CE99","level":0,"member_price":3,"member_account":"9903460.6","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":3,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"/storage/user/20180503/10149_img_1.png","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"比尔","created_at":"2018-05-03 14:21:15","updated_at":"2018-07-13 15:45:37","password":"$2y$10$tCEBbFBgSpVEdDDi4jIKhubaCLI7Pa2m4IourgPsEkh5RKfzY8TnS","remember_token":"","wy_acid":"10149","wy_token":"3beaf973e19c7a60806bad5484fc992b","member_avatar":"/storage/avatar/2018/07/13/avatar_1531462019_10149.png","member_area":"","listen_state":1,"visitor_num":4,"is_online":1,"receive":0,"member_cate_id":0,"member_sound":"","member_is_true":0,"member_true_sex":1,"member_true_name":"限制去","member_id_num":"420522199308138114","member_id_front":"http://116.62.217.183/storage/image/20180607/15283420669965.png","member_id_back":"http://116.62.217.183/storage/image/20180607/15283420731408.png","is_vip":0,"member_info_is_post":1,"talk_time":91,"order_sort":0,"lat":"34.801765","lng":"113.611325","background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531462019_10149.png"},{"id":10192,"member_name":"wx_1530711972","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"oajRhw0oGH2mjiittyrRQxVx4pDA","qq_openid":"","level":0,"member_price":3,"member_account":"0","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"宿舍","created_at":"2018-07-04 21:46:12","updated_at":"2018-07-13 14:07:48","password":"$2y$10$GbQMa2Aty75nl3k0sEVn8e8UgUNLZjNoYmn.VplFqezH6al5X1cjq","remember_token":"","wy_acid":"10192","wy_token":"a6a02cea1beb8bde5435d9a35e872c93","member_avatar":"/storage/avatar/2018/07/04/avatar_1530711998_10192.jpg","member_area":"","listen_state":1,"visitor_num":0,"is_online":1,"receive":0,"member_cate_id":45,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.797192","lng":"113.598944","background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/07/04/avatar_1530711998_10192.jpg"},{"id":10156,"member_name":"13937253685","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"2702.5","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"隔音","created_at":"2018-05-04 17:28:41","updated_at":"2018-07-13 14:06:35","password":"$2y$10$RIXcs7pf5RirJNZo4eqzre/X..oibZUU5ZeuSUajgGRF3.GW3E7tq","remember_token":"","wy_acid":"10156","wy_token":"1f86cb64ca88a40dc8cc28ff5f0d8d35","member_avatar":"/storage/avatar/2018/05/04/avatar_1525426154_10156.png","member_area":"","listen_state":1,"visitor_num":2,"is_online":1,"receive":0,"member_cate_id":46,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.801765","lng":"113.611325","background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/05/04/avatar_1525426154_10156.png"},{"id":10140,"member_name":"qq_1523607643","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"E969DC2C1EDB7156C1349A6DB77EEAB4","level":0,"member_price":3,"member_account":"25156.9","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"你在闹，我在笑","created_at":"2018-04-13 16:20:43","updated_at":"2018-07-13 12:04:02","password":"$2y$10$I/Sw9ocuQPStGkmnbYQm1OX1v/IDXKH1SRBFM357HQLqvaVf946v2","remember_token":"","wy_acid":"10140","wy_token":"f1ebc1366dabe9963a5a73b0ac3f6779","member_avatar":"/storage/avatar/2018/07/12/avatar_1531358772_10140.png","member_area":"","listen_state":1,"visitor_num":3,"is_online":1,"receive":0,"member_cate_id":45,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":1,"order_sort":0,"lat":"34.797408","lng":"113.598843","background_pic":null,"wents":"1","icon":"http://116.62.217.183/storage/avatar/2018/07/12/avatar_1531358772_10140.png"},{"id":10148,"member_name":"15713823323","member_age":"17","member_province":"上海","member_city":"上海市","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"991923.3999999986","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":7,"fans_num":2,"zan_num":5,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"2001-01-01","member_tag":[{"id":1,"text":"情感聊天","color":"#f59c9e"},{"id":3,"text":"知性御姐","color":"#b483a3"},{"id":4,"text":"热血青年","color":"#56e0cf"},{"id":5,"text":"老司机","color":"#ff4a06"},{"id":6,"text":"可人儿","color":"#ece006"},{"id":7,"text":"文艺范","color":"#86c02e"}],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"魔","created_at":"2018-05-02 12:29:57","updated_at":"2018-07-11 17:10:34","password":"$2y$10$xgV1ZkWJzURizPZYSrZuteygiAeoIurdmRBX4o0qdU/jaQS7tgiju","remember_token":"","wy_acid":"10148","wy_token":"4aa100f21fa6b3529c3db71d344f87c3","member_avatar":"/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","member_area":"","listen_state":1,"visitor_num":14,"is_online":1,"receive":0,"member_cate_id":49,"member_sound":"http://116.62.217.183/storage/sound/2018/07/05/E6YzG4lifxHdhPPrwTgpCZs472JAzhRPF9uDxTa5.","member_is_true":0,"member_true_sex":1,"member_true_name":"贾名","member_id_num":"410726199904264586","member_id_front":"http://116.62.217.183/storage/image/20180530/15276430507116.jpg","member_id_back":"http://116.62.217.183/storage/image/20180530/15276430765379.jpg","is_vip":0,"member_info_is_post":1,"talk_time":21,"order_sort":0,"lat":"34.801765","lng":"113.611325","background_pic":"/storage/dog/5953153119445010148file.jpg","wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"id":10164,"member_name":"wx_1527049692","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"oajRhw8_bWEqBc5ttEt7p9k66Xlg","qq_openid":"","level":0,"member_price":3,"member_account":"99923","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":1,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"321654","created_at":"2018-05-23 12:28:12","updated_at":"2018-07-11 17:10:34","password":"$2y$10$.2S/YIm7W0cRrZNZMzXb5ONcwxmZd.cxl1bw1CEnO18QLmN4vDvwS","remember_token":"","wy_acid":"10164","wy_token":"056023281c2a59c306af670e290b1e61","member_avatar":"/storage/avatar/2018/05/23/avatar_1527049720_10164.jpg","member_area":"","listen_state":1,"visitor_num":4,"is_online":1,"receive":0,"member_cate_id":0,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":20,"order_sort":0,"lat":null,"lng":null,"background_pic":null,"wents":null,"icon":"http://116.62.217.183/storage/avatar/2018/05/23/avatar_1527049720_10164.jpg"},{"id":10194,"member_name":"qq_1530777555","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"112A3F6D6B7BF76EE5ECEA07CC753B34","level":0,"member_price":3,"member_account":"0","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":[],"qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"安卓－何晓妮","created_at":"2018-07-05 15:59:15","updated_at":"2018-07-10 17:47:34","password":"$2y$10$hIOJqX0J6qUtjiVBJK8/bO4cwwllY.F3Qmb75fk6fZ.YAbJKqxjaG","remember_token":"","wy_acid":"10194","wy_token":"b9304e02b00f2eb91e432576b8664df5","member_avatar":"/storage/avatar/2018/07/06/avatar_1530873318_10194.png","member_area":"","listen_state":1,"visitor_num":0,"is_online":1,"receive":0,"member_cate_id":45,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":null,"lng":null,"background_pic":null,"wents":"1,5,9,13","icon":"http://116.62.217.183/storage/avatar/2018/07/06/avatar_1530873318_10194.png"}]
		 * first_page_url : http://116.62.217.183/api/pipei?page=1
		 * from : 1
		 * last_page : 36
		 * last_page_url : http://116.62.217.183/api/pipei?page=36
		 * next_page_url : http://116.62.217.183/api/pipei?page=2
		 * path : http://116.62.217.183/api/pipei
		 * per_page : 7
		 * prev_page_url : null
		 * to : 7
		 * total : 246
		 */

		private int current_page;
		private String first_page_url;
		private int from;
		private int last_page;
		private String last_page_url;
		private String next_page_url;
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

		public String getNext_page_url() {
			return next_page_url;
		}

		public void setNext_page_url(String next_page_url) {
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
			 * id : 10149
			 * member_name : qq_1525328475
			 * member_age :
			 * member_province : 河南省
			 * member_city : 郑州市
			 * member_sex : 1
			 * member_state : 0
			 * wx_openid :
			 * qq_openid : A12641AA740441F6C794DF0612F6CE99
			 * level : 0
			 * member_price : 3
			 * member_account : 9903460.6
			 * member_fronze : 0
			 * vip_start_time : 0
			 * vip_end_time : 0
			 * flow_num : 3
			 * fans_num : 0
			 * zan_num : 0
			 * member_signature :
			 * member_img_1 : /storage/user/20180503/10149_img_1.png
			 * member_img_2 :
			 * member_img_3 :
			 * member_img_4 :
			 * birthday :
			 * member_tag : []
			 * qq_bind : 0
			 * wx_bind : 0
			 * mobile_bind : 0
			 * member_mobile :
			 * nick_name : 比尔
			 * created_at : 2018-05-03 14:21:15
			 * updated_at : 2018-07-13 15:45:37
			 * password : $2y$10$tCEBbFBgSpVEdDDi4jIKhubaCLI7Pa2m4IourgPsEkh5RKfzY8TnS
			 * remember_token :
			 * wy_acid : 10149
			 * wy_token : 3beaf973e19c7a60806bad5484fc992b
			 * member_avatar : /storage/avatar/2018/07/13/avatar_1531462019_10149.png
			 * member_area :
			 * listen_state : 1
			 * visitor_num : 4
			 * is_online : 1
			 * receive : 0
			 * member_cate_id : 0
			 * member_sound :
			 * member_is_true : 0
			 * member_true_sex : 1
			 * member_true_name : 限制去
			 * member_id_num : 420522199308138114
			 * member_id_front : http://116.62.217.183/storage/image/20180607/15283420669965.png
			 * member_id_back : http://116.62.217.183/storage/image/20180607/15283420731408.png
			 * is_vip : 0
			 * member_info_is_post : 1
			 * talk_time : 91
			 * order_sort : 0
			 * lat : 34.801765
			 * lng : 113.611325
			 * background_pic : null
			 * wents : null
			 * icon : http://116.62.217.183/storage/avatar/2018/07/13/avatar_1531462019_10149.png
			 */

			private int id;
			private String member_name;
			private String member_age;
			private String member_province;
			private String member_city;
			private int member_sex;
			private int member_state;
			private String wx_openid;
			private String qq_openid;
			private int level;
			private int member_price;
			private String member_account;
			private int member_fronze;
			private int vip_start_time;
			private int vip_end_time;
			private int flow_num;
			private int fans_num;
			private int zan_num;
			private String member_signature;
			private String member_img_1;
			private String member_img_2;
			private String member_img_3;
			private String member_img_4;
			private String birthday;
			private int qq_bind;
			private int wx_bind;
			private int mobile_bind;
			private String member_mobile;
			private String nick_name;
			private String created_at;
			private String updated_at;
			private String password;
			private String remember_token;
			private String wy_acid;
			private String wy_token;
			private String member_avatar;
			private String member_area;
			private int listen_state;
			private int visitor_num;
			private int is_online;
			private int receive;
			private int member_cate_id;
			private String member_sound;
			private int member_is_true;
			private int member_true_sex;
			private String member_true_name;
			private String member_id_num;
			private String member_id_front;
			private String member_id_back;
			private int is_vip;
			private int member_info_is_post;
			private int talk_time;
			private int order_sort;
			private String lat;
			private String lng;
			private Object background_pic;
			private Object wents;
			private String icon;
			private List<?> member_tag;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getMember_name() {
				return member_name;
			}

			public void setMember_name(String member_name) {
				this.member_name = member_name;
			}

			public String getMember_age() {
				return member_age;
			}

			public void setMember_age(String member_age) {
				this.member_age = member_age;
			}

			public String getMember_province() {
				return member_province;
			}

			public void setMember_province(String member_province) {
				this.member_province = member_province;
			}

			public String getMember_city() {
				return member_city;
			}

			public void setMember_city(String member_city) {
				this.member_city = member_city;
			}

			public int getMember_sex() {
				return member_sex;
			}

			public void setMember_sex(int member_sex) {
				this.member_sex = member_sex;
			}

			public int getMember_state() {
				return member_state;
			}

			public void setMember_state(int member_state) {
				this.member_state = member_state;
			}

			public String getWx_openid() {
				return wx_openid;
			}

			public void setWx_openid(String wx_openid) {
				this.wx_openid = wx_openid;
			}

			public String getQq_openid() {
				return qq_openid;
			}

			public void setQq_openid(String qq_openid) {
				this.qq_openid = qq_openid;
			}

			public int getLevel() {
				return level;
			}

			public void setLevel(int level) {
				this.level = level;
			}

			public int getMember_price() {
				return member_price;
			}

			public void setMember_price(int member_price) {
				this.member_price = member_price;
			}

			public String getMember_account() {
				return member_account;
			}

			public void setMember_account(String member_account) {
				this.member_account = member_account;
			}

			public int getMember_fronze() {
				return member_fronze;
			}

			public void setMember_fronze(int member_fronze) {
				this.member_fronze = member_fronze;
			}

			public int getVip_start_time() {
				return vip_start_time;
			}

			public void setVip_start_time(int vip_start_time) {
				this.vip_start_time = vip_start_time;
			}

			public int getVip_end_time() {
				return vip_end_time;
			}

			public void setVip_end_time(int vip_end_time) {
				this.vip_end_time = vip_end_time;
			}

			public int getFlow_num() {
				return flow_num;
			}

			public void setFlow_num(int flow_num) {
				this.flow_num = flow_num;
			}

			public int getFans_num() {
				return fans_num;
			}

			public void setFans_num(int fans_num) {
				this.fans_num = fans_num;
			}

			public int getZan_num() {
				return zan_num;
			}

			public void setZan_num(int zan_num) {
				this.zan_num = zan_num;
			}

			public String getMember_signature() {
				return member_signature;
			}

			public void setMember_signature(String member_signature) {
				this.member_signature = member_signature;
			}

			public String getMember_img_1() {
				return member_img_1;
			}

			public void setMember_img_1(String member_img_1) {
				this.member_img_1 = member_img_1;
			}

			public String getMember_img_2() {
				return member_img_2;
			}

			public void setMember_img_2(String member_img_2) {
				this.member_img_2 = member_img_2;
			}

			public String getMember_img_3() {
				return member_img_3;
			}

			public void setMember_img_3(String member_img_3) {
				this.member_img_3 = member_img_3;
			}

			public String getMember_img_4() {
				return member_img_4;
			}

			public void setMember_img_4(String member_img_4) {
				this.member_img_4 = member_img_4;
			}

			public String getBirthday() {
				return birthday;
			}

			public void setBirthday(String birthday) {
				this.birthday = birthday;
			}

			public int getQq_bind() {
				return qq_bind;
			}

			public void setQq_bind(int qq_bind) {
				this.qq_bind = qq_bind;
			}

			public int getWx_bind() {
				return wx_bind;
			}

			public void setWx_bind(int wx_bind) {
				this.wx_bind = wx_bind;
			}

			public int getMobile_bind() {
				return mobile_bind;
			}

			public void setMobile_bind(int mobile_bind) {
				this.mobile_bind = mobile_bind;
			}

			public String getMember_mobile() {
				return member_mobile;
			}

			public void setMember_mobile(String member_mobile) {
				this.member_mobile = member_mobile;
			}

			public String getNick_name() {
				return nick_name;
			}

			public void setNick_name(String nick_name) {
				this.nick_name = nick_name;
			}

			public String getCreated_at() {
				return created_at;
			}

			public void setCreated_at(String created_at) {
				this.created_at = created_at;
			}

			public String getUpdated_at() {
				return updated_at;
			}

			public void setUpdated_at(String updated_at) {
				this.updated_at = updated_at;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

			public String getRemember_token() {
				return remember_token;
			}

			public void setRemember_token(String remember_token) {
				this.remember_token = remember_token;
			}

			public String getWy_acid() {
				return wy_acid;
			}

			public void setWy_acid(String wy_acid) {
				this.wy_acid = wy_acid;
			}

			public String getWy_token() {
				return wy_token;
			}

			public void setWy_token(String wy_token) {
				this.wy_token = wy_token;
			}

			public String getMember_avatar() {
				return member_avatar;
			}

			public void setMember_avatar(String member_avatar) {
				this.member_avatar = member_avatar;
			}

			public String getMember_area() {
				return member_area;
			}

			public void setMember_area(String member_area) {
				this.member_area = member_area;
			}

			public int getListen_state() {
				return listen_state;
			}

			public void setListen_state(int listen_state) {
				this.listen_state = listen_state;
			}

			public int getVisitor_num() {
				return visitor_num;
			}

			public void setVisitor_num(int visitor_num) {
				this.visitor_num = visitor_num;
			}

			public int getIs_online() {
				return is_online;
			}

			public void setIs_online(int is_online) {
				this.is_online = is_online;
			}

			public int getReceive() {
				return receive;
			}

			public void setReceive(int receive) {
				this.receive = receive;
			}

			public int getMember_cate_id() {
				return member_cate_id;
			}

			public void setMember_cate_id(int member_cate_id) {
				this.member_cate_id = member_cate_id;
			}

			public String getMember_sound() {
				return member_sound;
			}

			public void setMember_sound(String member_sound) {
				this.member_sound = member_sound;
			}

			public int getMember_is_true() {
				return member_is_true;
			}

			public void setMember_is_true(int member_is_true) {
				this.member_is_true = member_is_true;
			}

			public int getMember_true_sex() {
				return member_true_sex;
			}

			public void setMember_true_sex(int member_true_sex) {
				this.member_true_sex = member_true_sex;
			}

			public String getMember_true_name() {
				return member_true_name;
			}

			public void setMember_true_name(String member_true_name) {
				this.member_true_name = member_true_name;
			}

			public String getMember_id_num() {
				return member_id_num;
			}

			public void setMember_id_num(String member_id_num) {
				this.member_id_num = member_id_num;
			}

			public String getMember_id_front() {
				return member_id_front;
			}

			public void setMember_id_front(String member_id_front) {
				this.member_id_front = member_id_front;
			}

			public String getMember_id_back() {
				return member_id_back;
			}

			public void setMember_id_back(String member_id_back) {
				this.member_id_back = member_id_back;
			}

			public int getIs_vip() {
				return is_vip;
			}

			public void setIs_vip(int is_vip) {
				this.is_vip = is_vip;
			}

			public int getMember_info_is_post() {
				return member_info_is_post;
			}

			public void setMember_info_is_post(int member_info_is_post) {
				this.member_info_is_post = member_info_is_post;
			}

			public int getTalk_time() {
				return talk_time;
			}

			public void setTalk_time(int talk_time) {
				this.talk_time = talk_time;
			}

			public int getOrder_sort() {
				return order_sort;
			}

			public void setOrder_sort(int order_sort) {
				this.order_sort = order_sort;
			}

			public String getLat() {
				return lat;
			}

			public void setLat(String lat) {
				this.lat = lat;
			}

			public String getLng() {
				return lng;
			}

			public void setLng(String lng) {
				this.lng = lng;
			}

			public Object getBackground_pic() {
				return background_pic;
			}

			public void setBackground_pic(Object background_pic) {
				this.background_pic = background_pic;
			}

			public Object getWents() {
				return wents;
			}

			public void setWents(Object wents) {
				this.wents = wents;
			}

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}

			public List<?> getMember_tag() {
				return member_tag;
			}

			public void setMember_tag(List<?> member_tag) {
				this.member_tag = member_tag;
			}
		}
	}
}
