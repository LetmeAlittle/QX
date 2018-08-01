package com.ysxsoft.qxerkai.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.voice.AVChatSoundPlayer;
import com.ysxsoft.qxerkai.net.response.PaoHuaTiResponse;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaoHuaTiCallActivity extends AppCompatActivity {
	@BindView(R.id.name)
	TextView name;
	@BindView(R.id.content)
	TextView content;
	@BindView(R.id.accept)
	ImageView accept;
	@BindView(R.id.refuse)
	ImageView refuse;

	private PaoHuaTiResponse response;//通知传过来的参数

	/**
	 * 发起人
	 * @param context
	 * @param response
	 */

	public static void start(Context context, PaoHuaTiResponse response) {
		Intent intent = new Intent(context, PaoHuaTiCallActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("bean", response);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pao_hua_ti_call);
		ButterKnife.bind(this);
		initAudio();
		parseIntent();
	}

	/**
	 * 初始化语音播报
	 */
	private void initAudio() {
		AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
	}

	private void parseIntent() {
		response = (PaoHuaTiResponse) getIntent().getSerializableExtra("bean");
		if(response==null){
			content.setText("");
			return;
		}
		content.setText(StringUtils.convert(response.getMsg()));
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		AVChatSoundPlayer.instance().stop();
	}

	@OnClick({R.id.accept, R.id.refuse})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.accept://接受
//				Context context,String contactId,String icon,String name,String title,int isVip,int num, SessionCustomization customization, IMMessage anchor
				if (response == null) {
					ToastUtils.showToast(this, "话题发布方信息缺失！", 1);
					finish();
					return;
				}
				PaoHuaTiResponse.MemberInfoBean memberInfoBean = response.getMember_info();
				if (memberInfoBean == null) {
					return;
				}
				int contactId =memberInfoBean.getMember_id();//发起人id
				String icon = memberInfoBean.getIcon();//头像
				String name = memberInfoBean.getNick_name();
				String title = memberInfoBean.getTitle();
				int isVip = memberInfoBean.getIs_vip();
				int num = memberInfoBean.getNum();
				NimUIKit.startP2PSessionWithHuaTi(PaoHuaTiCallActivity.this, "" + contactId, icon, name, title, isVip, num, memberInfoBean.getGid(), 1);//抛话题 callType传1  为了区分扣费接口
				finish();
				break;
			case R.id.refuse://拒绝
				ToastUtils.showToast(this, "已拒绝！", 1);
				finish();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AVChatSoundPlayer.instance().stop();
	}
}
