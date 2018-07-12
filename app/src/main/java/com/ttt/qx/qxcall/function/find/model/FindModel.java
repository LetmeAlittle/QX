package com.ttt.qx.qxcall.function.find.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.find.model.api.FindServiceApi;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.find.model.entity.GiftRankList;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.listen.model.entity.StealDetailResponse;
import com.ttt.qx.qxcall.function.listen.model.entity.StealListenList;
import com.ttt.qx.qxcall.function.register.model.entity.CommitPyqBgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.Part;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 偷听Model
 * Created by wyd on 2017/7/19.
 */

public class FindModel implements BaseModel {
    //保存当前类的静态实例对象
    private static FindModel findModel;
    //ServiceApi接口对象
    private static FindServiceApi findServiceApi;
    private Subscription subscription;

    //私有构造函数
    private FindModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        findServiceApi = retrofit.create(FindServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static FindModel getFindModel() {
        if (findModel == null) {
            synchronized (FindModel.class) {
                findModel = new FindModel();
            }
        }
        return findModel;
    }


    /**
     * 发表说说
     *
     * @param subscriber
     * @param content
     * @param imgs
     * @param Authorization
     */
    public void publishDynamic(Subscriber<StandardResponse> subscriber, String content, String imgs, String Authorization) {
        Observable<StandardResponse> observable = findServiceApi.publishDynamic(content, imgs, Authorization);
        onToSubscribe8(observable, subscriber);
    }
    /**
     * 发表说说
     *
     * @param subscriber
     * @param flie
     */
    public void commitPyqBg(Subscriber<CommitPyqBgResponse> subscriber,String user_id,MultipartBody.Part flie) {
        Observable<CommitPyqBgResponse> observable = findServiceApi.commitPyqBg(user_id,flie);
        onToSubscribe20(observable, subscriber);
    }

    /**
     * 全部动态
     *
     * @param subscriber
     * @param page
     * @param Authorization
     */
    public void getAllDynamic(Subscriber<DynamicResponse> subscriber, String Authorization, String page,String user_id) {
        Observable<DynamicResponse> observable = findServiceApi.getAllDynamic(Authorization, page, user_id);
        onToSubscribe889(observable, subscriber);
    }

    /**
     * 好友动态
     *
     * @param subscriber
     * @param Authorization
     */
    public void getFriendDynamic(Subscriber<DynamicResponse> subscriber, String Authorization, String page,String user_id) {
        Observable<DynamicResponse> observable = findServiceApi.getFriendDynamic(Authorization, page, user_id);
        onToSubscribe889(observable, subscriber);
    }

    /**
     * 说说点赞
     *
     * @param subscriber
     * @param id            说说id
     * @param Authorization
     */
    public void callDianZan(Subscriber<StandardResponse> subscriber, String id, String Authorization) {
        Observable<StandardResponse> observable = findServiceApi.callDianZan(id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 说说详情
     *
     * @param subscriber
     * @param id            说说id
     * @param Authorization
     */
    public void callDetail(Subscriber<DynamicDetail> subscriber, String id, String Authorization) {
        Observable<DynamicDetail> observable = findServiceApi.callDetail(id, Authorization);
        onToSubscribe834(observable, subscriber);
    }

    /**
     * 说说详情
     * 说说 回复
     *
     * @param say_id        当前说说id
     * @param reply_id      说说的回复者 id 用户id
     * @param content       回复内容
     * @param Authorization
     */
    public void callReplay(Subscriber<StandardResponse> subscriber, String say_id, String reply_id, String content, String Authorization) {
        Observable<StandardResponse> observable = findServiceApi.callReplay(say_id, reply_id, content, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 获取礼物列表
     */
    public void getGiftList(Subscriber<GiftList> subscriber) {
        Observable<GiftList> observable = findServiceApi.getGiftList();
        onToSubscribe9(observable, subscriber);
    }

    /**
     * 获取礼物排行榜
     */
    public void getGiftRankList(Subscriber<GiftRankList> subscriber, String page,String user_id, String authorization) {
        Observable<GiftRankList> observable = findServiceApi.getGiftRankList(page, user_id,authorization);
        onToSubscribe99(observable, subscriber);
    }

    /**
     * 赠送礼物
     */
    public void sendGiftList(Subscriber<StandardResponse> subscriber, String gift_id, String say_id, String Authorization) {
        Observable<StandardResponse> observable = findServiceApi.sendGiftList(gift_id, say_id, Authorization);
        onToSubscribe8(observable, subscriber);
    }


    /**
     *
     */
    public void sendCallGiftList(Subscriber<StandardResponse> subscriber, String gift_id, String member_id, String Authorization) {
        Observable<StandardResponse> observable = findServiceApi.sendCallGiftList(gift_id, member_id, Authorization);
        onToSubscribe8(observable, subscriber);
    }

    /**
     *
     */
    public void sendChatGiftList(Subscriber<StandardResponse> subscriber, String gift_id, String member_id, String Authorization) {
        Observable<StandardResponse> observable = findServiceApi.sendChatGiftList(gift_id, member_id, Authorization);
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
    private void onToSubscribe834(Observable<DynamicDetail> observable, Subscriber<DynamicDetail> subscriber) {
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
    private void onToSubscribe20(Observable<CommitPyqBgResponse> observable, Subscriber<CommitPyqBgResponse> subscriber) {
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
    private void onToSubscribe9(Observable<GiftList> observable, Subscriber<GiftList> subscriber) {
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
    private void onToSubscribe99(Observable<GiftRankList> observable, Subscriber<GiftRankList> subscriber) {
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
    private void onToSubscribe889(Observable<DynamicResponse> observable, Subscriber<DynamicResponse> subscriber) {
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
