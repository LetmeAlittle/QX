package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;

/**
 * 偷听解锁  Sincerly
 */
public class TouTingSuoDialog extends Dialog {
	private Context context;
	private OnTouTingSuoDialogListener listener;
	private String money;

	public TouTingSuoDialog(@NonNull Context context, int themeResId) {
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
		View view = View.inflate(context, R.layout.dialog_tou_ting_suo, null);
		TextView sure = (TextView) view.findViewById(R.id.lock);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		TextView content= (TextView) view.findViewById(R.id.content);
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
				if (listener != null) {
					listener.sure();
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
		content.setText("解锁需要支付"+StringUtils.convert(money)+"砰砰豆哦~");
		return view;
	}

	public void show(OnTouTingSuoDialogListener listener) {
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

	public void setMoney(String money){
		this.money=money;
	}

	private void closeDialog() {
		dismiss();
	}

	public interface OnTouTingSuoDialogListener {
		void sure();
	}
}

