package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;

/**
 * 一键匹配  上锁 Sincerly
 */
public class PiPeiSuoDialog extends Dialog {
	private Context context;
	private OnPiPeiSuoDialogListener listener;
	private boolean isFirstLock;//true第一道锁  false 第二道锁
	private String money;

	public PiPeiSuoDialog(@NonNull Context context, int themeResId) {
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
		View view = View.inflate(context, R.layout.dialog_pi_pei_suo, null);
		TextView sure = (TextView) view.findViewById(R.id.lock);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		TextView content= (TextView) view.findViewById(R.id.content1);
		TextView content2= (TextView) view.findViewById(R.id.content2);
		TextView m= (TextView) view.findViewById(R.id.money);
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
				if (listener != null) {
					listener.lock();
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
			}
		});
//		1.加锁   加锁后别人不可进入  房主可以邀请他人   价格   立即加锁
//		2.上锁   上锁后只能进行偷听，不能进行录音。    价格    立即上锁
		if(isFirstLock){//第一道锁
			content.setText("加锁后别人不可进入");
			content2.setText("房主可以邀请他人");
			m.setText(StringUtils.convert(money)+"砰砰豆");
			sure.setText("立即加锁");
		}else{//第二道锁
			content.setText("上锁后只能进行偷听");
			content2.setText("不能进行录音");
			m.setText(StringUtils.convert(money)+"钻");
			sure.setText("立即上锁");
		}
		return view;
	}

	public void show(OnPiPeiSuoDialogListener listener) {
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

	/**
	 * 设置上锁类型  切换dialog文本
	 * @param isFirstLock
	 */
	public void setType(boolean isFirstLock) {
		this.isFirstLock = isFirstLock;
	}

	public void setMoney(String money){
		this.money=money;
	}

	private void closeDialog() {
		dismiss();
	}

	public interface OnPiPeiSuoDialogListener {
		void lock();
	}
}

