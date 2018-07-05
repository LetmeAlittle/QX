package com.ttt.qx.qxcall.function.login.model;

import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.login.model.api.NickHeadImgServiceApi;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 昵称Model
 * Created by wyd on 2017/7/19.
 */

public class NickHeadImgModel implements BaseModel {
    //保存当前类的静态实例对象
    private static NickHeadImgModel nickHeadImgModel;
    //ServiceApi接口对象
    private static NickHeadImgServiceApi nickHeadImgServiceApi;
    private Subscription subscription;

    //私有构造函数
    private NickHeadImgModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        nickHeadImgServiceApi = retrofit.create(NickHeadImgServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static NickHeadImgModel getNickHeadImgModel() {
        if (nickHeadImgModel == null) {
            synchronized (NickHeadImgModel.class) {
                nickHeadImgModel = new NickHeadImgModel();
            }
        }
        return nickHeadImgModel;
    }

    /**
     * 更改昵称
     *
     * @param subscriber
     * @param headImg    修改昵称也需要 需传任意不为空的字符串
     * @param nickName
     * @param type       0 修改头像 1 修改昵称
     */
    public void changeNickHeadImg(Subscriber<ResponseStatus> subscriber, String headImg, String nickName, String type, String singKey) {
        Observable<ResponseStatus> observable = nickHeadImgServiceApi.postNickHeadImg(headImg, nickName, type,singKey);
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
