package com.ysxsoft.qxerkai.net;


import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.net.response.GetGuShiResponse;
import com.ysxsoft.qxerkai.net.response.GetHuaTiListResponse;
import com.ysxsoft.qxerkai.net.response.GetHuaTiListResponse;
import com.ysxsoft.qxerkai.net.response.GetJiaoSeListenningResponse;
import com.ysxsoft.qxerkai.net.response.GetJiaoSePPidResponse;
import com.ysxsoft.qxerkai.net.response.GetLiaoRenListResponse;
import com.ysxsoft.qxerkai.net.response.GetLuYinListResponse;
import com.ysxsoft.qxerkai.net.response.GetLuYinTagListResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.GetPiPeiListResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingDetailResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingListResponse;
import com.ysxsoft.qxerkai.net.response.GetQuestionRespose;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.net.response.MemberListResponse;
import com.ysxsoft.qxerkai.net.response.MyLiWuResponse;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.net.response.HomeItemList;
import com.ysxsoft.qxerkai.net.response.MyLiWuResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;
import com.ysxsoft.qxerkai.net.response.SearchListResponse;
import com.ysxsoft.qxerkai.net.response.TwoPageTuiJianResponse;
import com.ysxsoft.qxerkai.net.response.UserXiaoFeiNum;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_ADD;
import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_DETAIL;
import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_LIKE;
import static com.ttt.qx.qxcall.constant.CommonConstant.CARD_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.DAN_MU;
import static com.ttt.qx.qxcall.constant.CommonConstant.DEL_ICON;
import static com.ttt.qx.qxcall.constant.CommonConstant.GET_JIAO_ACCEPT_CHECK;
import static com.ttt.qx.qxcall.constant.CommonConstant.GET_JIAO_LISTENNING;
import static com.ttt.qx.qxcall.constant.CommonConstant.GET_JIAO_SE_PPID;
import static com.ttt.qx.qxcall.constant.CommonConstant.GU_SHI;
import static com.ttt.qx.qxcall.constant.CommonConstant.HUA_TI_ADD;
import static com.ttt.qx.qxcall.constant.CommonConstant.HUA_TI_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.HUA_TI_START;
import static com.ttt.qx.qxcall.constant.CommonConstant.HOME_ITEM_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.JIE_SUO;
import static com.ttt.qx.qxcall.constant.CommonConstant.MEMBER_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.MY_CARD;
import static com.ttt.qx.qxcall.constant.CommonConstant.NOTICE_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.PI_PEI;
import static com.ttt.qx.qxcall.constant.CommonConstant.PI_PEI_BUY;
import static com.ttt.qx.qxcall.constant.CommonConstant.PI_PEI_SUO;
import static com.ttt.qx.qxcall.constant.CommonConstant.PI_PEI_YI_JIAN;
import static com.ttt.qx.qxcall.constant.CommonConstant.PUSH_COMMENT;
import static com.ttt.qx.qxcall.constant.CommonConstant.RULE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIKE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_fjs;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_guanzhur;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_search;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_topList;
import static com.ttt.qx.qxcall.constant.CommonConstant.TOU_TING_DETAIL;
import static com.ttt.qx.qxcall.constant.CommonConstant.TOU_TING_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.TOU_TING_LU_YIN_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.SRDZ_wentiliebiao;
import static com.ttt.qx.qxcall.constant.CommonConstant.TOU_TING_START;
import static com.ttt.qx.qxcall.constant.CommonConstant.TOU_TING_TAG;
import static com.ttt.qx.qxcall.constant.CommonConstant.TWO_PAGE_USER_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.UPDATE_USER_CATE;
import static com.ttt.qx.qxcall.constant.CommonConstant.UPLOAD_ICONS;
import static com.ttt.qx.qxcall.constant.CommonConstant.USER_GUARDS_LIST;
import static com.ttt.qx.qxcall.constant.CommonConstant.USER_MY_LIWU;
import static com.ttt.qx.qxcall.constant.CommonConstant.USER_SHOUHU;
import static com.ttt.qx.qxcall.constant.CommonConstant.USER_XIAO_FEI_NUM;

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
    @GET(USER_SHOUHU)
    Observable<BaseResponse> getGuardsing(@QueryMap Map<String, String> map);

    /**
     * 用户的守护列表/用户守护的列表
     *
     * @param map
     * @return
     */
    @GET(USER_GUARDS_LIST)
    Observable<GuardsListResponse> getGuardsingList(@QueryMap Map<String, String> map);

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
     * 获取漂浮的话题
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HUA_TI_LIST)
    Observable<GetHuaTiListResponse> getHuaTiList(@FieldMap Map<String, String> map);

    /**
     * 发表话题
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HUA_TI_ADD)
    Observable<BaseResponse> addHuaTi(@FieldMap Map<String, String> map);

    /**
     * 发表话题
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HUA_TI_START)
    Observable<BaseResponse> huaTiStart(@FieldMap Map<String, String> map);

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
     * 获取好友列表  私人定制
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SRDZ_search)
    Observable<SearchListResponse> getSearchList(@FieldMap Map<String, String> map);


    /**
     * 获取土豪/一姐榜
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SRDZ_topList)
    Observable<HaoYouListResponse> getTopList(@FieldMap Map<String, String> map);

    /**
     * 获取我的礼物列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(USER_MY_LIWU)
    Observable<MyLiWuResponse> getMyLiWuList(@FieldMap Map<String, String> map);

    /**
     * 获取用户消费的豆子数
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(USER_XIAO_FEI_NUM)
    Observable<UserXiaoFeiNum> getUserXiaoFei(@FieldMap Map<String, String> map);

    /**
     * 获取用户消费的豆子数
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(HOME_ITEM_LIST)
    Observable<HomeItemList> getHomeItemList(@FieldMap Map<String, String> map);

    /**
     * 一键匹配
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(PI_PEI_YI_JIAN)
    Observable<GetPiPeiListResponse> getPiPeiList(@FieldMap Map<String, String> map);

    /**
     * 开始匹配
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(PI_PEI)
    Observable<BaseResponse> startPiPei(@FieldMap Map<String, String> map);

    /**
     * 一键匹配 每分钟扣钱
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(PI_PEI_BUY)
    Observable<BaseResponse> piPeiBuy(@FieldMap Map<String, String> map);

    /**
     * 往相册上传图片
     *
     * @return
     */
    @POST(UPLOAD_ICONS)
    Observable<BaseResponse> uploadIcon(@Body RequestBody Body);

    /**
     * 删除照片
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(DEL_ICON)
    Observable<BaseResponse> delIcon(@FieldMap Map<String, String> map);

    /**
     * 获取用户身份列表
     *
     * @return
     */
    @POST(MEMBER_LIST)
    Observable<MemberListResponse> getMemberList();

    /**
     * 问题的列表
     *
     * @return
     */
    @POST(SRDZ_wentiliebiao)
    Observable<GetQuestionRespose> getQuestionList();

    /**
     * 获取偷听列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TOU_TING_LIST)
    Observable<GetTouTingListResponse> getTouTingList(@FieldMap Map<String, String> map);

    /**
     * 获取录音列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TOU_TING_LU_YIN_LIST)
    Observable<GetLuYinListResponse> getLuYinList(@FieldMap Map<String, String> map);

    /**
     * 获取录音 标签列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TOU_TING_TAG)
    Observable<GetLuYinTagListResponse> getLuYinTagList(@FieldMap Map<String, String> map);

    /**
     * 获取偷听详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TOU_TING_DETAIL)
    Observable<GetTouTingDetailResponse> getTouTingDetail(@FieldMap Map<String, String> map);

    /**
     * 匹配上锁
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(PI_PEI_SUO)
    Observable<BaseResponse> suo(@FieldMap Map<String, String> map);

    /**
     * 发弹幕
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(DAN_MU)
    Observable<BaseResponse> fadanmu(@FieldMap Map<String, String> map);

    /**
     * 解锁
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(JIE_SUO)
    Observable<BaseResponse> jiesuo(@FieldMap Map<String, String> map);

    /**
     * 故事内容
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(GU_SHI)
    Observable<GetGuShiResponse> getGuShi(@FieldMap Map<String, String> map);

    /**
     * 检测房间是否上锁 是否可以偷听  开始偷听
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TOU_TING_START)
    Observable<BaseResponse> checkTouTing(@FieldMap Map<String, String> map);

    /**
     * 获取系统匹配ppid
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(GET_JIAO_SE_PPID)
    Observable<GetJiaoSePPidResponse> getJiaoSePPid(@FieldMap Map<String, String> map);

    /**
     * 用户接听 判断是否能接听
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(GET_JIAO_ACCEPT_CHECK)
    Observable<BaseResponse> acceptJiaoSeCheck(@FieldMap Map<String, String> map);

    /**
     * 获取系统匹配ppid
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(GET_JIAO_LISTENNING)
    Observable<GetJiaoSeListenningResponse> jiaoSeListenning(@FieldMap Map<String, String> map);

    /**
     * 更改用户身份
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(UPDATE_USER_CATE)
    Observable<BaseResponse> updateUserCate(@FieldMap Map<String, String> map);
}
