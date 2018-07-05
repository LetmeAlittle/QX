package com.ttt.qx.qxcall.function.login.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper2;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.login.model.api.UploadImgServiceApi;
import com.ttt.qx.qxcall.function.login.model.entity.UploadImg;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 上传图片
 * Created by wyd on 2017/7/19.
 */

public class UploadImgModel implements BaseModel {
    //保存当前类的静态实例对象
    private static UploadImgModel uploadImgModel;
    //ServiceApi接口对象
    private static UploadImgServiceApi uploadImgServiceApi;
    private Subscription subscription;

    //私有构造函数
    private UploadImgModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper2.getRetrofitHelper(CommonConstant.COMMON_UPLOAD_IMG_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        uploadImgServiceApi = retrofit.create(UploadImgServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static UploadImgModel getUploadImgModel() {
        if (uploadImgModel == null) {
            synchronized (UploadImgModel.class) {
                uploadImgModel = new UploadImgModel();
            }
        }
        return uploadImgModel;
    }

    /**
     * 更改昵称
     *
     * @param subscriber
     * @param headImg    头像文件对象
     */
    public void uploadHeadImg(Subscriber<UploadImgResponse> subscriber, File headImg, String Authorization) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), headImg);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("name", headImg.getName(), requestBody);
        Observable<UploadImgResponse> observable = uploadImgServiceApi.uploadFile("更改头像", requestBody, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 更改昵称
     *
     * @param headImg 头像文件对象
     */
    public void uploadHeadImg22(File headImg, String Authorization) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), headImg);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("name", headImg.getName(), requestBody);
        Call<Response> call = uploadImgServiceApi.uploadFile22("更改头像", requestBody, Authorization);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                String string = response.body().toString();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    /**
     * 用户上传图片操作 上传一张
     *
     * @param subscriber
     * @param
     */
    public void uploadImg1(Subscriber<UploadImgResponse> subscriber, String description, File img1, String Authorization) {
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), img1);
        Observable<UploadImgResponse> observable = uploadImgServiceApi.uploadFile(description, requestBody1, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 用户上传图片操作 上传两张
     *
     * @param subscriber
     * @param
     */
    public void uploadImg2(Subscriber<UploadImg> subscriber, String description, File img1, File img2) {
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), img1);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), img2);
        Observable<UploadImg> observable = uploadImgServiceApi.uploadFile2(description, requestBody1, requestBody2);
        onToSubscribe(observable, subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe(Observable<UploadImg> observable, Subscriber<UploadImg> subscriber) {
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
    private void onToSubscribe88(Observable<UploadImgResponse> observable, Subscriber<UploadImgResponse> subscriber) {
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
