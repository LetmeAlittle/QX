package com.ttt.qx.qxcall.function.login.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.login.model.api.ForgetPwdServiceApi;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用户实体类Model
 * Created by wyd on 2017/7/19.
 */

public class ForgetPwdModel implements BaseModel {
    //保存当前类的静态实例对象
    private static ForgetPwdModel forgetPwdModel;
    //ServiceApi接口对象
    private static ForgetPwdServiceApi forgetPwdServiceApi;
    private Subscription subscription;

    //私有构造函数
    private ForgetPwdModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        forgetPwdServiceApi = retrofit.create(ForgetPwdServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static ForgetPwdModel getForgetPwdModel() {
        if (forgetPwdModel == null) {
            synchronized (ForgetPwdModel.class) {
                forgetPwdModel = new ForgetPwdModel();
            }
        }
        return forgetPwdModel;
    }

    /**
     * 忘记密码
     *
     * @param subscriber
     * @param phone
     * @param phoneCode
     * @param newPassword
     * @param newPasswordAgain
     */
    public void forgetPwd(Subscriber<ResponseStatus> subscriber, String phone, String phoneCode, String newPassword
            , String newPasswordAgain) {
        Observable<ResponseStatus> observable = forgetPwdServiceApi.postForgetPwd(phone, phoneCode, newPassword
                , newPasswordAgain);
        onToSubscribe(observable, subscriber);
    }
    /**
     * 获取验证码
     *
     * @param subscriber
     * @param phone
     * @param type
     */
    public void getIdentifyCode(Subscriber<ResponseStatus> subscriber, String phone, Integer type) {
        Observable<ResponseStatus> observable = forgetPwdServiceApi.postIdentifyCode2(phone, type);
        onToSubscribe(observable, subscriber);
    }
    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe(Observable<ResponseStatus> observable, Subscriber<ResponseStatus> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void onUnSubscribe() {
        //取消订阅
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
