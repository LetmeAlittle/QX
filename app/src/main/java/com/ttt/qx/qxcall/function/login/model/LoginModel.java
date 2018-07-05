package com.ttt.qx.qxcall.function.login.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.login.model.api.LoginServiceApi;
import com.ttt.qx.qxcall.function.login.model.entity.InvitedRecord;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.login.model.entity.ThreeLoginResponse;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.UserShareInfo;
import com.ttt.qx.qxcall.function.login.model.entity.VIPBuyList;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

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

public class LoginModel implements BaseModel {
    //保存当前类的静态实例对象
    private static LoginModel loginModel;
    //ServiceApi接口对象
    private static LoginServiceApi loginServiceApi;
    private Subscription subscription;

    //私有构造函数
    private LoginModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        loginServiceApi = retrofit.create(LoginServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static LoginModel getLoginModel() {
        if (loginModel == null) {
            synchronized (LoginModel.class) {
                loginModel = new LoginModel();
            }
        }
        return loginModel;
    }

    /**
     * 获取用户信息
     *
     * @param subscriber
     * @param account
     * @param pwd
     */
    public void login(Subscriber<LoginedResponse> subscriber, String account, String pwd) {
        Observable<LoginedResponse> observable = loginServiceApi.postLogin(account, pwd);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 登录奖励
     */
    public void loginReward(Subscriber<StandardResponse> subscriber, String Authorization) {
        Observable<StandardResponse> observable = loginServiceApi.loginReward(Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 上线状态
     */
    public void androidOnline(Subscriber<StandardResponse> subscriber) {
        Observable<StandardResponse> observable = loginServiceApi.androidOnline();
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 举报
     */
    public void userReport(Subscriber<StandardResponse> subscriber, String member_id, String content, String Authorization) {
        Observable<StandardResponse> observable = loginServiceApi.userReport(member_id, content, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 获取用户分享信息
     */
    public void getShareInfo(Subscriber<UserShareInfo> subscriber, String id) {
        Observable<UserShareInfo> observable = loginServiceApi.getShareInfo(id);
        onToSubscribe89(observable, subscriber);
    }

    /**
     * 获取邀请记录
     */
    public void getInvitedRecord(Subscriber<InvitedRecord> subscriber, String id) {
        Observable<InvitedRecord> observable = loginServiceApi.getInvitedRecord(id);
        onToSubscribe890(observable, subscriber);
    }

    /**
     * @param subscriber
     */
    public void buyVipList(Subscriber<VIPBuyList> subscriber) {
        Observable<VIPBuyList> observable = loginServiceApi.buyVipList();
        onToSubscribe888(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param accessToken
     * @param openId
     */
    public void weixinLogin(Subscriber<ThreeLoginResponse> subscriber, String accessToken, String openId) {
        Observable<ThreeLoginResponse> observable = loginServiceApi.postShareWeiXinLogin(openId);
        onToSubscribe38(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param openId
     */
    public void qqLogin(Subscriber<ThreeLoginResponse> subscriber, String openId) {
        Observable<ThreeLoginResponse> observable = loginServiceApi.postShareQQLogin(openId);
        onToSubscribe38(observable, subscriber);
    }

    /**
     * 获取用户信息
     *
     * @param subscriber
     * @param loginSession
     */
    public void checkSession(Subscriber<ResponseStatus> subscriber, String loginSession, String signKey) {
        Observable<ResponseStatus> observable = loginServiceApi.checkSession(loginSession, signKey);
        onToSubscribe2(observable, subscriber);
    }

    /**
     * 交易账户与微财账户的绑定
     *
     * @param subscriber
     * @param userId     微财用户id
     * @param tradeId    马特交易id
     * @param password   交易密码
     * @param signKey
     */
    public void bindMatAccount(Subscriber<ResponseStatus> subscriber, String userId, String tradeId, String password, String signKey) {
        Observable<ResponseStatus> observable = loginServiceApi.bindMatAccount(userId, tradeId, password, signKey);
        onToSubscribe2(observable, subscriber);
    }

    /**
     * 用户超时不操作 5小时 输入交易密码检测
     *
     * @param subscriber
     * @param userId     微财用户id
     * @param tradeId    马特交易id
     * @param password   交易密码
     * @param signKey
     */
    public void checkMatAccount(Subscriber<ResponseStatus> subscriber, String userId, String tradeId, String password, String signKey) {
        Observable<ResponseStatus> observable = loginServiceApi.checkMatAccount(userId, tradeId, password, signKey);
        onToSubscribe2(observable, subscriber);
    }


    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe(Observable<User> observable, Subscriber<User> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe8(Observable<StandardResponse> observable, Subscriber<StandardResponse> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe89(Observable<UserShareInfo> observable, Subscriber<UserShareInfo> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe890(Observable<InvitedRecord> observable, Subscriber<InvitedRecord> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe38(Observable<ThreeLoginResponse> observable, Subscriber<ThreeLoginResponse> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe88(Observable<LoginedResponse> observable, Subscriber<LoginedResponse> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe888(Observable<VIPBuyList> observable, Subscriber<VIPBuyList> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe2(Observable<ResponseStatus> observable, Subscriber<ResponseStatus> subscriber) {
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
