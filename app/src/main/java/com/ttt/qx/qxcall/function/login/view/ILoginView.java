package com.ttt.qx.qxcall.function.login.view;


import com.ttt.qx.qxcall.function.base.interfacee.BaseView;
import com.ttt.qx.qxcall.function.login.model.entity.User;

/**
 * 登录视图逻辑接口
 * Created by wyd on 2017/7/19.
 */

public interface ILoginView extends BaseView {
    /**
     * 登录成功
     */
    void onLoginSuccess(User user);
}
