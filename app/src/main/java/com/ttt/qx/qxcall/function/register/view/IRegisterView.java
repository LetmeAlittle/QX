package com.ttt.qx.qxcall.function.register.view;


import com.ttt.qx.qxcall.function.base.interfacee.BaseView;

/**
 * 注册视图逻辑接口
 * Created by wyd on 2017/7/27.
 */

public interface IRegisterView extends BaseView {
    /**
     * 获取验证码成功之后处理
     */
    void onGetIDCodeCompleted();
}
