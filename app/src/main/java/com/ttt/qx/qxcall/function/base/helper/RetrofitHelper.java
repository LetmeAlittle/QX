package com.ttt.qx.qxcall.function.base.helper;


import com.ttt.qx.qxcall.constant.CommonConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit网络配置工具类
 * 主要用于获取Retrofit对象
 * Created by wyd on 2017/1/13.
 */

public class RetrofitHelper {
    //retrofit静态变量
    private static Retrofit retrofit;
    //retrofitHelper静态变量
    private static RetrofitHelper retrofitHelper;

    /**
     * 私有化构造
     *
     * @param baseUrl
     */
    private RetrofitHelper(String baseUrl) {
        //创建okHttpClient对象以及设置网络超时
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(CommonConstant.TIME_OUT, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        // TODO: 2017/7/18  缓存机制处理
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        //创建Retrofit对象
        retrofit = retrofitBuilder
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * 同步代码块 获取RetrofitHelper实例对象
     *@param baseUrl
     * @return
     */
    public static RetrofitHelper getRetrofitHelper(String baseUrl) {
        if (retrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                //双重枷锁，获取实例对象
                retrofitHelper = new RetrofitHelper(baseUrl);
            }
        }
        return retrofitHelper;
    }

    /**
     * 返回Retrofit对象实例
     *
     * @return
     */
    public synchronized Retrofit getRetrofit() {
        return retrofit;
    }
}
