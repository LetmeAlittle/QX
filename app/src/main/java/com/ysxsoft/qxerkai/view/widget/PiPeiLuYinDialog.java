package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.SystemUtils;

/**
 * 房间录音弹窗 Sincerly
 */
public class PiPeiLuYinDialog extends Dialog {
	private Context context;
	private OnPiPeiTipsDialogListener listener;
	private int totalTime;

	public PiPeiLuYinDialog(@NonNull Context context, int themeResId) {
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
	private Chronometer time;
	private TextView hint;
	private boolean isStarted=false;//第一次自动运行

	private View initView() {
		View view = View.inflate(context, R.layout.dialog_lu_yin, null);
		TextView sure = (TextView) view.findViewById(R.id.sure);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		hint = (TextView) view.findViewById(R.id.hint);
		time = (Chronometer) view.findViewById(R.id.time);
		hint.setText(String.valueOf(totalTime) + "s");//录音时间
		hint.setText("开始录音");
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
				if(isStarted){
					//结束录音
					if (listener != null) {
						listener.end();
					}
				}else{
					isStarted=true;
					listener.start();
					hint.setText("结束录音");
					time.setBase(SystemClock.elapsedRealtime());
					time.start();
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
		void start();
		void end();
	}
}

