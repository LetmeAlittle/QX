package com.ttt.qx.qxcall.function.register.model;

import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.register.model.api.RegisterServiceApi;
import com.ttt.qx.qxcall.function.register.model.entity.BindResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseResult;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UserInfoSave;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 用户注册Model
 * Created by wyd on 2017/7/19.
 */

public class RegisterModel implements BaseModel {
    //保存当前类的静态实例对象
    private static RegisterModel registerModel;
    //RegisterServiceApi接口对象
    private static RegisterServiceApi registerServiceApi;
    private Subscription subscription;

    //私有构造函数
    private RegisterModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取UserServiceApi接口对象
        registerServiceApi = retrofit.create(RegisterServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static RegisterModel getRegisterModel() {
        if (registerModel == null) {
            synchronized (RegisterModel.class) {
                registerModel = new RegisterModel();
            }
        }
        return registerModel;
    }

    /**
     * 获取验证码
     *
     * @param subscriber
     * @param mobile
     */
    public void getIdentifyCode(Subscriber<ResponseResult> subscriber, String mobile) {
        Observable<ResponseResult> observable = registerServiceApi.postIdentifyCode(mobile, mobile);
        onToSubscribe8(observable, subscriber);
    }

    /**
     * 注册
     *
     * @param phone
     * @param phoneCode
     * @param pwd
     */
    public void register(Subscriber<StandardResponse> subscriber, String phone, String phoneCode, String pwd) {
        Observable<StandardResponse> observable = registerServiceApi.postRegister(phone, phone, phoneCode, pwd
                , pwd);//默认确定两次输入密码一致，由移动端逻辑判断
        onToSubscribe(observable, subscriber);
    }

    /**
     * 上传头像
     *
     * @param avatar
     */
    public void uploadHeadImg(Subscriber<UploadImgResponse> subscriber, String avatar, String Authorization) {
        Observable<UploadImgResponse> observable = registerServiceApi.uploadHeadImg(avatar, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 上传用户相册 照片
     *
     * @param img
     * @param field
     */
    public void uploadUserMainPic(Subscriber<UploadImgResponse> subscriber, String img, String field, String Authorization) {
        Observable<UploadImgResponse> observable = registerServiceApi.uploadUserMainPic(img, field, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 应用图片通用上传接口
     *
     * @param img
     */
    public void commonUpload(Subscriber<UploadImgResponse> subscriber, String img) {
        Observable<UploadImgResponse> observable = registerServiceApi.commonUpload(img);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 删除用户相册 某张照片
     *
     * @param field
     */
    public void delUserMainPic(Subscriber<StandardResponse> subscriber, String field, String Authorization) {
        Observable<StandardResponse> observable = registerServiceApi.delUserMainPic(field, Authorization);
        onToSubscribe(observable, subscriber);
    }

    /**
     * 上传头像
     *
     * @param nick_name
     * @param sex 0 未知 1男 2 女
     * @param age       选填
     */
    public void infoSave(Subscriber<UserInfoSave> subscriber, String nick_name, String sex, String age, String Authorization) {
        Observable<UserInfoSave> observable = registerServiceApi.infoSave(nick_name, sex, age, Authorization);
        onToSubscribe99(observable, subscriber);
    }

    /**
     * 重置密码
     *
     * @param phone
     * @param phoneCode
     * @param pwd
     */
    public void resetPwd(Subscriber<StandardResponse> subscriber, String phone, String phoneCode, String pwd) {
        Observable<StandardResponse> observable = registerServiceApi.postResetPwd(phone, phoneCode, pwd
                , pwd, phone);//默认确定两次输入密码一致，由移动端逻辑判断
        onToSubscribe(observable, subscriber);
    }

    /**
     * 绑定
     *
     * @param phone
     * @param phoneCode
     * @param pwd
     */
    public void bind(Subscriber<BindResponseStatus> subscriber, String phone, String phoneCode, String pwd, String signKey) {
        Observable<BindResponseStatus> observable = registerServiceApi.postBind(phone, phoneCode, pwd, signKey);
        onToSubscribe2(observable, subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe(Observable<StandardResponse> observable, Subscriber<StandardResponse> subscriber) {
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
    private void onToSubscribe99(Observable<UserInfoSave> observable, Subscriber<UserInfoSave> subscriber) {
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
    private void onToSubscribe88(Observable<UploadImgResponse> observable, Subscriber<UploadImgResponse> subscriber) {
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
    private void onToSubscribe8(Observable<ResponseResult> observable, Subscriber<ResponseResult> subscriber) {
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
    private void onToSubscribe2(Observable<BindResponseStatus> observable, Subscriber<BindResponseStatus> subscriber) {
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
