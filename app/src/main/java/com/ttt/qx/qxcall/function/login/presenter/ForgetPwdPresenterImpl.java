package com.ttt.qx.qxcall.function.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.ForgetPwdModel;
import com.ttt.qx.qxcall.function.login.view.IForgetPwdView;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;

import rx.Subscriber;

/**
 * 忘记密码业务逻辑
 * Created by wyd on 2017/7/28.
 */

public class ForgetPwdPresenterImpl implements IForgetPwdPresenter {
    private final Context contex;
    private final IForgetPwdView iForgetPwdView;
    private final ForgetPwdModel mForgetPwdModel;
    //应用缓存对象
    public SharedPreferences sp;

    public ForgetPwdPresenterImpl(Context context, IForgetPwdView iForgetPwdView) {
        this.contex = context;
        this.iForgetPwdView = iForgetPwdView;
        mForgetPwdModel = ForgetPwdModel.getForgetPwdModel();
        sp = context.getSharedPreferences(CommonConstant.APP_SP_CONFIG, Context.MODE_PRIVATE);
    }

    @Override
    public void forgetPwd(String phone, String code, String pwd, String aggainPwd) {
        mForgetPwdModel.forgetPwd(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseStatus>() {
            @Override
            public void onNext(ResponseStatus responseStatus) {
                if (responseStatus.getStatus().equals("200")) {
                    if (responseStatus.isIsSuccess()) {
                        //重置成功，直接登录
                        iForgetPwdView.onFinish();
//                        HomeModel.getHomeModel().login(new Subscriber<User>() {// TODO: 2017/7/28 抽时间抽取
//                            @Override
//                            public void onCompleted() {
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                            }
//
//                            @Override
//                            public void onNext(User user) {
//                                if (user.getStatus().equals("200")) {
//                                    if (user.isIsSuccess()) {
////                                        LoginSuccess loginSuccess = new LoginSuccess();
////                                        loginSuccess.mUser = user;
////                                        EventBus.getDefault().post(loginSuccess);
////                                        sp.edit().putString(CommonConstant.ACCOUT, phone).commit();
////                                        sp.edit().putString(CommonConstant.PWD, pwd).commit();
////                                        sp.edit().putString(CommonConstant.EMAIL,
////                                                user.getData().getEmail() != null ? user.getData().getEmail().toString() : "").commit();
////                                        if (!message) {
////                                            AppActivityManager.getInstance().killActivity(LoginTransferActivity.class);
////                                        }
////                                        AppActivityManager.getInstance().killActivity(LoginActivity.class);
//                                    }
//                                } else {
//                                    errorMessageShow(responseStatus);
//                                }
//                            }
//                        }, phone, pwd);
                    } else {
                        errorMessageShow(responseStatus);
                    }
                } else {
                    errorMessageShow(responseStatus);
                }
            }
        }, contex), phone, code, pwd, aggainPwd);
    }

    @Override
    public void onGetIdentifyCode(String phone) {
        mForgetPwdModel.getIdentifyCode(new Subscriber<ResponseStatus>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                iForgetPwdView.onToast("网络异常！");
            }

            @Override
            public void onNext(ResponseStatus responseStatus) {
                if (responseStatus.getStatus().equals("200")) {
                    if (responseStatus.isIsSuccess()) {
                        iForgetPwdView.onToast(responseStatus.getData());
                        //开启 计时器 同时 将获取验证码隐藏 可见重新获取以及秒数倒计时控件
                        iForgetPwdView.onGetIdentifyCodeCompleted();
                    } else {
                        errorMessageShow(responseStatus);
                    }
                } else {
                    errorMessageShow(responseStatus);
                }
            }
        }, phone, 1);
    }

    /**
     * 显示错误信息
     *
     * @param responseStatus
     */
    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            iForgetPwdView.onToast(message.toString());
        }
    }
}
