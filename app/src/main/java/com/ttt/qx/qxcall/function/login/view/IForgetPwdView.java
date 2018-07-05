package com.ttt.qx.qxcall.function.login.view;


import com.ttt.qx.qxcall.function.base.interfacee.BaseView;

/**
 * 忘记密码视图
 * Created by wyd on 2017/7/28.
 */

public interface IForgetPwdView extends BaseView {
    /**
     * 获取验证码
     *
     */
    void onGetIdentifyCodeCompleted();

}
