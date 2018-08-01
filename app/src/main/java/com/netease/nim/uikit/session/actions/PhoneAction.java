package com.netease.nim.uikit.session.actions;

import android.app.Activity;

import com.netease.nim.uikit.R;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.RechargeActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.utils.IntentUtil;

import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.onToast;

/**
 * Created by 王亚东 on 2017/10/4.
 */

public class PhoneAction extends BaseAction {
	private int callType = 0;//0普通  1抛话题  2角色扮演
	private int gid = 0;//抛话题id
	private String ppid ="";//
	private boolean isAdmin = false;//是否是发起人

	public PhoneAction() {
		super(R.drawable.nim_message_plus_phone_selector, R.string.input_panel_phone);
	}

	public void setCallType(int callType) {
		this.callType = callType;
	}

	public void setPPid(String ppid) {
		this.ppid = ppid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public void onClick() {
		Activity activity = getActivity();
		UserDao userDao = new UserDao();
		UserBean userBean = userDao.queryFirstData();
		if (userBean != null) {
			//获取当前用户账号 调用语音通话
			String accid = getAccount();
			String authorization = "Bearer " + userBean.getToken();
			HomeModel.getHomeModel().isBackFans(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
				@Override
				public void onNext(StandardResponse standardResponse) {
					if (standardResponse.getStatus_code() == 200) {
						if (standardResponse.getData().getIs_black() == 0) {

							StealListenModel.getStealListenModel().isAllowTalk(new Subscriber<StandardResponse>() {
								@Override
								public void onCompleted() {
								}

								@Override
								public void onError(Throwable e) {
								}

								@Override
								public void onNext(StandardResponse standardResponse) {
									if (standardResponse.getStatus_code() == 200) {
										//调起拨打界面。
										AVChatProfile.getInstance().setAVChatting(true);
										AVChatActivity.launch(activity, callType, gid,ppid, accid, AVChatType.AUDIO.getValue(), isAdmin, AVChatActivity.FROM_INTERNAL);
									} else {
										//费用不足 弹出是否充值对话框
										TipDialog.showCenterTipDialog(activity, "当前剩余钻石不足,是否前去充值？", new TipDialog.OnComponentClickListener() {
											@Override
											public void onCancle() {
												//用户取消操作
											}

											@Override
											public void onConfirm() {
												//用户点击确定执行 相关逻辑
												IntentUtil.jumpIntent(activity, RechargeActivity.class);
											}
										}, true);
									}
								}
							}, accid, authorization);
						} else {
							onToast("您已被对方拉黑！");
						}
					}
				}
			}, activity), String.valueOf(accid), authorization);
		} else {
			IntentUtil.jumpIntent(activity, LoginTransferActivity.class);
		}
	}
}
