package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.ReplaySubject;

public class NFeedBackActivity extends NBaseActivity {

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
	@BindView(R.id.tv_public_titlebar_left)
	TextView tvPublicTitlebarLeft;
	@BindView(R.id.iv_public_titlebar_left_2)
	ImageView ivPublicTitlebarLeft2;
	@BindView(R.id.iv_public_titlebar_right_1)
	ImageView ivPublicTitlebarRight1;
	@BindView(R.id.tv_public_titlebar_right)
	TextView tvPublicTitlebarRight;
	@BindView(R.id.iv_public_titlebar_right_2)
	ImageView ivPublicTitlebarRight2;
	@BindView(R.id.ll_public_titlebar_right)
	LinearLayout llPublicTitlebarRight;
	@BindView(R.id.ll_public_titlebar)
	LinearLayout llPublicTitlebar;
	@BindView(R.id.content)
	EditText content;
	@BindView(R.id.phone)
	EditText phone;
	@BindView(R.id.submit)
	TextView submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfeed_back);
		ButterKnife.bind(this);
		initStatusBar();
		initStatusBar(statusBar);
		initTitleBar();
		initView();
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
		tvPublicTitlebarCenter.setText("意见反馈");
	}

	private void initView() {
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});
	}

	/**
	 * 发布
	 */
	private void submit() {
		if ("".equals(content.getText().toString())) {
			ToastUtils.showToast(NFeedBackActivity.this, "请输入反馈内容", 1);
			return;
		}
		if ("".equals(phone.getText().toString())) {
			ToastUtils.showToast(NFeedBackActivity.this, "请填写您的联系方式", 1);
			return;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", phone.getText().toString());
		map.put("content", content.getText().toString());
		RetrofitTools.feedBack(map).subscribe(new ResponseSubscriber<BaseResponse>() {
			@Override
			public void onSuccess(BaseResponse baseResponse, int code, String msg) {
				if (code == 200) {
					finish();
				} else {
					ToastUtils.showToast(NFeedBackActivity.this, msg, 1);
				}
			}

			@Override
			public void onFailed(Throwable e) {
			}
		});
	}
}
