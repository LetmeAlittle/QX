package com.netease.nim.uikit.session.actions;

import android.app.Activity;
import android.app.Application;

import com.netease.nim.uikit.R;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.GiftSendDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.IntentUtil;

import static com.ttt.qx.qxcall.QXCallApplication.onToast;

/**
 * Created by 王亚东 on 2017/10/4.
 */

public class GiftAction extends BaseAction {
    public GiftAction() {
        super(R.drawable.nim_message_plus_gift_selector, R.string.input_panel_gift);
    }

    @Override
    public void onClick() {
        //礼物发送
        Activity activity = getActivity();
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        Application application = QXCallApplication.getApplication();
        if (userBean != null) {
            //获取当前用户账号 调用语音通话
            String accid = getAccount();
            String authorization = "Bearer " + userBean.getToken();
            FindModel.getFindModel().getGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftList>() {
                @Override
                public void onNext(GiftList giftList) {
                    if (giftList.getStatus_code() == 200) {
                        GiftSendDialog.showBottomDialog(activity, giftList.getData(), new GiftSendDialog.OnComponentClickListener() {
                            @Override
                            public void onCancle() {
                            }

                            @Override
                            public void onSend(String gift_id) {
                                FindModel.getFindModel().sendCallGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                    @Override
                                    public void onNext(StandardResponse response) {
                                        if (response.getStatus_code() == 200) {
                                            onToast("赠送成功！");
                                        } else {
                                            onToast(response.getMessage());
                                        }
                                    }
                                }, activity), gift_id,accid, authorization);
                            }
                        });
                    } else {
                        onToast(giftList.getMessage());
                    }
                }
            }, activity));
        } else {
            IntentUtil.jumpIntent(activity, LoginTransferActivity.class);
        }
    }
}
