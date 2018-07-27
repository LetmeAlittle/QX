package com.ysxsoft.qxerkai.net.response;

public class PaoHuaTiResponse{
	/**
	 * time : 2018-07-27 18:29
	 * gift_info : {"git_num":1}
	 * msg : Sincerly 进入你的话题【999999999999999999999999999】
	 * member_info : {"nick_name":"Sincerly","icon":"http://116.62.217.183/storage/avatar/2018/07/24/avatar_1532415615_10195.png","sex":1,"num":7,"title":"999999999999999999999999999","is_vip":0,"gid":102,"user_id":10195,"member_id":10208}
	 * msg_type : 13
	 * member_id : 10195
	 */

	private String time;
	private GiftInfoBean gift_info;
	private String msg;
	private MemberInfoBean member_info;
	private int msg_type;
	private int member_id;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public GiftInfoBean getGift_info() {
		return gift_info;
	}

	public void setGift_info(GiftInfoBean gift_info) {
		this.gift_info = gift_info;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public MemberInfoBean getMember_info() {
		return member_info;
	}

	public void setMember_info(MemberInfoBean member_info) {
		this.member_info = member_info;
	}

	public int getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public static class GiftInfoBean {
		/**
		 * git_num : 1
		 */

		private int git_num;

		public int getGit_num() {
			return git_num;
		}

		public void setGit_num(int git_num) {
			this.git_num = git_num;
		}
	}

	public static class MemberInfoBean {
		/**
		 * nick_name : Sincerly
		 * icon : http://116.62.217.183/storage/avatar/2018/07/24/avatar_1532415615_10195.png
		 * sex : 1
		 * num : 7
		 * title : 999999999999999999999999999
		 * is_vip : 0
		 * gid : 102
		 * user_id : 10195
		 * member_id : 10208
		 */

		private String nick_name;
		private String icon;
		private int sex;
		private int num;
		private String title;
		private int is_vip;
		private int gid;
		private int user_id;
		private int member_id;

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

		public int getSex() {
			return sex;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getIs_vip() {
			return is_vip;
		}

		public void setIs_vip(int is_vip) {
			this.is_vip = is_vip;
		}

		public int getGid() {
			return gid;
		}

		public void setGid(int gid) {
			this.gid = gid;
		}

		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		public int getMember_id() {
			return member_id;
		}

		public void setMember_id(int member_id) {
			this.member_id = member_id;
		}
	}
}
