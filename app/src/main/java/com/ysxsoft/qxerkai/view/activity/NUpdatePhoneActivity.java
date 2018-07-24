package com.ysxsoft.qxerkai.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.GetCodeTimerUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NUpdatePhoneActivity extends NBaseActivity {


	private static final int UPDATE = 0x01;
	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.iv_public_titlebar_left_1)
	ImageView ivPublicTitlebarLeft1;
	@BindView(R.id.tv_public_titlebar_left)
	TextView tvPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_left_2)
	ImageView ivPublicTitlebarLeft2;
	@BindView(R.id.ll_public_titlebar_left)
	LinearLayout llPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_right_1)
	ImageView ivPublicTitlebarRight1;
	@BindView(R.id.tv_public_titlebar_right)
	TextView tvPublicTitlebarRight;
	@BindView(R.id.iv_public_titlebar_right_2)
	ImageView ivPublicTitlebarRight2;
	@BindView(R.id.ll_public_titlebar_right)
	LinearLayout llPublicTitlebarRight;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.c)
	TextView c;
	@BindView(R.id.ll_public_titlebar)
	LinearLayout llPublicTitlebar;
	@BindView(R.id.phone)
	EditText phone;
	@BindView(R.id.ll_update_pwd)
	LinearLayout llUpdatePwd;
	@BindView(R.id.code)
	EditText code;
	@BindView(R.id.ll_update_pwd2)
	LinearLayout llUpdatePwd2;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	GetCodeTimerUtils utils;
	private int s = 0;
	private boolean isRunning = false;

	public static void start(Context context) {
		Intent intent = new Intent(context, NUpdatePhoneActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_phone);
		ButterKnife.bind(this);
		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
		utils = GetCodeTimerUtils.getInstance();
	}

	private void initTitleBar() {
		ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
		ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
		llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		tvPublicTitlebarCenter.setText("修改手机号");
	}

	public void onSubmit(View view) {
		updatePhone();
	}

	public void onGetCode(View view) {
		if (!isRunning) {
			utils.initDelayTime(60);
			utils.initStepTime(1);
			utils.setOnGetCodeListener(new GetCodeTimerUtils.OnGetCodeListener() {
				@Override
				public void onRunning(int totalTime) {
					c.setText(totalTime + "s后可重新获取");
					isRunning = true;
				}

				@Override
				public void onFinish() {
					c.setText("重新获取");
					isRunning = false;
				}
			});
			utils.startDelay();

			getCode();
		} else {
			ToastUtils.showToast(this, "请稍后再试！", 1);
		}
	}

	/**
	 * 获取短信验证码
	 */
	private void getCode() {
		Map<String, String> map = new HashMap<>();
		map.put("phone", phone.getText().toString());
		RetrofitTools.getCode(map)
				.subscribe(new ResponseSubscriber<BaseResponse>() {
					@Override
					public void onSuccess(BaseResponse ruleResponse, int code, String msg) {
						if (code == 200) {
						} else {
							ToastUtil.showToast(NUpdatePhoneActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	/**
	 * 修改手机号
	 */
	private void updatePhone() {
		if ("".equals(phone.getText().toString())) {
			ToastUtils.showToast(this, "请输入手机号", 1);
			return;
		}
		if ("".equals(code.getText().toString())) {
			ToastUtils.showToast(this, "请输入验证码", 1);
			return;
		}
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());
		map.put("phone", phone.getText().toString());
		map.put("code", code.getText().toString());
		RetrofitTools.updatePhone(map)
				.subscribe(new ResponseSubscriber<BaseResponse>() {
					@Override
					public void onSuccess(BaseResponse ruleResponse, int code, String msg) {
						if (code == 200) {
							finish();
						} else {
							ToastUtil.showToast(NUpdatePhoneActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {

					}
				});
	}
}
