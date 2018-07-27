package com.ysxsoft.qxerkai.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.session.activity.P2PMessageActivity;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.voice.AVChatSoundPlayer;

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

	public static void start(Context context, List<String> members, String role, String teamId, String story, String teamName, String userIcon,int type) {
		//

		Intent intent = new Intent(context, PaoHuaTiCallActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("role", role);//0代表左边  1代表右边
		intent.putExtra("teamId", teamId);//发起人id
		intent.putExtra("story", story);////故事类型 0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书
		intent.putExtra("teamName", teamName);//发起人名称
		intent.putStringArrayListExtra("members", (ArrayList<String>) members);//类型

		intent.putExtra("userIcon", userIcon);//发起人用户头像
		intent.putExtra("type",type);
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
//		//角色扮演
//		role = getIntent().getStringExtra("role");//0代表左边  1代表右边
//		story = getIntent().getStringExtra("story");//故事类型 0:教师VS学生 1:亲王VS宠妃 2:护士VS病人 3:大叔VS萝莉 4:空姐VS乘客 5:老板VS秘书
//		userId2 = getIntent().getStringExtra("teamId");//发起人id
//		teamName = getIntent().getStringExtra("teamName");//发起人名称
//		userIcon = getIntent().getStringExtra("userIcon");//发起人头像
		content.setText("邀请你进行话题探讨");
//		name.setText(StringUtils.convert(teamName));
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
//				P2PMessageActivity.startByHuaTi2();
				break;
			case R.id.refuse://拒绝
				break;
		}
	}
}
