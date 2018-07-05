package com.ttt.qx.qxcall.function.home.model.api;


import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.home.model.entity.FlowVisitorInfoList;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 网络请求服务接口
 * Created by wyd on 2017/10/15.
 */

public interface HomeServiceApi {

    /**
     * 获取用户详细信息
     *
     * @param id 用户id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/get_user_info")
    Observable<UserDetailInfo> getUserInfo(@Field("id") String id, @Header("Authorization") String Authorization);

    /**
     * 判断某个用户是否是 我的黑名单
     *
     * @param member_id 用户id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/is_black_fans")
    Observable<StandardResponse> isBackFans(@Field("member_id") String member_id, @Header("Authorization") String Authorization);

    /**
     * 判断某个用户是否是 拉黑某个用户
     *
     * @param member_id 用户id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/black_fans")
    Observable<StandardResponse> blackFans(@Field("member_id") String member_id, @Header("Authorization") String Authorization);

    /**
     * 关注用户
     *
     * @param member_id 用户id
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/flow")
    Observable<StandardResponse> followUser(@Field("member_id") String member_id, @Header("Authorization") String Authorization);

    /**
     * 取消关注用户
     *
     * @param member_id 用户id
     * @return
     */
    @FormUrlEncoded
    @POST("api/friend/unflow")
    Observable<StandardResponse> unfollowUser(@Field("member_id") String member_id, @Header("Authorization") String Authorization);

    /**
     * 获取用户列表
     *
     * @param tag 用户标签
     * @param sex 当前查询性别 1男 2女
     * @return
     */
    @FormUrlEncoded
    @POST("api/home")
    Observable<UserListInfo> getUserList(@Field("tag") String tag, @Field("sex") String sex, @Field("page") String page, @Header("Authorization") String Authorization);

    /**
     * 首页用户数据筛选
     *
     * @param id   用户id
     * @param sex  性别 传递 数字 1男 2女
     * @param page 当前请求页
     * @return
     */
    @FormUrlEncoded
    @POST("api/home/search")
    Observable<UserListInfo> filterUserList(@Field("id") String id, @Field("sex") String sex, @Field("page") String page);

    /**
     * 应用首页标签
     *
     * @return
     */
    @POST("api/home/tag")
    Observable<CommonTagList> getHomeTagList();

    /**
     * 获取关注
     *
     * @param member_id
     * @param page
     * @param authorization
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/flow_list")
    Observable<FlowVisitorInfoList> getUserFollowList(@Field("member_id") String member_id, @Field("page") String page, @Header("Authorization") String authorization);

    /**
     * 获取粉丝
     *
     * @param member_id
     * @param page
     * @param authorization
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/fans_list")
    Observable<FlowVisitorInfoList> getUserFansList(@Field("member_id") String member_id, @Field("page") String page, @Header("Authorization") String authorization);

    /**
     * 获取访客
     *
     * @param member_id
     * @param page
     * @param authorization
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/visitor_list")
    Observable<FlowVisitorInfoList> getUserVisitorList(@Field("member_id") String member_id, @Field("page") String page, @Header("Authorization") String authorization);
}
