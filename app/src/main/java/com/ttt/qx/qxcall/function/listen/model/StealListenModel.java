package com.ttt.qx.qxcall.function.listen.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.listen.model.api.StealListenServiceApi;
import com.ttt.qx.qxcall.function.listen.model.entity.RandomStealListen;
import com.ttt.qx.qxcall.function.listen.model.entity.StealDetailResponse;
import com.ttt.qx.qxcall.function.listen.model.entity.StealListenList;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse3;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 偷听Model
 * Created by wyd on 2017/7/19.
 */

public class StealListenModel implements BaseModel {
    //保存当前类的静态实例对象
    private static StealListenModel stealListenModel;
    //ServiceApi接口对象
    private static StealListenServiceApi stealListenServiceApi;
    private Subscription subscription;

    //私有构造函数
    private StealListenModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        stealListenServiceApi = retrofit.create(StealListenServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static StealListenModel getStealListenModel() {
        if (stealListenModel == null) {
            synchronized (StealListenModel.class) {
                stealListenModel = new StealListenModel();
            }
        }
        return stealListenModel;
    }


    /**
     * 获取偷听列表
     *
     * @param subscriber
     * @param tag
     * @param page
     */
    public void getStealListenList(Subscriber<StealListenList> subscriber, String tag, String page, String Authorization) {
        Observable<StealListenList> observable = stealListenServiceApi.getStealListenList(tag, page, Authorization);
        onToSubscribe90(observable, subscriber);
    }

    /**
     * 获取随机偷听列表
     *
     * @param subscriber
     */
    public void getRandomStealListenList(Subscriber<RandomStealListen> subscriber, String Authorization) {
        Observable<RandomStealListen> observable = stealListenServiceApi.getRandomStealListenList(Authorization);
        onToSubscribe909(observable, subscriber);
    }

    /**
     * 获取偷听详情
     *
     * @param subscriber
     * @param id
     */
    public void getStealListenDetail(Subscriber<StealDetailResponse> subscriber, String id, String Authorization) {
        Observable<StealDetailResponse> observable = stealListenServiceApi.getStealListenDetail(id, Authorization);
        onToSubscribe(observable, subscriber);
    }

    /**
     * 获取偷听标签分类列表
     *
     * @param subscriber
     */
    public void getStealListenTags(Subscriber<CommonTagList> subscriber) {
        Observable<CommonTagList> observable = stealListenServiceApi.getStealListenTags();
        onToSubscribe1(observable, subscriber);
    }

    /**
     * 偷听扣费
     *
     * @param subscriber
     * @param id
     */
    public void stealListenDeduction(Subscriber<StandardResponse> subscriber, String id, String Authorization) {
        Observable<StandardResponse> observable = stealListenServiceApi.stealListenDeduction(id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 通话扣费
     *
     * @param subscriber
     * @param member_id
     */
    public void callDeduction(Subscriber<StandardResponse> subscriber, String member_id, String Authorization) {
        Observable<StandardResponse> observable = stealListenServiceApi.callDeduction(member_id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 通话扣费第二个
     *
     * @param subscriber
     * @param member_id
     */
    public void callDeduction2(Subscriber<StandardResponse3> subscriber, String member_id, String Authorization) {
        Observable<StandardResponse3> observable = stealListenServiceApi.callDeduction2(member_id, Authorization);
        onToSubscribe9(observable, subscriber);
    }

    /**
     * 通话允许判断
     *
     * @param subscriber
     * @param member_id
     */
    public void isAllowTalk(Subscriber<StandardResponse> subscriber, String member_id, String Authorization) {
        Observable<StandardResponse> observable = stealListenServiceApi.isAllowTalk(member_id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 偷听点赞
     *
     * @param subscriber
     * @param id
     */
    public void giveThumbs(Subscriber<StandardResponse> subscriber, String id, String Authorization) {
        Observable<StandardResponse> observable = stealListenServiceApi.giveThumbs(id, Authorization);
        onToSubscribe8(observable, subscriber);
    }


    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe(Observable<StealDetailResponse> observable, Subscriber<StealDetailResponse> subscriber) {
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
    private void onToSubscribe1(Observable<CommonTagList> observable, Subscriber<CommonTagList> subscriber) {
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
    private void onToSubscribe90(Observable<StealListenList> observable, Subscriber<StealListenList> subscriber) {
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
    private void onToSubscribe909(Observable<RandomStealListen> observable, Subscriber<RandomStealListen> subscriber) {
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
    private void onToSubscribe9(Observable<StandardResponse3> observable, Subscriber<StandardResponse3> subscriber) {
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
