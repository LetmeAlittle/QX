package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;

public class UpdateDialog extends Dialog implements View.OnClickListener {
	private Context context;
	private OnUpdateListener listener;
	private String t;
	private String c;

	public UpdateDialog(@NonNull Context context, int themeResId) {
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
		View view = View.inflate(context, R.layout.dialog_update, null);
		TextView sure = (TextView) view.findViewById(R.id.sure);
		ImageView cancel = (ImageView) view.findViewById(R.id.cancel);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView content = (TextView) view.findViewById(R.id.content);
		title.setText(t==null?"发现新版本":t);
		content.setText(c==null?"":c);

		sure.setOnClickListener(this);
		cancel.setOnClickListener(this);
		return view;
	}

	public void show(OnUpdateListener listener) {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sure://开始升级
				if (isShowing()) {
					dismiss();
				}
				if (listener != null) {
					listener.start();
				}
				break;
			case R.id.cancel://取消
				if (isShowing()) {
					dismiss();
				}
				break;
		}
	}

	public UpdateDialog setTitle(String t) {
		this.t = t;
		return this;
	}

	public UpdateDialog setContent(String content) {
		this.c = content;
		return this;
	}

	public interface OnUpdateListener {
		void start();
	}
}
