package com.ttt.qx.qxcall.function.base.helper;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ysxsoft.qxerkai.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
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
        OkHttpClient okHttpClient = okHttpClientBuilder.addInterceptor(new LogInterceptor()).build();

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
     * OkHttp过滤器
     */
    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtils.e("RequestHelper-->  request:" + request.toString());
            okhttp3.Response response = chain.proceed(request);
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.e("RequestHelper-->  response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
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
