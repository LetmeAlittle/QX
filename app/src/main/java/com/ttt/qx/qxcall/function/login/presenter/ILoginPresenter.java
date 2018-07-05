package com.ttt.qx.qxcall.function.login.presenter;

import com.ttt.qx.qxcall.function.base.interfacee.BasePresenter;

/**
 * 登录业务逻辑接口
 * Created by wyd on 2017/7/19.
 */

public interface ILoginPresenter extends BasePresenter {

    /**
     * 登录
     *
     * @param account 账户
     * @param pwd     密码
     * @param type    标记登录逻辑是从哪里调取的 0、代表登录页 1、代表注册2、其他
     */
    void onLogin(String account, String pwd, Integer type);
}
