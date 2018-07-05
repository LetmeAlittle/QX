package com.ttt.qx.qxcall.function.login.model.api;

import com.ttt.qx.qxcall.function.login.model.entity.UploadImg;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;


/**
 * 上传图片
 * Created by wyd on 2017/7/30.
 */

public interface UploadImgServiceApi {
    /**
     * 上传图片
     *
     * @param imgs 请求体 单张
     * @return
     */
    @Multipart
    @POST("/api/user/avatar_upload")
    Observable<UploadImgResponse> uploadFile(@Query("des") String description, @Part("uploadFile\"; filename=\"test2.jpg\"") RequestBody imgs , @Header("Authorization") String Authorization);
    /**
     * 上传图片
     *
     * @param imgs 请求体 单张
     * @return
     */
    @Multipart
    @POST("/api/user/avatar_upload")
    Call<retrofit2.Response> uploadFile22(@Query("des") String description, @Part("uploadFile\"; filename=\"test2.jpg\"") RequestBody imgs
    , @Header("Authorization") String Authorization);

    /**
     * 上传图片 2张
     *
     * @param imgs1 请求体 2张
     * @return
     */
    @Multipart
    @POST("api/file/HeadImg")
    Observable<UploadImg> uploadFile2(@Query("des") String description
            , @Part("uploadFile\"; filename=\"test2.jpg\"") RequestBody imgs1
            , @Part("uploadFile\"; filename=\"test2.jpg\"") RequestBody imgs2);
}
