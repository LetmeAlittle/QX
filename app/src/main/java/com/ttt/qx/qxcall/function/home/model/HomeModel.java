package com.ttt.qx.qxcall.function.home.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.home.model.api.HomeServiceApi;
import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.home.model.entity.FlowVisitorInfoList;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
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

public class HomeModel implements BaseModel {
    //保存当前类的静态实例对象
    private static HomeModel homeModel;
    //ServiceApi接口对象
    private static HomeServiceApi homeServiceApi;
    private Subscription subscription;

    //私有构造函数
    private HomeModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        homeServiceApi = retrofit.create(HomeServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static HomeModel getHomeModel() {
        if (homeModel == null) {
            synchronized (HomeModel.class) {
                homeModel = new HomeModel();
            }
        }
        return homeModel;
    }

    /**
     * 获取用户信息
     * Authorization
     *
     * @param subscriber
     * @param id
     * @param Authorization
     */
    public void getUserInfo(Subscriber<UserDetailInfo> subscriber, String id, String Authorization) {
        Observable<UserDetailInfo> observable = homeServiceApi.getUserInfo(id, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 判断某个用户的拉黑状态
     * Authorization
     *
     * @param subscriber
     * @param member_id
     * @param Authorization
     */
    public void isBackFans(Subscriber<StandardResponse> subscriber, String member_id, String Authorization) {
        Observable<StandardResponse> observable = homeServiceApi.isBackFans(member_id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 拉黑某个用户
     * Authorization
     *
     * @param subscriber
     * @param member_id
     * @param Authorization
     */
    public void blackFans(Subscriber<StandardResponse> subscriber, String member_id, String Authorization) {
        Observable<StandardResponse> observable = homeServiceApi.blackFans(member_id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 关注用户
     * Authorization
     *
     * @param subscriber
     * @param id
     * @param Authorization
     */
    public void followUser(Subscriber<StandardResponse> subscriber, String id, String Authorization) {
        Observable<StandardResponse> observable = homeServiceApi.followUser(id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 取消关注用户
     * Authorization
     *
     * @param subscriber
     * @param id
     * @param Authorization
     */
    public void unfollowUser(Subscriber<StandardResponse> subscriber, String id, String Authorization) {
        Observable<StandardResponse> observable = homeServiceApi.unfollowUser(id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 用户关注列表
     * Authorization
     *
     * @param subscriber
     * @param member_id     传递此值的话 ，返回所传递的ID 对应用户的粉丝信息，否则的话默认返回当前登录用户的关注信息
     * @param Authorization
     */
    public void getUserFollowList(Subscriber<FlowVisitorInfoList> subscriber, String member_id, String page, String Authorization) {
        Observable<FlowVisitorInfoList> observable = homeServiceApi.getUserFollowList(member_id, page, Authorization);
        onToSubscribe889(observable, subscriber);
    }

    /**
     * 用户粉丝列表
     * Authorization
     *
     * @param subscriber
     * @param member_id     传递此值的话 ，返回所传递的ID 对应用户的粉丝信息，否则的话默认返回当前登录用户的关注信息
     * @param Authorization
     */
    public void getUserFansList(Subscriber<FlowVisitorInfoList> subscriber, String member_id, String page, String Authorization) {
        Observable<FlowVisitorInfoList> observable = homeServiceApi.getUserFansList(member_id, page, Authorization);
        onToSubscribe889(observable, subscriber);
    }

    /**
     * 用户访客列表
     * Authorization
     *
     * @param subscriber
     * @param member_id     传递此值的话 ，返回所传递的ID 对应用户的粉丝信息，否则的话默认返回当前登录用户的关注信息
     * @param Authorization
     */
    public void getUserVisitorList(Subscriber<FlowVisitorInfoList> subscriber, String member_id, String page, String Authorization) {
        Observable<FlowVisitorInfoList> observable = homeServiceApi.getUserVisitorList(member_id, page, Authorization);
        onToSubscribe889(observable, subscriber);
    }

    /**
     * 获取首页用户列表
     *
     * @param subscriber
     * @param tag
     */
    public void getUserList(Subscriber<UserListInfo> subscriber, String tag, String sex, String page, String Authorization) {
        Observable<UserListInfo> observable = homeServiceApi.getUserList("1",tag, sex,page, Authorization);
        onToSubscribe90(observable, subscriber);
    }


    /**
     * 根据性别 或者 id号 检索数据
     *
     * @param subscriber
     * @param id
     * @param sex
     */
    public void filterUserList(Subscriber<UserListInfo> subscriber, String id, String sex, String page) {
        Observable<UserListInfo> observable = homeServiceApi.filterUserList(id, sex, page);
        onToSubscribe90(observable, subscriber);
    }


    /**
     * 获取首页 标签分类列表
     *
     * @param subscriber
     */
    public void getHomeTagList(Subscriber<CommonTagList> subscriber) {
        Observable<CommonTagList> observable = homeServiceApi.getHomeTagList();
        onToSubscribe91(observable, subscriber);
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
    private void onToSubscribe88(Observable<UserDetailInfo> observable, Subscriber<UserDetailInfo> subscriber) {
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
    private void onToSubscribe90(Observable<UserListInfo> observable, Subscriber<UserListInfo> subscriber) {
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
    private void onToSubscribe91(Observable<CommonTagList> observable, Subscriber<CommonTagList> subscriber) {
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
    private void onToSubscribe889(Observable<FlowVisitorInfoList> observable, Subscriber<FlowVisitorInfoList> subscriber) {
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
