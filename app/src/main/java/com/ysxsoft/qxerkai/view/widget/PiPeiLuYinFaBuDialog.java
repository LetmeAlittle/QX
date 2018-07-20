package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.SystemUtils;

/**
 * 一键匹配  发布录音弹窗 Sincerly
 */
public class PiPeiLuYinFaBuDialog extends Dialog {
	private Context context;
	private OnPiPeiTipsDialogListener listener;
	private int totalTime;

	public PiPeiLuYinFaBuDialog(@NonNull Context context, int themeResId) {
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
		View view = View.inflate(context, R.layout.dialog_pi_pei_fabu, null);
		TextView sure = (TextView) view.findViewById(R.id.sure);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		TextView time = (TextView) view.findViewById(R.id.time);

		time.setText(String.valueOf(totalTime) + "s");//录音时间
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
				if (listener != null) {
					listener.fabu();
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

	public void init(int totalTime) {
		this.totalTime = totalTime;
	}

	private void closeDialog() {
		dismiss();
	}

	public interface OnPiPeiTipsDialogListener {
		void fabu();
	}
}

