package com.ttt.qx.qxcall.function.login.presenter;

import android.content.Context;

import com.ttt.qx.qxcall.constant.LoginConstant;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.login.view.ILoginView;

/**
 * 登录业务逻辑接口实现类
 * Created by wyd on 2017/7/19.
 */

public class LoginPresenterImpl implements ILoginPresenter {
    //上下文
    private final Context context;
    //视图逻辑接口对象
    private final ILoginView iLoginView;
    //UserModel对象
    private final LoginModel mLoginModel;

    public LoginPresenterImpl(Context context, ILoginView iLoginView) {
        this.context = context;
        this.iLoginView = iLoginView;
        mLoginModel = LoginModel.getLoginModel();
    }

    @Override
    public void onLogin(String account, String pwd, Integer type) {
        if (type == LoginConstant.LOGIN) {
            mLoginModel.login(new ProgressSubscribe<>(new SubScribeOnNextListener<LoginedResponse>() {
                @Override
                public void onNext(LoginedResponse loginedResponse) {

                }
            }, context), account, pwd);
        } else if (type == LoginConstant.REGISTER) {
//            mLoginModel.login(new Subscriber<User>() {
//                @Override
//                public void onCompleted() {
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                }
//
//                @Override
//                public void onNext(User user) {
//                    //请求之后处理
////                    loginAfterHandle(user);
//                }
//            }, account, pwd);
        } else {
        }
    }

//    /**
//     * 登录接口请求之后处理
//     *
//     * @param user
//     */
//    private void loginAfterHandle(User user) {
//        if (user.getStatus().equals("200")) {
//            if (user.isIsSuccess()) {
//                iLoginView.onLoginSuccess(user);
//            } else {
//                errorMessageShow(user);
//            }
//        } else {
//            errorMessageShow(user);
//        }
//    }
//
//    private void errorMessageShow(User user) {
//        Object message = user.getErrorMessage();
//        if (message != null) {
//            iLoginView.onToast(message.toString());
//        }
//    }
}
