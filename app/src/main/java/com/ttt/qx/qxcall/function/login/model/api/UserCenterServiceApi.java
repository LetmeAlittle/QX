package com.ttt.qx.qxcall.function.login.model.api;


import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络请求服务接口
 * Created by wyd on 2017/7/19.
 */

public interface UserCenterServiceApi {
    /**
     * 忘记密码
     *
     * @param phone
     * @param code             手机验证码
     * @param newPassword      新密码
     * @param newPasswordAgain 确认密码
     * @return
     */
    @POST("v1/User/ResetPassword")
    Observable<ResponseStatus> postForgetPwd(@Query("phone") String phone, @Query("code") String code
            , @Query("newPassword") String newPassword, @Query("newPasswordAgain") String newPasswordAgain);
    /**
     * post 没有响应体直接拼接的方式
     *
     * @param phone
     * @param type
     * @return
     */
    @POST("v1/SMS/GetSmsVerificationCode")
    Observable<ResponseStatus> postIdentifyCode2(@Query("phone") String phone, @Query("type") Integer type);

}
