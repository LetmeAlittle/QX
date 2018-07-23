package com.ysxsoft.qxerkai.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class NAccountSafeActivity extends NBaseActivity {

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
	@BindView(R.id.phone)
	TextView phone;
	@BindView(R.id.ll_update_phone)
	LinearLayout llUpdatePhone;
	@BindView(R.id.ll_update_pwd)
	LinearLayout llUpdatePwd;
	@BindView(R.id.multipleStatusView)
	MultipleStatusView multipleStatusView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_safe);
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
		tvPublicTitlebarCenter.setText("账户安全");
	}

	@OnClick({R.id.ll_update_phone, R.id.ll_update_pwd})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.ll_update_phone://修改手机号
				NUpdatePhoneActivity.start(NAccountSafeActivity.this);
				break;
			case R.id.ll_update_pwd://修改密码
				NUpdatePwdActivity.start(NAccountSafeActivity.this);
				break;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 获取账号手机号
	 */
	private void getUserInfo(){
		String authorization = "Bearer " + DBUtils.getUserToken();
		HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(UserDetailInfo userDetailInfo) {
				if (userDetailInfo.getStatus_code() == 200) {

				}
			}
		}, DBUtils.getUserId(), authorization);
	}
}
