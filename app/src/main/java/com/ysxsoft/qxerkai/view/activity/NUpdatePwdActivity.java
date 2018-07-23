package com.ysxsoft.qxerkai.view.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.eventbus.ExitLogin;
import com.ttt.qx.qxcall.eventbus.SetSelectItem;
import com.ttt.qx.qxcall.function.login.view.LoginActivity;
import com.ttt.qx.qxcall.utils.AppActivityManager;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.UpdatePwdResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ttt.qx.qxcall.QXCallApplication.login;
import static com.ttt.qx.qxcall.QXCallApplication.onToast;

public class NUpdatePwdActivity extends NBaseActivity {


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
	@BindView(R.id.ll_public_titlebar)
	LinearLayout llPublicTitlebar;
	@BindView(R.id.oldPwd)
	EditText oldPwd;
	@BindView(R.id.ll_update_phone)
	LinearLayout llUpdatePhone;
	@BindView(R.id.newPwd)
	EditText newPwd;
	@BindView(R.id.ll_update_pwd)
	LinearLayout llUpdatePwd;
	@BindView(R.id.newPwd2)
	EditText newPwd2;
	@BindView(R.id.ll_update_pwd2)
	LinearLayout llUpdatePwd2;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;

	public static void start(Context context) {
		Intent intent = new Intent(context, NUpdatePwdActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pwd);
		ButterKnife.bind(this);
		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
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
		tvPublicTitlebarCenter.setText("修改登录密码");
	}

	public void onSubmit(View view) {
		updatePwd();
	}

	/**
	 * 修改密码接口
	 */
	private void updatePwd() {
		if ("".equals(oldPwd.getText().toString())) {
			ToastUtils.showToast(this, "请输入旧密码", 1);
			return;
		}
		if ("".equals(newPwd.getText().toString())) {
			ToastUtils.showToast(this, "请输入新密码", 1);
			return;
		}
		if ("".equals(newPwd2.getText().toString())) {
			ToastUtils.showToast(this, "请再次输入新密码", 1);
			return;
		}
		if (!newPwd.getText().toString().equals(newPwd2.getText().toString())) {
			ToastUtils.showToast(this, "新密码不一致", 1);
			return;
		}
		Map<String, String> map = new HashMap<>();
		map.put("user_id", DBUtils.getUserId());
		map.put("password", oldPwd.getText().toString());
		map.put("newpassword", newPwd.getText().toString());
		RetrofitTools.updatePwd(map)
				.subscribe(new ResponseSubscriber<UpdatePwdResponse>() {
					@Override
					public void onSuccess(UpdatePwdResponse baseResponse, int code, String msg) {
						LogUtils.e("updatepwd onSuccess");
						if (code == 200) {
							def();
							finish();
						} else {
							ToastUtil.showToast(NUpdatePwdActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
//						ToastUtil.showToast(NUpdatePwdActivity.this, "原始密码错误！");
						LogUtils.e("updatepwd onFailed" + e.getMessage());
					}
				});
	}

	private void def() {
//		Intent intent = new Intent(NUpdatePwdActivity.this, NLoginActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
//		finish();
//		AppActivityManager.getInstance().killAllActivity();
//		Intent intent = new Intent(NUpdatePwdActivity.this, NLoginActivity.class);
//		startActivity(intent);
//		finish();
		EventBus.getDefault().post(new ExitLogin());
		finish();
	}

	@Subscribe
	public void onEventExitLogin(ExitLogin exitLogin) {
		UserDao userDao = new UserDao();
		//首先清空数据库表
		userDao.deleteAll();
		login = false;
		onToast("退出成功！");
		//切换到首页
		SetSelectItem setSelectItem = new SetSelectItem();
		setSelectItem.selectPosition = CommonConstant.SELECT_HOME;//上次选择就是首页
		EventBus.getDefault().post(setSelectItem);
	}
}
