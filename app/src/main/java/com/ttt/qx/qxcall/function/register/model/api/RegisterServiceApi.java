package com.ttt.qx.qxcall.function.register.model.api;


import com.ttt.qx.qxcall.function.register.model.entity.BindResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseResult;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UserInfoSave;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * user网络请求服务接口 注册
 * Created by wyd on 2017/7/19.
 */

public interface RegisterServiceApi {

    /**
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("sms/verify-code")
    Observable<ResponseResult> postIdentifyCode(@Field("mobile") String mobile, @Field("access_token") String access_token);

    /**
     * post 没有响应体直接拼接的方式
     *
     * @param mobile
     * @return
     */
    @POST("sms/verify-code")
    Observable<ResponseStatus> postIdentifyCode2(@Query("mobile") String mobile);

    /**
     * 注册
     *
     * @param mobile                账号
     * @param code                  验证码
     * @param password              密码
     * @param password_confirmation 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/register")
    Observable<StandardResponse> postRegister(@Field("mobile") String mobile
            , @Field("access_token") String access_token, @Field("code") String code
            , @Field("password") String password, @Field("password_confirmation") String password_confirmation);

    /**
     * 重置密码
     *
     * @param mobile                账号
     * @param code                  验证码
     * @param password              密码
     * @param password_confirmation 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/reset_password")
    Observable<StandardResponse> postResetPwd(@Field("mobile") String mobile
            , @Field("code") String code, @Field("password") String password
            , @Field("password_confirmation") String password_confirmation
            , @Field("access_token") String access_token);

    /**
     * post 有响应体
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @POST("v1/User/BindPhone")
    Observable<BindResponseStatus> postBind(@Query("phone") String phone
            , @Query("code") String code, @Query("password") String password, @Header("signKey") String signKey);

    /**
     * 上传头像
     *
     * @param avatar
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/avatar_uploadbase")
    Observable<UploadImgResponse> uploadHeadImg(@Field("avatar") String avatar, @Header("Authorization") String Authorizatio);

    /**
     * 上传 用户主页 相册照片
     *
     * @param img
     * @param field field 字段只能为 img_1  img_2  img_3  img_4
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/thumb_upload")
    Observable<UploadImgResponse> uploadUserMainPic(@Field("img") String img, @Field("field") String field, @Header("Authorization") String Authorizatio);

    /**
     * 图片通用上传接口
     *
     * @param img
     * @return
     */
    @FormUrlEncoded
    @POST("api/img_upload")
    Observable<UploadImgResponse> commonUpload(@Field("img") String img);

    /**
     * 删除 用户主页 相册照片
     *
     * @param field field 字段只能为 img_1  img_2  img_3  img_4
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/thumb_del")
    Observable<StandardResponse> delUserMainPic(@Field("field") String field, @Header("Authorization") String Authorizatio);

    /**
     * 保存用户信息
     *
     * @param nick_name
     * @param sex
     * @param age
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/info_save")
    Observable<UserInfoSave> infoSave(@Field("nick_name") String nick_name, @Field("sex") String sex
            , @Field("age") String age, @Header("Authorization") String Authorizatio);
}
