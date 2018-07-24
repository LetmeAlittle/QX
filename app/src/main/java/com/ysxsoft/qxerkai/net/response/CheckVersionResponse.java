package com.ysxsoft.qxerkai.net.response;

public class CheckVersionResponse  extends BaseResponse{
	/**
	 * data : {"id":1,"bbsm":"Android","verCode":1,"version":"1.0.0","fileAbsolutePath":"","content":"系统升级，优化界面效果、修复部分BU"}
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
		 * id : 1
		 * bbsm : Android
		 * verCode : 1
		 * version : 1.0.0
		 * fileAbsolutePath :
		 * content : 系统升级，优化界面效果、修复部分BU
		 */

		private int id;
		private String bbsm;
		private int verCode;
		private String version;
		private String fileAbsolutePath;
		private String content;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getBbsm() {
			return bbsm;
		}

		public void setBbsm(String bbsm) {
			this.bbsm = bbsm;
		}

		public int getVerCode() {
			return verCode;
		}

		public void setVerCode(int verCode) {
			this.verCode = verCode;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getFileAbsolutePath() {
			return fileAbsolutePath;
		}

		public void setFileAbsolutePath(String fileAbsolutePath) {
			this.fileAbsolutePath = fileAbsolutePath;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}
