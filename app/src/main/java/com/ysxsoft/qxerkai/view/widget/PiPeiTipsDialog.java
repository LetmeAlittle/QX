package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Subscriber;

/**
 * 一键匹配刚进去弹窗 Sincerly
 */
public class PiPeiTipsDialog extends Dialog {
	private Context context;
	private OnPiPeiTipsDialogListener listener;

	public PiPeiTipsDialog(@NonNull Context context, int themeResId) {
		super(context, themeResId);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		setCanceledOnTouchOutside(true);
		setContentView(initView());
	}

	private View initView() {
		View view = View.inflate(context, R.layout.dialog_pi_pei_tips, null);
		TextView sure = (TextView) view.findViewById(R.id.sure);
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
				listener.sure();
			}
		});
		return view;
	}

	public void show(OnPiPeiTipsDialogListener listener) {
		this.listener = listener;
		if (!isShowing()) {
			show();
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.width = (int) SystemUtils.getScreenWidth(context) * 6 / 7;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			getWindow().setAttributes(lp);
			getWindow().setGravity(Gravity.CENTER);
		}
	}

	private void closeDialog() {
		dismiss();
	}

	public interface OnPiPeiTipsDialogListener {
		void sure();
	}
}

