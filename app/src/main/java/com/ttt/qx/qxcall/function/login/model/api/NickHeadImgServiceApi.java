package com.ttt.qx.qxcall.function.login.model.api;


import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;

import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络请求服务接口
 * Created by wyd on 2017/7/19.
 */

public interface NickHeadImgServiceApi {
    /**
     * 修改昵称以及头像
     *
     * @param headImg  修改昵称也需要 需传任意不为空的字符串 图片名称
     * @param nickName
     * @param type     0 修改头像 1 修改昵称
     * @return
     */
    @POST("v1/User/ResetUserInfo")
    Observable<ResponseStatus> postNickHeadImg(@Query("headImg") String headImg, @Query("nickName") String nickName
            , @Query("type") String type, @Header("signKey") String singKey);
}
