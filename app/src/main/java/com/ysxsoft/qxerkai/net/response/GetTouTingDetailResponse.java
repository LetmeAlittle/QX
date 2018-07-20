package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetTouTingDetailResponse extends BaseResponse {

	/**
	 * data : {"id":558658037,"uid":10148,"fuid":10182,"addtime":1529910160,"endtime":1529958927,"flag":0,"num":0,"suo":0,"suotime":null,"ttnum":null,"user":{"id":10148,"member_name":"15713823323","member_age":"18","member_province":"上海","member_city":"上海市","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"1","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":4,"fans_num":1,"zan_num":3,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"2000-01-01","member_tag":"[1,3,4,5,6,7]","qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"魔鬼","created_at":"2018-05-02 12:29:57","updated_at":"2018-06-25 17:54:18","password":"$2y$10$c6JrNXgKXKnaKSU0pHZQJ.f6D5DEGBqKcjYnV08f4V4It1QO9NNk2","remember_token":"","wy_acid":"10148","wy_token":"4aa100f21fa6b3529c3db71d344f87c3","member_avatar":"/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","member_area":"","listen_state":1,"visitor_num":14,"is_online":1,"receive":0,"member_cate_id":49,"member_sound":"http://116.62.217.183/storage/sound/2018/06/05/VIolfUsBMiNvZUpIxpdnaPuSJiohoSywUTqhewAl.","member_is_true":0,"member_true_sex":1,"member_true_name":"贾名","member_id_num":"410726199904264586","member_id_front":"http://116.62.217.183/storage/image/20180530/15276430507116.jpg","member_id_back":"http://116.62.217.183/storage/image/20180530/15276430765379.jpg","is_vip":0,"member_info_is_post":1,"talk_time":21,"order_sort":0,"lat":"34.801765","lng":"113.611325","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},"f_user":{"id":10182,"member_name":"13333826947","member_age":"18","member_province":"","member_city":"","member_sex":2,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"1.6","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":1,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"/storage/user/20180623/10182_img_1.jpg","member_img_2":"/storage/user/20180623/10182_img_2.jpg","member_img_3":"/storage/user/20180623/10182_img_3.jpg","member_img_4":"","birthday":"2000-08-23","member_tag":"","qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"在一起","created_at":"2018-06-21 16:57:53","updated_at":"2018-06-25 15:14:10","password":"$2y$10$j6OXAZDQQ0vZwBl9faZUeOubo7lEMLy9YHLSqUEldSlhaFUQDecwK","remember_token":"","wy_acid":"10182","wy_token":"8ca89606b93920fe830369a8e21dd274","member_avatar":"/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg","member_area":"","listen_state":1,"visitor_num":1,"is_online":1,"receive":0,"member_cate_id":46,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.801765","lng":"113.611325","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"}}
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
		 * id : 558658037
		 * uid : 10148
		 * fuid : 10182
		 * addtime : 1529910160
		 * endtime : 1529958927
		 * flag : 0
		 * num : 0
		 * suo : 0
		 * suotime : null
		 * ttnum : null
		 * user : {"id":10148,"member_name":"15713823323","member_age":"18","member_province":"上海","member_city":"上海市","member_sex":1,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"1","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":4,"fans_num":1,"zan_num":3,"member_signature":"","member_img_1":"","member_img_2":"","member_img_3":"","member_img_4":"","birthday":"2000-01-01","member_tag":"[1,3,4,5,6,7]","qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"魔鬼","created_at":"2018-05-02 12:29:57","updated_at":"2018-06-25 17:54:18","password":"$2y$10$c6JrNXgKXKnaKSU0pHZQJ.f6D5DEGBqKcjYnV08f4V4It1QO9NNk2","remember_token":"","wy_acid":"10148","wy_token":"4aa100f21fa6b3529c3db71d344f87c3","member_avatar":"/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","member_area":"","listen_state":1,"visitor_num":14,"is_online":1,"receive":0,"member_cate_id":49,"member_sound":"http://116.62.217.183/storage/sound/2018/06/05/VIolfUsBMiNvZUpIxpdnaPuSJiohoSywUTqhewAl.","member_is_true":0,"member_true_sex":1,"member_true_name":"贾名","member_id_num":"410726199904264586","member_id_front":"http://116.62.217.183/storage/image/20180530/15276430507116.jpg","member_id_back":"http://116.62.217.183/storage/image/20180530/15276430765379.jpg","is_vip":0,"member_info_is_post":1,"talk_time":21,"order_sort":0,"lat":"34.801765","lng":"113.611325","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"}
		 * f_user : {"id":10182,"member_name":"13333826947","member_age":"18","member_province":"","member_city":"","member_sex":2,"member_state":0,"wx_openid":"","qq_openid":"","level":0,"member_price":3,"member_account":"1.6","member_fronze":0,"vip_start_time":0,"vip_end_time":0,"flow_num":1,"fans_num":1,"zan_num":0,"member_signature":"","member_img_1":"/storage/user/20180623/10182_img_1.jpg","member_img_2":"/storage/user/20180623/10182_img_2.jpg","member_img_3":"/storage/user/20180623/10182_img_3.jpg","member_img_4":"","birthday":"2000-08-23","member_tag":"","qq_bind":0,"wx_bind":0,"mobile_bind":0,"member_mobile":"","nick_name":"在一起","created_at":"2018-06-21 16:57:53","updated_at":"2018-06-25 15:14:10","password":"$2y$10$j6OXAZDQQ0vZwBl9faZUeOubo7lEMLy9YHLSqUEldSlhaFUQDecwK","remember_token":"","wy_acid":"10182","wy_token":"8ca89606b93920fe830369a8e21dd274","member_avatar":"/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg","member_area":"","listen_state":1,"visitor_num":1,"is_online":1,"receive":0,"member_cate_id":46,"member_sound":"","member_is_true":0,"member_true_sex":0,"member_true_name":"","member_id_num":"","member_id_front":"","member_id_back":"","is_vip":0,"member_info_is_post":0,"talk_time":0,"order_sort":0,"lat":"34.801765","lng":"113.611325","icon":"http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg"}
		 */

		private String id;
		private int uid;
		private int fuid;
		private int addtime;
		private int endtime;
		private int flag;
		private int num;
		private int suo;
		private int suotime;
		private int ttnum;
		private UserBean user;
		private FUserBean f_user;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public int getFuid() {
			return fuid;
		}

		public void setFuid(int fuid) {
			this.fuid = fuid;
		}

		public int getAddtime() {
			return addtime;
		}

		public void setAddtime(int addtime) {
			this.addtime = addtime;
		}

		public int getEndtime() {
			return endtime;
		}

		public void setEndtime(int endtime) {
			this.endtime = endtime;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getSuo() {
			return suo;
		}

		public void setSuo(int suo) {
			this.suo = suo;
		}

		public Object getSuotime() {
			return suotime;
		}

		public void setSuotime(int suotime) {
			this.suotime = suotime;
		}

		public int getTtnum() {
			return ttnum;
		}

		public void setTtnum(int ttnum) {
			this.ttnum = ttnum;
		}

		public UserBean getUser() {
			return user;
		}

		public void setUser(UserBean user) {
			this.user = user;
		}

		public FUserBean getF_user() {
			return f_user;
		}

		public void setF_user(FUserBean f_user) {
			this.f_user = f_user;
		}

		public static class UserBean {
			/**
			 * id : 10148
			 * member_name : 15713823323
			 * member_age : 18
			 * member_province : 上海
			 * member_city : 上海市
			 * member_sex : 1
			 * member_state : 0
			 * wx_openid :
			 * qq_openid :
			 * level : 0
			 * member_price : 3
			 * member_account : 1
			 * member_fronze : 0
			 * vip_start_time : 0
			 * vip_end_time : 0
			 * flow_num : 4
			 * fans_num : 1
			 * zan_num : 3
			 * member_signature :
			 * member_img_1 :
			 * member_img_2 :
			 * member_img_3 :
			 * member_img_4 :
			 * birthday : 2000-01-01
			 * member_tag : [1,3,4,5,6,7]
			 * qq_bind : 0
			 * wx_bind : 0
			 * mobile_bind : 0
			 * member_mobile :
			 * nick_name : 魔鬼
			 * created_at : 2018-05-02 12:29:57
			 * updated_at : 2018-06-25 17:54:18
			 * password : $2y$10$c6JrNXgKXKnaKSU0pHZQJ.f6D5DEGBqKcjYnV08f4V4It1QO9NNk2
			 * remember_token :
			 * wy_acid : 10148
			 * wy_token : 4aa100f21fa6b3529c3db71d344f87c3
			 * member_avatar : /storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
			 * member_area :
			 * listen_state : 1
			 * visitor_num : 14
			 * is_online : 1
			 * receive : 0
			 * member_cate_id : 49
			 * member_sound : http://116.62.217.183/storage/sound/2018/06/05/VIolfUsBMiNvZUpIxpdnaPuSJiohoSywUTqhewAl.
			 * member_is_true : 0
			 * member_true_sex : 1
			 * member_true_name : 贾名
			 * member_id_num : 410726199904264586
			 * member_id_front : http://116.62.217.183/storage/image/20180530/15276430507116.jpg
			 * member_id_back : http://116.62.217.183/storage/image/20180530/15276430765379.jpg
			 * is_vip : 0
			 * member_info_is_post : 1
			 * talk_time : 21
			 * order_sort : 0
			 * lat : 34.801765
			 * lng : 113.611325
			 * icon : http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
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
			private List<String> member_tag;
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

			public List<String> getMember_tag() {
				return member_tag;
			}

			public void setMember_tag(List<String> member_tag) {
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

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}
		}

		public static class FUserBean {
			/**
			 * id : 10182
			 * member_name : 13333826947
			 * member_age : 18
			 * member_province :
			 * member_city :
			 * member_sex : 2
			 * member_state : 0
			 * wx_openid :
			 * qq_openid :
			 * level : 0
			 * member_price : 3
			 * member_account : 1.6
			 * member_fronze : 0
			 * vip_start_time : 0
			 * vip_end_time : 0
			 * flow_num : 1
			 * fans_num : 1
			 * zan_num : 0
			 * member_signature :
			 * member_img_1 : /storage/user/20180623/10182_img_1.jpg
			 * member_img_2 : /storage/user/20180623/10182_img_2.jpg
			 * member_img_3 : /storage/user/20180623/10182_img_3.jpg
			 * member_img_4 :
			 * birthday : 2000-08-23
			 * member_tag :
			 * qq_bind : 0
			 * wx_bind : 0
			 * mobile_bind : 0
			 * member_mobile :
			 * nick_name : 在一起
			 * created_at : 2018-06-21 16:57:53
			 * updated_at : 2018-06-25 15:14:10
			 * password : $2y$10$j6OXAZDQQ0vZwBl9faZUeOubo7lEMLy9YHLSqUEldSlhaFUQDecwK
			 * remember_token :
			 * wy_acid : 10182
			 * wy_token : 8ca89606b93920fe830369a8e21dd274
			 * member_avatar : /storage/avatar/2018/06/21/avatar_1529571492_10182.jpg
			 * member_area :
			 * listen_state : 1
			 * visitor_num : 1
			 * is_online : 1
			 * receive : 0
			 * member_cate_id : 46
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
			 * lat : 34.801765
			 * lng : 113.611325
			 * icon : http://116.62.217.183/storage/avatar/2018/06/21/avatar_1529571492_10182.jpg
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
			private List<String> member_tag;
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

			public List<String> getMember_tag() {
				return member_tag;
			}

			public void setMember_tag(List<String> member_tag) {
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

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}
		}
	}
}
