package com.ysxsoft.qxerkai.net;

import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;
import com.ysxsoft.qxerkai.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ttt.qx.qxcall.constant.CommonConstant.COMMON_BASE_URL;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;

public class RetrofitTools {
    public static RetrofitTools instance;

    public static RetrofitTools getInstance() {
        if (instance == null) {
            synchronized (RetrofitTools.class) {
                instance = new RetrofitTools();
            }
        }
        return instance;
    }

    public static Retrofit.Builder getBuilder() {
        return new Retrofit.Builder()
                .baseUrl(COMMON_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new LogInterceptor())
                        .build());
    }

    public static RetrofitApi getManager() {
        return getBuilder().build().create(RetrofitApi.class);
    }

    public static RetrofitApi getManager(String url) {
        return getInstance().getBuilder().baseUrl(url).build().create(RetrofitApi.class);
    }

    /**
     * OkHttp过滤器
     */
    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtils.e("RequestTool-->  request:" + request.toString());
            okhttp3.Response response = chain.proceed(request);
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.e("RequestTool-->  response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 网络请求 基础Method
    ///////////////////////////////////////////////////////////////////////////
    public static <T> Observable<T> subscribe(Observable<T> responseObservable) {
        return responseObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 请求体builder  上传文件
     *
     * @param map
     * @param imageNames
     * @param imageFile
     * @return
     */
    public static MultipartBody.Builder builder(Map<String, String> map, String[] imageNames, File[] imageFile) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (map != null) {
            Set<String> keys = map.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addFormDataPart(key, map.get(key));
            }
        }
        if (imageNames != null && imageFile != null && imageNames.length == imageFile.length) {
            int length = imageNames.length;
            for (int i = 0; i < length; i++) {
                builder.addFormDataPart(imageNames[i], imageFile[i].getName(), RequestBody.create(MediaType.parse("image/*"), imageFile[i]));
            }
        } else {
            throw new IllegalArgumentException("The param imageNames is null");
        }
        return builder;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 网络请求 Api
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 撒狗粮列表
     *
     * @param map
     * @return
     */
    public static Observable<SaGouLiangListResponse> getSaGouLiangList(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getSaGouLiangList(map));
    }

    /**
     * 撒狗粮点赞
     *
     * @param map
     * @return
     */
    public static Observable<SaGouLiangLikeResponse> likeSaGouLiang(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().likeSaGouLiang(map));
    }

    /**
     * 关于我们/我的收益规则/萨狗粮规则/萨狗粮奖品/使用说明/vip特权
     *
     * @param map
     * @return
     */
    public static Observable<RuleResponse> getRule(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getRule(map));
    }

    /**
     * 发布撒狗粮
     *
     * @param map
     * @return
     */
    public static Observable<SaGouLiangPublishResponse> publishSaGouLiang(Map<String, String> map, String[] imageNames, File[] imageFiles) {
        return subscribe(RetrofitTools.getManager().publishSaGouLiang(COMMON_BASE_URL+SA_GOU_LIANG_COMMIT, builder(map, imageNames, imageFiles).build()));
    }

    /**
     * 对某人进行守护
     *
     * @param map
     * @return
     */
    public static Observable<BaseResponse> getGuardsing(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getGuardsing(map));
    }

    /**
     * 用户的守护列表/用户守护的列表
     *
     * @param map
     * @return
     */
    public static Observable<GuardsListResponse> getGuardsingList(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getGuardsingList(map));
    }

    /**
     * 小情趣列表
     *
     * @param map
     * @return
     */
    public static Observable<GetCardListResponse> getCardList(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getCardList(map));
    }

    /**
     * 小情趣帖子详情
     *
     * @param map
     * @return
     */
    public static Observable<GetCardDetailResponse> getCardDetail(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getCardDetail(map));
    }

    /**
     * 小情趣帖子评论
     *
     * @param map
     * @return
     */
    public static Observable<BaseResponse> submitCardComment(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().submitCardComment(map));
    }

    /**
     * 小情趣 公告
     *
     * @param map
     * @return
     */
    public static Observable<GetNoticeListResponse> getNoticeList(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().getNoticeList(map));
    }

    /**
     * 小情趣点赞
     *
     * @param map
     * @return
     */
    public static Observable<BaseResponse> cardLike(Map<String, String> map) {
        return subscribe(RetrofitTools.getManager().cardLike(map));
    }
}
