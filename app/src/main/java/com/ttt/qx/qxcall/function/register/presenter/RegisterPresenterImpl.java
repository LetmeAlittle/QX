package com.ttt.qx.qxcall.function.register.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.BindResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseResult;
import com.ttt.qx.qxcall.function.register.view.IRegisterView;

import rx.Subscriber;


/**
 * 注册业务逻辑
 * Created by 王亚东 on 2017/7/27.
 */

public class RegisterPresenterImpl implements IRegisterPresenter {
    private final Context context;
    private final IRegisterView iRegisterView;
    private final RegisterModel mRegisterModel;
    //应用缓存对象
    public SharedPreferences sp;
    private String mDirection;
    private String mLoginSession;

    public RegisterPresenterImpl(Context context, IRegisterView iRegisterView, String mMDirection, String mLoginSession) {
        this.context = context;
        this.iRegisterView = iRegisterView;
        mRegisterModel = RegisterModel.getRegisterModel();
        sp = context.getSharedPreferences(CommonConstant.APP_SP_CONFIG, Context.MODE_PRIVATE);
        this.mDirection = mMDirection;
        this.mLoginSession = mLoginSession;
    }

    @Override
    public void onGetIdentifyCode(String phone) {
        int phoneCodeType;
        if (mDirection.equals("LoginActivity")) {
            phoneCodeType = 0;
        } else {
            phoneCodeType = 2;
        }
        mRegisterModel.getIdentifyCode(new Subscriber<ResponseResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                iRegisterView.onToast("网络异常！");
            }

            @Override
            public void onNext(ResponseResult responseResult) {
                    if (responseResult.isSuccess()) {
                    } else {
                        errorMessageShow(responseResult.getMessage());
                    }
            }
        }, phone);
    }

    private void errorMessageShow(Object message ) {
        if (message != null) {
            iRegisterView.onToast(message.toString());
        }
    }

    @Override
    public void onRegister(String phone, String code, String pwd) {
        if (mDirection.equals("LoginActivity")) {
            //注册
            mRegisterModel.register(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                @Override
                public void onNext(StandardResponse responseResult) {
                    //结束后处理
//                    registerAfterHanlde(responseResult, phone, pwd);
                }
            }, context), phone, code, pwd);
        } else {
            //绑定
            mRegisterModel.bind(new ProgressSubscribe<>(new SubScribeOnNextListener<BindResponseStatus>() {
                @Override
                public void onNext(BindResponseStatus bindResponseStatus) {
                    //结束后处理
                    bindAfterHanlde(bindResponseStatus, phone, pwd);
                }
            }, context), phone, code, pwd, mLoginSession);
        }
    }

    /**
     * 注册后处理
     * @param responseResult
     * @param phone
     * @param pwd
     */
    private void registerAfterHanlde(ResponseResult responseResult, String phone, String pwd) {
            //绑定成功之后 直接登录
            if (responseResult.isSuccess()) {
                iRegisterView.onFinish();
                //调用登录接口
//                login(phone, pwd);
            }
    }

    /**
     * 绑定后处理
     * @param responseStatus
     * @param phone
     * @param pwd
     */
    private void bindAfterHanlde(final BindResponseStatus responseStatus, final String phone, final String pwd) {
        String status = responseStatus.getStatus();
        if (status.equals("200")) {
            //绑定成功之后 直接登录
            if (responseStatus.isIsSuccess()) {
                iRegisterView.onFinish();
                //调用登录接口
//                login(phone, pwd);
            }
        } else {
            errorMessageShow(responseStatus.getErrorMessage());
        }
    }

//    private void login(final String phone, String pwd) {
//        HomeModel.getHomeModel().login(new Subscriber<User>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onNext(User user) {
//                if (user.getStatus().equals("200")) {
//                    if (user.isIsSuccess()) {
//                        LoginSuccess loginSuccess = new LoginSuccess();
//                        loginSuccess.mUser = user;
//                        EventBus.getDefault().post(loginSuccess);
//                        if (!message) {
//                            AppActivityManager.getInstance().killActivity(LoginTransferActivity.class);
//                        }
//                        //注册完毕之后登录
//                        UserDao userDao = new UserDao();
//                        UserBean userBean = userDao.queryFirstData();
//                        if (userBean == null) {//添加
//                            User.DataBean userData = user.getData();
//                            userBean = new UserBean();
//                            userBean.setHeadImg(userData.getHeadImg());
//                            userBean.setNickName(userData.getNickName());
//                            userBean.setMobile(userData.getMobile());
//                            userBean.setCreateOn(userData.getCreateOn());
//                            userBean.setEmail(userData.getEmail() != null ? userData.getEmail().toString() : "");
//                            userBean.setUserId(userData.getId());
//                            userBean.setLoginIP(userData.getLoginIP());
//                            userBean.setLoginName(userData.getLoginName());
//                            userBean.setLoginSession(userData.getLoginSession());
//                            userBean.setLoginTime(userData.getLoginTime());
//                            userBean.setPassportType(userData.getPassportType());
//                            userBean.setPassportUpdateTime(userData.getPassportUpdateTime());
//                            userBean.setRealName(userData.getRealName() != null ? userData.getRealName().toString() : "");
//                            //添加
//                            userDao.add(userBean);
//                        }
//                        login = true;
//                        //根据来自方向 做出不同处理  // TODO: 2017/7/28  来自登录页或者第三方登录
//                        if (mDirection.equals("LoginActivity")) {
//                            AppActivityManager.getInstance().killActivity(LoginActivity.class);
//                            if (userBean != null) {
//                                userBean.setMobile(phone);
//                                userDao.update(userBean);
//                            }
//                        }
//                    }
//                } else {
//                    errorMessageShow(user.getErrorMessage());
//                }
//            }
//        }, phone, pwd);
//    }
}
