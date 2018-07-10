package com.ttt.qx.qxcall.function.find.model.api;


import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.find.model.entity.GiftRankList;
import com.ttt.qx.qxcall.function.listen.model.entity.StealDetailResponse;
import com.ttt.qx.qxcall.function.listen.model.entity.StealListenList;
import com.ttt.qx.qxcall.function.register.model.entity.CommitPyqBgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 网络请求服务接口
 * Created by wyd on 2017/10/15.
 */

public interface FindServiceApi {

    /**
     * 获取偷听列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/listen/list")
    Observable<StealListenList> getStealListenList(@Field("tag") String tag);

    /**
     * 获取偷听详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/listen/info")
    Observable<StealDetailResponse> getStealListenDetail(@Field("id") String id);

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
     * 更换朋友圈背景
     *
     * @return
     */
    @Multipart
    @POST("api/backgroundqh")
    Observable<CommitPyqBgResponse> commitPyqBg(@Part("user_id") String user_id, @Part MultipartBody.Part flie);

    /**
     * 发表说说
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/say")
    Observable<StandardResponse> publishDynamic(@Field("content") String content, @Field("img") String img
            , @Header("Authorization") String Authorization);

    /**
     * 全部动态数据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/list")
    Observable<DynamicResponse> getAllDynamic(@Header("Authorization") String Authorization, @Field("page") String page, @Field("user_id") String user_id);

    /**
     * 好友动态数据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/friend_list")
    Observable<DynamicResponse> getFriendDynamic(@Header("Authorization") String Authorization, @Field("page") String page,@Field("user_id") String user_id);

    /**
     * 说说点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/zan")
    Observable<StandardResponse> callDianZan(@Field("id") String id, @Header("Authorization") String Authorization);

    /**
     * 说说 动态详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/detail")
    Observable<DynamicDetail> callDetail(@Field("id") String id, @Header("Authorization") String Authorization);

    /**
     * 说说 回复
     *
     * @param say_id        当前说说id
     * @param reply_id      说说的回复者 id 用户id
     * @param content       回复内容
     * @param Authorization
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/reply")
    Observable<StandardResponse> callReplay(@Field("say_id") String say_id, @Field("reply_id") String reply_id
            , @Field("content") String content, @Header("Authorization") String Authorization);

    /**
     * 获取礼物列表
     *
     * @return
     */
    @POST("api/gift/list")
    Observable<GiftList> getGiftList();

    /**
     * 获取礼物排行榜
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/gift_top")
    Observable<GiftRankList> getGiftRankList(@Field("page") String page,@Header("Authorization") String Authorization);

    /**
     * 说说赠送礼物
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/send_gift")
    Observable<StandardResponse> sendGiftList(@Field("gift_id") String gift_id
            , @Field("say_id") String say_id
            , @Header("Authorization") String Authorization);
    /**
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/talk_send_gift")
    Observable<StandardResponse> sendCallGiftList(@Field("gift_id") String gift_id
            , @Field("member_id") String member_id
            , @Header("Authorization") String Authorization);
    /**
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/chat_send_gift")
    Observable<StandardResponse> sendChatGiftList(@Field("gift_id") String gift_id
            , @Field("member_id") String member_id
            , @Header("Authorization") String Authorization);
}
