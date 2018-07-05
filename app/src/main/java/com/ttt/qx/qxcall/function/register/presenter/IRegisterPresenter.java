package com.ttt.qx.qxcall.function.register.presenter;


import com.ttt.qx.qxcall.function.base.interfacee.BasePresenter;

/**
 * 注册业务逻辑接口
 * Created by wyd on 2017/7/27.
 */

public interface IRegisterPresenter extends BasePresenter {
    /**
     * 获取验证码
     *
     * @param phone
     */
    void onGetIdentifyCode(String phone);

    /**
     * 用户注册
     *
     * @param phone
     * @param code
     * @param pwd
     */
    void onRegister(String phone, String code, String pwd);
}
