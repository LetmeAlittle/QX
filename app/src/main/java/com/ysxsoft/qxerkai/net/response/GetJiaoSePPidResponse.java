package com.ysxsoft.qxerkai.net.response;

public class GetJiaoSePPidResponse  extends BaseResponse{

	/**
	 * data : {"ppid":2,"flag":0,"list":{"id":10147,"member_name":"wx_1525092221","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"oajRhw1MUD0Ohr4rdSkKXlUkMFu8","qq_openid":"","level":0,"member_price":3,"member_account":"0","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":"","qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"豬公子","created_at":"2018-04-30 20:43:41","updated_at":"2018-04-30 20:44:05","password":"$2y$10$CZTLi4ISqg9aOqLy4D6Xrerk32ycngjpVtqbH4LZBIVEdgHYImrBS","remember_token":"","wy_acid":"10147","wy_token":"0714de4d98f3fe0896a58984b8e3be10","member_avatar":"/storage/avatar/2018/04/30/avatar_1525092243_10147.jpg","member_area":"","listen_state":1,"visitor_num":4,"is_online":1,"receive":0,"member_cate_id":0,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.784516","lng":"113.454917","background_pic":null,"icon":"http://116.62.217.183/storage/avatar/2018/04/30/avatar_1525092243_10147.jpg"}}
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
		 * ppid : 2
		 * flag : 0
		 * list : {"id":10147,"member_name":"wx_1525092221","member_age":"","member_province":"","member_city":"","member_sex":1,"member_state":0,"wx_openid":"oajRhw1MUD0Ohr4rdSkKXlUkMFu8","qq_openid":"","level":0,"member_price":3,"member_account":"0","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":0,"fans_num":0,"zan_num":0,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"","member_tag":"","qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"豬公子","created_at":"2018-04-30 20:43:41","updated_at":"2018-04-30 20:44:05","password":"$2y$10$CZTLi4ISqg9aOqLy4D6Xrerk32ycngjpVtqbH4LZBIVEdgHYImrBS","remember_token":"","wy_acid":"10147","wy_token":"0714de4d98f3fe0896a58984b8e3be10","member_avatar":"/storage/avatar/2018/04/30/avatar_1525092243_10147.jpg","member_area":"","listen_state":1,"visitor_num":4,"is_online":1,"receive":0,"member_cate_id":0,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.784516","lng":"113.454917","background_pic":null,"icon":"http://116.62.217.183/storage/avatar/2018/04/30/avatar_1525092243_10147.jpg"}
		 */

		private int ppid;
		private int flag;
		private ListBean list;

		public int getPpid() {
			return ppid;
		}

		public void setPpid(int ppid) {
			this.ppid = ppid;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public ListBean getList() {
			return list;
		}

		public void setList(ListBean list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * id : 10147
			 * member_name : wx_1525092221
			 * member_age :
			 * member_province :
			 * member_city :
			 * member_sex : 1
			 * member_state : 0
			 * wx_openid : oajRhw1MUD0Ohr4rdSkKXlUkMFu8
			 * qq_openid :
			 * level : 0
			 * member_price : 3
			 * member_account : 0
			 * member_fronze : 0
			 * vip_start_time : 0
			 * vip_end_time : 0
			 * flow_num : 0
			 * fans_num : 0
			 * zan_num : 0
			 * member_signature :
			 * member_img_1 :
			 * member_img_2 :
			 * member_img_3 :
			 * member_img_4 :
			 * birthday :
			 * member_tag :
			 * qq_bind : 0
			 * wx_bind : 0
			 * mobile_bind : 0
			 * member_mobile :
			 * nick_name : 豬公子
			 * created_at : 2018-04-30 20:43:41
			 * updated_at : 2018-04-30 20:44:05
			 * password : $2y$10$CZTLi4ISqg9aOqLy4D6Xrerk32ycngjpVtqbH4LZBIVEdgHYImrBS
			 * remember_token :
			 * wy_acid : 10147
			 * wy_token : 0714de4d98f3fe0896a58984b8e3be10
			 * member_avatar : /storage/avatar/2018/04/30/avatar_1525092243_10147.jpg
			 * member_area :
			 * listen_state : 1
			 * visitor_num : 4
			 * is_online : 1
			 * receive : 0
			 * member_cate_id : 0
			 * member_sound :
			 * member_is_true : 0
			 * member_true_sex : 0
			 * member_true_name :
			 * member_id_num :
			 * member_id_front :
			 * member_id_back :
			 * is_vip : 0
			 * member_info_is_post : 0
			 * talk_time : 0
			 * order_sort : 0
			 * lat : 34.784516
			 * lng : 113.454917
			 * background_pic : null
			 * icon : http://116.62.217.183/storage/avatar/2018/04/30/avatar_1525092243_10147.jpg
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
			private String member_tag;
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
			private String icon;

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

			public String getMember_tag() {
				return member_tag;
			}

			public void setMember_tag(String member_tag) {
				this.member_tag = member_tag;
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

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}
		}
	}
}
