package com.ttt.qx.qxcall.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;


/**
 * 用户网络提示对话框
 * Created by wyd on 2014/4/21.
 */
public class CustomAlertDialogUtil {
	/**
	 * 创建加载对话框
	 * @param context 上下文
	 * @param msg 提示内容
	 * @param enableCancel 是否点击屏幕取消对话框
     * @return
     */
	public static Dialog createLoadingDialog(Context context, String msg, boolean enableCancel) {
		if (context != null) {
			Dialog progressDialog = new Dialog(context, R.style.progress_dialog);
			progressDialog.setContentView(R.layout.dialog);
			progressDialog.setCancelable(enableCancel);
			progressDialog.getWindow().setBackgroundDrawableResource(
					android.R.color.transparent);
			TextView tv = (TextView) progressDialog
					.findViewById(R.id.id_tv_loadingmsg);
			tv.setText(msg);
			return progressDialog;
		}
		return null;
	}
}
