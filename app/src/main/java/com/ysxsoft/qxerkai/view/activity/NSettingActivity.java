package com.ysxsoft.qxerkai.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.eventbus.ExitLogin;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitApi;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.CheckVersionResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.UpdatePwdResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DownloadUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.UpdateDialog;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NSettingActivity extends NBaseActivity implements View.OnClickListener {

	private static final String TAG = "NSettingActivity";
	@BindView(R.id.status_bar)
	View statusBar;
	@BindView(R.id.iv_public_titlebar_left_1)
	ImageView ivPublicTitlebarLeft1;
	@BindView(R.id.ll_public_titlebar_left)
	LinearLayout llPublicTitlebarLeft;
	@BindView(R.id.tv_public_titlebar_center)
	TextView tvPublicTitlebarCenter;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;
	@BindView(R.id.ll_zhanghuanquan)
	LinearLayout llZhanghuanquan;
	@BindView(R.id.ll_yijianfankui)
	LinearLayout llYijianfankui;
	@BindView(R.id.ll_jianchagengxin)
	LinearLayout llJianchagengxin;
	@BindView(R.id.ll_qinglihuancun)
	LinearLayout llQinglihuancun;
	@BindView(R.id.ll_shiyongshuoming)
	LinearLayout llShiyongshuoming;
	@BindView(R.id.ll_guanyuwomen)
	LinearLayout llGuanyuwomen;
	private Dialog mDialog;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nsetting);
		ButterKnife.bind(this);
		sp =getSharedPreferences(CommonConstant.APP_SP_CONFIG, Context.MODE_PRIVATE);

		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
		initView();

		if(sp.getBoolean("isPlatform",false)){
			llZhanghuanquan.setVisibility(View.GONE);
		}else{
			llZhanghuanquan.setVisibility(View.VISIBLE);
		}
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
		tvPublicTitlebarCenter.setText("设置");
	}

	private void initView() {
		llZhanghuanquan.setOnClickListener(this);
		llYijianfankui.setOnClickListener(this);
		llJianchagengxin.setOnClickListener(this);
		llQinglihuancun.setOnClickListener(this);
		llShiyongshuoming.setOnClickListener(this);
		llGuanyuwomen.setOnClickListener(this);
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	public void onExit(View view) {
		EventBus.getDefault().post(new ExitLogin());
		finish();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			//账户安全
			case R.id.ll_zhanghuanquan:
				startActivity(new Intent(this, NAccountSafeActivity.class));
				break;
			//意见反馈
			case R.id.ll_yijianfankui:
				startActivity(new Intent(this, NFeedBackActivity.class));
				break;
			//检查更新
			case R.id.ll_jianchagengxin:
				DownloadUtils.checkVersion(this);
				break;
			//清理缓存
			case R.id.ll_qinglihuancun:
				showDialog();
				break;
			//使用说明
			case R.id.ll_shiyongshuoming:
				parseRule("5");
				break;
			//关于我们
			case R.id.ll_guanyuwomen:
				parseRule("1");
				break;
		}
	}


	/**
	 * 网页 1关于我们  5使用说明
	 *
	 * @param aid
	 */
	private void parseRule(String aid) {
		Map<String, String> map = new HashMap<>();
		map.put("aid", aid);
		RetrofitTools.getRule(map)
				.subscribe(new ResponseSubscriber<RuleResponse>() {
					@Override
					public void onSuccess(RuleResponse ruleResponse, int code, String msg) {
						if (code == 200) {
//                            ToastUtil.showToast(SaGouLiangActivity.this, msg);
							BaseWebViewActivity.startWithContent(NSettingActivity.this, ruleResponse.getData(), parseTitle(aid));
						} else {
							ToastUtil.showToast(NSettingActivity.this, msg);
						}
					}

					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

	private String parseTitle(String aid) {
		String result = "";
		switch (aid) {
			case "1":
				result = "关于我们";
				break;
			case "5":
				result = "使用说明";
				break;
			default:
				break;
		}
		return result;
	}

	private void showDialog() {
		if (mDialog == null) {
			mDialog = CustomAlertDialogUtil.createLoadingDialog(this, "清除中...", false);
		}
		if (mDialog.isShowing()) {
			return;
		}
		if (!mDialog.isShowing()) {
			mDialog.show();
		}

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				closeDialog();
			}
		}, 3000);
	}

	private void closeDialog() {
		if (mDialog != null && mDialog.isShowing() && !isFinishing()) {
			mDialog.dismiss();
		}
	}


}
