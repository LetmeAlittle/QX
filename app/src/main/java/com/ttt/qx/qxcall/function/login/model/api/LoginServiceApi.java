package com.ttt.qx.qxcall.function.login.model.api;


import com.ttt.qx.qxcall.function.login.model.entity.InvitedRecord;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.login.model.entity.ThreeLoginResponse;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.UserShareInfo;
import com.ttt.qx.qxcall.function.login.model.entity.VIPBuyList;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * user网络请求服务接口
 * Created by wyd on 2017/7/19.
 */

public interface LoginServiceApi {
    /**
     * 用户登录接口
     *
     * @param member_name
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/login")
    Observable<LoginedResponse> postLogin(@Field("member_name") String member_name, @Field("password") String password);

    /**
     * 用户登录奖励
     *
     * @return
     */
    @POST("api/user/vip_login")
    Observable<StandardResponse> loginReward(@Header("Authorization") String Authorization);

    /**
     * 上线状态
     *
     * @return
     */
    @POST("api/android_online")
    Observable<StandardResponse> androidOnline();

    /**
     * 举报
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/report")
    Observable<StandardResponse> userReport(@Field("member_id") String member_id, @Field("content") String content, @Header("Authorization") String Authorization);

    /**
     * 获取用户分享信息
     *
     * @return
     */
    @GET("api/share/info/{id}")
    Observable<UserShareInfo> getShareInfo(@Path("id") String id);

    /**
     * 获取邀请记录
     *
     * @return
     */
    @GET("api/share/list/{id}")
    Observable<InvitedRecord> getInvitedRecord(@Path("id") String id);

    /**
     * 用户购买VIP 列表接口
     *
     * @return
     */
    @POST("api/vip_list")
    Observable<VIPBuyList> buyVipList();

    /**
     * 第三方登录成功回调接口
     * 微信
     *
     * @param openid
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/wx_login")
    Observable<ThreeLoginResponse> postShareWeiXinLogin(@Field("openid") String openid);

    /**
     * 第三方QQ登录成功回调接口
     *
     * @param openid
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/qq_login")
    Observable<ThreeLoginResponse> postShareQQLogin(@Field("openid") String openid);

    /**
     * 请求url根据函数参数变化
     * 路径替换
     *
     * @param groupId 请求路径变化参数
     * @return
     */
    @GET("group/{id}/users")
    Observable<List<User>> getUserList(@Path("id") int groupId);//注意 Path注解的参数要和前面的字符串一样 id

    /**
     * Post请求时，使用@Body注解可以声明一个对象作为请求体发送到服务器
     * 且使用对应的转换器转换为字符串或者字节流提交到服务器
     *
     * @param user           待上传给服务器的user对象
     * @param observableUser 服务器回调返回的user对象
     */
    @POST("users/new")
    void createUser(@Body User user, Observable<User> observableUser);

    /**
     * 发送表单数据
     *
     * @param first_name
     * @param last_name
     * @return
     */
    @FormUrlEncoded
    @POST("user/edit")
    Observable<User> updateUser(@Field("first_name") String first_name, @Field("last_name") String last_name);

    /**
     * 发送multipart 数据
     *
     * @param photo
     * @param description
     * @return
     */
//    @Multipart
//    @PUT("user/photo")
//    Observable<User> updateUser(@Part("photo") TypedFile photo, @Part("description") TypedString description);

    /**
     * 固定头请求
     *
     * @param map 查询Map
     * @return
     */
    @GET("user/photo")
    @Headers("Accept-Encoding:application/json")
    Observable<User> updateUser(@QueryMap Map<String, String> map);

    /**
     * 动态请求头
     * Location为请求key
     *
     * @param location
     * @return
     */
    @GET("some/endpoint")
    Observable<User> someEndPoint(@Header("Location") String location);

    /**
     * 检验loginsession 是否过期
     *
     * @param loginSession
     * @param signKey
     * @return
     */
    @POST("v1/Auth/CheckSession")
    Observable<ResponseStatus> checkSession(@Query("loginSession") String loginSession, @Header("signKey") String signKey);

    /**
     * 绑定交易账户userId、tradeId与微财账户
     *
     * @param MattUserId
     * @param TradeId
     * @param Password
     * @param signKey
     * @return
     */
    @FormUrlEncoded
    @POST("v1/Auth/MattMapping")
    Observable<ResponseStatus> bindMatAccount(@Field("MattUserId") String MattUserId, @Field("TradeId") String TradeId, @Field("Password") String Password, @Header("signKey") String signKey);

    /**
     * 检查交易账户
     *
     * @param MattUserId
     * @param TradeId
     * @param Password
     * @param signKey
     * @return
     */
    @FormUrlEncoded
    @POST("v1/Auth/MattCheck")
    Observable<ResponseStatus> checkMatAccount(@Field("MattUserId") String MattUserId, @Field("TradeId") String TradeId, @Field("Password") String Password, @Header("signKey") String signKey);
}
