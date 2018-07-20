package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetTouTingListResponse extends BaseResponse{


	/**
	 * data : {"current_page":1,"data":[{"id":12321321,"uid":10148,"fuid":10158,"addtime":1530008307,"endtime":1730008547,"flag":0,"num":0,"suo":1,"suotime":1530008672,"ttnum":8,"sc":62514,"user_id":10148,"uname":"魔鬼","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"}],"first_page_url":"http://116.62.217.183/api/touting?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/touting?page=1","next_page_url":null,"path":"http://116.62.217.183/api/touting","per_page":15,"prev_page_url":null,"to":1,"total":1}
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
		 * data : [{"id":12321321,"uid":10148,"fuid":10158,"addtime":1530008307,"endtime":1730008547,"flag":0,"num":0,"suo":1,"suotime":1530008672,"ttnum":8,"sc":62514,"user_id":10148,"uname":"魔鬼","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"}]
		 * first_page_url : http://116.62.217.183/api/touting?page=1
		 * from : 1
		 * last_page : 1
		 * last_page_url : http://116.62.217.183/api/touting?page=1
		 * next_page_url : null
		 * path : http://116.62.217.183/api/touting
		 * per_page : 15
		 * prev_page_url : null
		 * to : 1
		 * total : 1
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
			 * id : 12321321
			 * uid : 10148
			 * fuid : 10158
			 * addtime : 1530008307
			 * endtime : 1730008547
			 * flag : 0
			 * num : 0
			 * suo : 1
			 * suotime : 1530008672
			 * ttnum : 8
			 * sc : 62514
			 * user_id : 10148
			 * uname : 魔鬼
			 * uicon : http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
			 * f_user_id : 10158
			 * fname : 雪儿
			 * ficon : http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg
			 */

			private int id;
			private int uid;
			private int fuid;
			private int addtime;
			private int endtime;
			private int flag;
			private int num;
			private int suo;
			private int suotime;
			private int ttnum;
			private int sc;
			private int user_id;
			private String uname;
			private String uicon;
			private int f_user_id;
			private String fname;
			private String ficon;

			public int getId() {
				return id;
			}

			public void setId(int id) {
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

			public int getSuotime() {
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

			public int getSc() {
				return sc;
			}

			public void setSc(int sc) {
				this.sc = sc;
			}

			public int getUser_id() {
				return user_id;
			}

			public void setUser_id(int user_id) {
				this.user_id = user_id;
			}

			public String getUname() {
				return uname;
			}

			public void setUname(String uname) {
				this.uname = uname;
			}

			public String getUicon() {
				return uicon;
			}

			public void setUicon(String uicon) {
				this.uicon = uicon;
			}

			public int getF_user_id() {
				return f_user_id;
			}

			public void setF_user_id(int f_user_id) {
				this.f_user_id = f_user_id;
			}

			public String getFname() {
				return fname;
			}

			public void setFname(String fname) {
				this.fname = fname;
			}

			public String getFicon() {
				return ficon;
			}

			public void setFicon(String ficon) {
				this.ficon = ficon;
			}
		}
	}
}
