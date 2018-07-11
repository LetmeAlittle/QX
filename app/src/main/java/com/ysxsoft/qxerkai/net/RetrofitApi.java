package com.ysxsoft.qxerkai.net;


import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.net.response.GetLiaoRenListResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;
import com.ysxsoft.qxerkai.net.response.TwoPageTuiJianResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_ADD;
import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_DETAIL;
import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_LIKE;
import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.MY_CARD;
import static com.ttt.qx.qxcall.constant.CommonConstant.NOTICE_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.PUSH_COMMENT;
import static com.ttt.qx.qxcall.constant.CommonConstant.RULE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIKE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_fjs;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_guanzhur;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_topList;
import static com.ttt.qx.qxcall.constant.CommonConstant.TWO_PAGE_USER_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.USER_GUARDS_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.USER_SHOUHU;

public interface RetrofitApi {

    /**
     * 撒狗粮列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SA_GOU_LIANG_LIST)
    Observable<SaGouLiangListResponse> getSaGouLiangList(@FieldMap Map<String, String> map);

    /**
     * 撒狗粮点赞
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SA_GOU_LIANG_LIKE)
    Observable<SaGouLiangLikeResponse> likeSaGouLiang(@FieldMap Map<String, String> map);

    /**
     * 关于我们/我的收益规则/萨狗粮规则/萨狗粮奖品/使用说明/vip特权
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(RULE)
    Observable<RuleResponse> getRule(@FieldMap Map<String, String> map);

    /**
     * 发布撒狗粮 图片
     *
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Observable<SaGouLiangPublishResponse> publishSaGouLiang(@Url() String url, @Body RequestBody Body);

    /**
     * 发布撒狗粮 故事
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SA_GOU_LIANG_COMMIT)
    Observable<SaGouLiangPublishResponse> publishSaGouLiang(@FieldMap Map<String, String> map);

    /**
     * 对某人进行守护
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @GET(USER_SHOUHU)
    Observable<BaseResponse> getGuardsing(@FieldMap Map<String, String> map);

    /**
     * 用户的守护列表/用户守护的列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @GET(USER_GUARDS_LIST)
    Observable<GuardsListResponse> getGuardsingList(@FieldMap Map<String, String> map);

    /**
     * 小情趣列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(CARD_LIST)
    Observable<GetCardListResponse> getCardList(@FieldMap Map<String, String> map);

    /**
     * 小情趣详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(CARD_DETAIL)
    Observable<GetCardDetailResponse> getCardDetail(@FieldMap Map<String, String> map);

    /**
     * 小情趣评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(PUSH_COMMENT)
    Observable<BaseResponse> submitCardComment(@FieldMap Map<String, String> map);

    /**
     * 小情趣公告
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(NOTICE_LIST)
    Observable<GetNoticeListResponse> getNoticeList(@FieldMap Map<String, String> map);

    /**
     * 小情趣点赞
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(CARD_LIKE)
    Observable<BaseResponse> cardLike(@FieldMap Map<String, String> map);

    /**
     * 发表小情趣/撩人区帖子  文本
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(CARD_ADD)
    Observable<BaseResponse> publishCard(@FieldMap Map<String, String> map);

    /**
     * 发表小情趣/撩人区帖子  图片
     *
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Observable<BaseResponse> publishCard(@Url() String url, @Body RequestBody Body);

    /**
     * 获取我的撩人区列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(MY_CARD)
    Observable<GetLiaoRenListResponse> getLiaoRenList(@FieldMap Map<String, String> map);

    /**
     * 获取第二页推荐列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TWO_PAGE_USER_LIST)
    Observable<TwoPageTuiJianResponse> getTuiJianList(@FieldMap Map<String, String> map);



    /**
     * 获取附近的人列表  私人定制
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SRDZ_fjs)
    Observable<HaoYouListResponse> getFjRenList(@FieldMap Map<String, String> map);

    /**
     * 获取好友列表  私人定制
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SRDZ_guanzhur)
    Observable<HaoYouListResponse> getHaoYouList(@FieldMap Map<String, String> map);


    /**
     * 获取土豪/一姐榜
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SRDZ_topList)
    Observable<HaoYouListResponse> getTopList(@FieldMap Map<String, String> map);
}
