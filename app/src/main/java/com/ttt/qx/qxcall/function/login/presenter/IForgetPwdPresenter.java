package com.ttt.qx.qxcall.function.login.presenter;

import com.ttt.qx.qxcall.function.base.interfacee.BasePresenter;

/**
 * Created by wyd on 2017/7/28.
 */

public interface IForgetPwdPresenter extends BasePresenter {
    /**
     * 忘记密码
     * @param phone
     * @param code
     * @param pwd
     * @param againPwd
     */
    void forgetPwd(String phone, String code, String pwd, String againPwd);

    /**
     * 点击获取验证码
     * @param phone
     */
    void onGetIdentifyCode(String phone);
}
