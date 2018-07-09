package com.ysxsoft.qxerkai.net;


import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

import static com.ttt.qx.qxcall.constant.CommonConstant.RULE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIKE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIST;
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
     * 关于我们/我的收益规则/萨狗粮规则/萨狗粮奖品/使用说明/vip特权
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
     * 发布撒狗粮
     *
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Observable<SaGouLiangPublishResponse> publishSaGouLiang(@Url() String url, @Body RequestBody Body);




//    /**
//     * 上传图片
//     *
//     * @return
//     */
//    @POST()
//    Observable<UpdateUserInfoResponse> updateLogo(@Url() String url, @Body RequestBody Body);

    /**
     * 对某人进行守护
     * @param map
     * @return
     */
    @FormUrlEncoded
    @GET(USER_SHOUHU)
    Observable<BaseResponse> getGuardsing(@FieldMap Map<String, String> map);

    /**
     * 用户的守护列表/用户守护的列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @GET(USER_GUARDS_LIST)
    Observable<GuardsListResponse> getGuardsingList(@FieldMap Map<String, String> map);
}
