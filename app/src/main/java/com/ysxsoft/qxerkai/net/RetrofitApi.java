package com.ysxsoft.qxerkai.net;


import com.umeng.socialize.sina.message.BaseResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

import static com.ttt.qx.qxcall.constant.CommonConstant.RULE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIKE;
import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_LIST;

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
     * 发布撒狗粮  照片
     *
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Observable<SaGouLiangPublishResponse> publishSaGouLiang(@Url() String url, @Body RequestBody Body);

    /**
     * 发布撒狗粮  文本
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(SA_GOU_LIANG_COMMIT)
    Observable<SaGouLiangPublishResponse> publishSaGouLiang(@FieldMap Map<String, String> map);
}
