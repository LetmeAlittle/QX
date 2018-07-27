package com.ttt.qx.qxcall.function.listen.model.api;


import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.listen.model.entity.RandomStealListen;
import com.ttt.qx.qxcall.function.listen.model.entity.StealDetailResponse;
import com.ttt.qx.qxcall.function.listen.model.entity.StealListenList;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse3;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 网络请求服务接口
 * Created by wyd on 2017/10/15.
 */

public interface StealListenServiceApi {

    /**
     * 获取偷听列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/listen/list")
    Observable<StealListenList> getStealListenList(@Field("tag") String tag, @Field("page") String page, @Header("Authorization") String Authorization);

    /**
     * 获取随机偷听数据
     *
     * @return
     */
    @POST("api/listen/change")
    Observable<RandomStealListen> getRandomStealListenList(@Header("Authorization") String Authorization);

    /**
     * 获取偷听详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/listen/info")
    Observable<StealDetailResponse> getStealListenDetail(@Field("id") String id, @Header("Authorization") String Authorization);

    /**
     * 偷听标签列表
     *
     * @return
     */
    @POST("api/listen/tag")
    Observable<CommonTagList> getStealListenTags();

    /**
     * 偷听扣费
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/listen/listen")
    Observable<StandardResponse> stealListenDeduction(@Field("id") String id
            , @Header("Authorization") String Authorization);

    /**
     * 通话扣费
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/talk")
    Observable<StandardResponse> callDeduction(@Field("member_id") String member_id
            , @Header("Authorization") String Authorization);


    /**
     * 通话扣费2
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/talk")
    Observable<StandardResponse3> callDeduction2(@Field("member_id") String member_id
            , @Header("Authorization") String Authorization);

    /**
     * 通话允许
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/is_alow_talk")
    Observable<StandardResponse> isAllowTalk(@Field("member_id") String member_id
            , @Header("Authorization") String Authorization);

    /**
     * 偷听列表点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/listen/zan")
    Observable<StandardResponse> giveThumbs(@Field("id") String id
            , @Header("Authorization") String Authorization);
}
