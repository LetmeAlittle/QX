package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GouLiangTop  extends BaseResponse{

	/**
	 * data : {"current_page":1,"data":[{"cnt":12,"is_vip":0,"user_id":10213,"nick_name":"李铭阳","icon":"http://116.62.217.183/storage/avatar/2018/08/01/avatar_1533104792_10213.png"},{"cnt":6,"is_vip":0,"user_id":10148,"nick_name":"。","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"cnt":5,"is_vip":0,"user_id":10149,"nick_name":"比尔","icon":"http://116.62.217.183/storage/avatar/2018/07/25/avatar_1532487725_10149.png"},{"cnt":4,"is_vip":0,"user_id":10134,"nick_name":"App开发郝","icon":"http://116.62.217.183/storage/avatar/2018/08/01/avatar_1533104424_10134.png"},{"cnt":4,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/24/avatar_1532415615_10195.png"},{"cnt":2,"is_vip":null,"user_id":null,"nick_name":null,"icon":null},{"cnt":2,"is_vip":0,"user_id":10201,"nick_name":"噜啦噜啦 嘞~?","icon":"http://116.62.217.183/storage/avatar/2018/07/28/avatar_1532790668_10201.png"}],"first_page_url":"http://116.62.217.183/api/dogrank?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/dogrank?page=1","next_page_url":null,"path":"http://116.62.217.183/api/dogrank","per_page":10,"prev_page_url":null,"to":7,"total":7}
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
		 * data : [{"cnt":12,"is_vip":0,"user_id":10213,"nick_name":"李铭阳","icon":"http://116.62.217.183/storage/avatar/2018/08/01/avatar_1533104792_10213.png"},{"cnt":6,"is_vip":0,"user_id":10148,"nick_name":"。","icon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg"},{"cnt":5,"is_vip":0,"user_id":10149,"nick_name":"比尔","icon":"http://116.62.217.183/storage/avatar/2018/07/25/avatar_1532487725_10149.png"},{"cnt":4,"is_vip":0,"user_id":10134,"nick_name":"App开发郝","icon":"http://116.62.217.183/storage/avatar/2018/08/01/avatar_1533104424_10134.png"},{"cnt":4,"is_vip":0,"user_id":10195,"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/24/avatar_1532415615_10195.png"},{"cnt":2,"is_vip":null,"user_id":null,"nick_name":null,"icon":null},{"cnt":2,"is_vip":0,"user_id":10201,"nick_name":"噜啦噜啦 嘞~?","icon":"http://116.62.217.183/storage/avatar/2018/07/28/avatar_1532790668_10201.png"}]
		 * first_page_url : http://116.62.217.183/api/dogrank?page=1
		 * from : 1
		 * last_page : 1
		 * last_page_url : http://116.62.217.183/api/dogrank?page=1
		 * next_page_url : null
		 * path : http://116.62.217.183/api/dogrank
		 * per_page : 10
		 * prev_page_url : null
		 * to : 7
		 * total : 7
		 */

		private int current_page;
		private String first_page_url;
		private int from;
		private int last_page;
		private String last_page_url;
		private String next_page_url;
		private String path;
		private int per_page;
		private String prev_page_url;
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

		public String getPrev_page_url() {
			return prev_page_url;
		}

		public void setPrev_page_url(String prev_page_url) {
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
			 * cnt : 12
			 * is_vip : 0
			 * user_id : 10213
			 * nick_name : 李铭阳
			 * icon : http://116.62.217.183/storage/avatar/2018/08/01/avatar_1533104792_10213.png
			 */

			private int cnt;
			private int is_vip;
			private int user_id;
			private String nick_name;
			private String icon;

			public int getCnt() {
				return cnt;
			}

			public void setCnt(int cnt) {
				this.cnt = cnt;
			}

			public int getIs_vip() {
				return is_vip;
			}

			public void setIs_vip(int is_vip) {
				this.is_vip = is_vip;
			}

			public int getUser_id() {
				return user_id;
			}

			public void setUser_id(int user_id) {
				this.user_id = user_id;
			}

			public String getNick_name() {
				return nick_name;
			}

			public void setNick_name(String nick_name) {
				this.nick_name = nick_name;
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
