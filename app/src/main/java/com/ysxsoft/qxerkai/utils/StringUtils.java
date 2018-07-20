package com.ysxsoft.qxerkai.utils;

public class StringUtils {
	public static String convert(String str) {
		if (str == null || "".equals(str) || "null".equals(str)) {
			return "";
		}
		return str;
	}

	/**
	 * 毫秒转   时 分 秒
	 *
	 * @return
	 */
	public static String msToTime(int ms) {
		StringBuilder sb = new StringBuilder();
		int totals = ms / 1000;
		int h,m,s;
		int _h=totals/3600;
		if(_h>0){
			sb.append(_h+"小时");
		}
		int _m=totals%3600/60;
		if(_m>0){
			sb.append(_m+"分钟");
		}

		int _s=totals%3600/60%60;
		if(_m>0){
			sb.append(_s+"秒");
		}
		return sb.toString();
	}

	public static String getS(int s) {
		String str = "";
		int s2 = s % 60;
		if (s2 > 9) {
			str = "" + s2;
		} else {
			str = "0" + s2;
		}
		return str;
	}

	public static String getH(int s) {
		String str = "";
		if (s > 3600) {
			int h = s / 3600;
			if (h > 9) {
				str = "" + h;
			} else {
				str = "0" + h;
			}
		}
		return str;
	}
	public static String getM(int s) {
		String str = "";
		if (s >= 60) {
			int m = s / 60;
			if (m > 9) {
				str = "" + m;
			} else {
				str = "0" + m;
			}
		} else {
			str = "00";
		}
		return str;
	}
}
