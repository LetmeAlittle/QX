package com.ysxsoft.qxerkai.net;


import com.umeng.socialize.sina.message.BaseResponse;
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

import static com.ttt.qx.qxcall.constant.CommonConstant.SA_GOU_LIANG_COMMIT;
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
}
