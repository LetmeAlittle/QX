package com.ysxsoft.qxerkai.net.response;

import java.util.List;

public class GetLuYinListResponse  extends BaseResponse{


	/**
	 * data : {"current_page":1,"data":[{"id":12,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530010856,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":17,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":11,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530009896,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":125,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":10,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530009895,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":2655,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":9,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530009894,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":545,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":8,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":2,"addtime":1530009893,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":11,"zanNum":2,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":7,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":2,"addtime":1530009892,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":1234,"zanNum":1,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":1,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":2,"addtime":1530009685,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":23,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"}],"first_page_url":"http://116.62.217.183/api/luyinlist?page=1","from":1,"last_page":1,"last_page_url":"http://116.62.217.183/api/luyinlist?page=1","next_page_url":null,"path":"http://116.62.217.183/api/luyinlist","per_page":15,"prev_page_url":null,"to":7,"total":7}
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
		 * data : [{"id":12,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530010856,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":17,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":11,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530009896,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":125,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":10,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530009895,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":2655,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":9,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":1,"addtime":1530009894,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":545,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":8,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":2,"addtime":1530009893,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":11,"zanNum":2,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":7,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":2,"addtime":1530009892,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":1234,"zanNum":1,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"},{"id":1,"file":"http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac","tag_id":2,"addtime":1530009685,"money":6,"uid":10182,"tid":"12321321","del":0,"sc":23,"zanNum":0,"user_id":10148,"uname":"魔","uicon":"http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg","f_user_id":10158,"fname":"雪儿","ficon":"http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg"}]
		 * first_page_url : http://116.62.217.183/api/luyinlist?page=1
		 * from : 1
		 * last_page : 1
		 * last_page_url : http://116.62.217.183/api/luyinlist?page=1
		 * next_page_url : null
		 * path : http://116.62.217.183/api/luyinlist
		 * per_page : 15
		 * prev_page_url : null
		 * to : 7
		 * total : 7
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
			 * id : 12
			 * file : http://116.62.217.183/storage/sound/2017/12/17/193719194177793_mix.aac
			 * tag_id : 1
			 * addtime : 1530010856
			 * money : 6
			 * uid : 10182
			 * tid : 12321321
			 * del : 0
			 * sc : 17
			 * zanNum : 0
			 * user_id : 10148
			 * uname : 魔
			 * uicon : http://116.62.217.183/storage/avatar/2018/06/02/avatar_1527910701_10148.jpg
			 * f_user_id : 10158
			 * fname : 雪儿
			 * ficon : http://116.62.217.183/storage/avatar/2018/05/09/avatar_1525825589_10158.jpg
			 */

			private int id;
			private String file;
			private int tag_id;
			private int addtime;
			private int money;
			private int uid;
			private String tid;
			private int del;
			private int sc;
			private int zanNum;
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

			public String getFile() {
				return file;
			}

			public void setFile(String file) {
				this.file = file;
			}

			public int getTag_id() {
				return tag_id;
			}

			public void setTag_id(int tag_id) {
				this.tag_id = tag_id;
			}

			public int getAddtime() {
				return addtime;
			}

			public void setAddtime(int addtime) {
				this.addtime = addtime;
			}

			public int getMoney() {
				return money;
			}

			public void setMoney(int money) {
				this.money = money;
			}

			public int getUid() {
				return uid;
			}

			public void setUid(int uid) {
				this.uid = uid;
			}

			public String getTid() {
				return tid;
			}

			public void setTid(String tid) {
				this.tid = tid;
			}

			public int getDel() {
				return del;
			}

			public void setDel(int del) {
				this.del = del;
			}

			public int getSc() {
				return sc;
			}

			public void setSc(int sc) {
				this.sc = sc;
			}

			public int getZanNum() {
				return zanNum;
			}

			public void setZanNum(int zanNum) {
				this.zanNum = zanNum;
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

