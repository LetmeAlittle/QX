package com.ttt.qx.qxcall.function.login.model.api;

import com.ttt.qx.qxcall.function.login.model.entity.BlacksList;
import com.ttt.qx.qxcall.function.login.model.entity.GetPayInfoResponse;
import com.ttt.qx.qxcall.function.login.model.entity.MentionRecordList;
import com.ttt.qx.qxcall.function.login.model.entity.PaymentDetail;
import com.ttt.qx.qxcall.function.login.model.entity.UserTagListResponse;
import com.ttt.qx.qxcall.function.login.model.entity.UserTypeSkillList;
import com.ttt.qx.qxcall.function.login.model.entity.VersionUpdate;
import com.ttt.qx.qxcall.function.login.model.entity.VirtualCoinRatio;
import com.ttt.qx.qxcall.function.register.model.entity.IdentifyVerify;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse2;
import com.ttt.qx.qxcall.function.wxpay.WXPayData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络请求服务接口
 * Created by wyd on 2017/7/19.
 */

public interface MineServiceApi {
    /**
     * 修改手机号
     *
     * @param phone     修改手机号
     * @param phoneCode
     * @return
     */
    @POST("v1/User/ResetPhone")
    Observable<ResponseStatus> postModifyPhone(@Query("phone") String phone, @Query("phoneCode") String phoneCode
            , @Header("signKey") String signKey);

    /**
     * 获取订单信息
     *
     * @param pay_type 支付类别 alipay 支付宝支付（默认） wxpay 微信支付
     * @param amount   充值金额
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/pay")
    Observable<GetPayInfoResponse> getPayOrderInfo(@Field("pay_type") String pay_type, @Field("amount") String amount
            , @Header("Authorization") String Authorization);
  /**
     * 微信支付
     *
     * @param pay_type 支付类别 alipay 支付宝支付（默认） wxpay 微信支付
     * @param amount   充值金额
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/pay")
    Observable<WXPayData> getWXPayOrderInfo(@Field("pay_type") String pay_type, @Field("amount") String amount
            , @Header("Authorization") String Authorization);

    /**
     * vip购买
     *
     * @param pay_type 支付类别 alipay 支付宝支付（默认） wxpay 微信支付
     * @param vip_id   vip id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/vip_pay")
    Observable<GetPayInfoResponse> getVipPayOrderInfo(@Field("pay_type") String pay_type, @Field("vip_id") String vip_id
            , @Header("Authorization") String Authorization);
 /**
     * vip购买
     *
     * @param pay_type 支付类别 alipay 支付宝支付（默认） wxpay 微信支付
     * @param vip_id   vip id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/vip_pay")
    Observable<WXPayData> getVipWXPayOrderInfo(@Field("pay_type") String pay_type, @Field("vip_id") String vip_id
            , @Header("Authorization") String Authorization);

    /**
     * vip购买
     *
     * @param amount
     * @param alipay_name
     * @param true_name
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/cash_apply")
    Observable<StandardResponse> mentionMoney(@Field("amount") String amount, @Field("alipay_name") String alipay_name
            , @Field("true_name") String true_name, @Header("Authorization") String Authorization);

    /**
     * 获取应用 所使用的标签数据
     *
     * @return
     */
    @POST("api/tag_list")
    Observable<UserTagListResponse> getTagList();

    /**
     * 获取应用 所使用的标签数据
     *
     * @return
     */
    @POST("api/pay/ratio")
    Observable<VirtualCoinRatio> getVirtualCoinRatio();

    /**
     * 设置用户个人资料 昵称
     *
     * @param nick_name
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setUserNickName(@Field("nick_name") String nick_name, @Header("Authorization") String Authorization);


    /**
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("api/comment")
    Observable<StandardResponse> feedBack(@Field("content") String content,@Field("mobile") String mobile, @Header("Authorization") String Authorization);
   /**
     * @param
     * @return
     */
    @POST("api/user/vip_time")
    Observable<StandardResponse> vipEndTime( @Header("Authorization") String Authorization);








    /**
     * 设置用户个人资料 偷听价格
     *
     * @param member_price 偷听价格 为整数
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setMemberPrice(@Field("member_price") String member_price, @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 签名
     *
     * @param member_signature 签名
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setMemberSignature(@Field("member_signature") String member_signature, @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 生日
     *
     * @param birthday
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setBirthday(@Field("birthday") String birthday, @Header("Authorization") String Authorization);

    /**
     * 设置用户录音文件
     *
     * @param member_sound
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setMemberSound(@Field("member_sound") String member_sound, @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 年龄
     *
     * @param member_age
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setAge(@Field("member_age") String member_age, @Header("Authorization") String Authorization);
    /**
     * 设置用户地址信息
     *
     * @param member_province
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setAddress(@Field("member_province") String member_province
            , @Field("member_city") String member_city
            , @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 标签
     *
     * @param member_tag
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setTag(@Field("member_tag") String member_tag, @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 技能
     *
     * @param member_cate_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse2> setUserSkill(@Field("member_cate_id") String member_cate_id, @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 在线状态
     *
     * @param is_online
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setOnlineStatus(@Field("is_online") String is_online, @Header("Authorization") String Authorization);

    /**
     * 设置用户个人资料 偷听状态
     *
     * @param listen_state
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/set_info")
    Observable<StandardResponse> setListenState(@Field("listen_state") String listen_state, @Header("Authorization") String Authorization);

    /**
     * 可提现金额
     *
     * @return
     */
    @POST("api/user/cash_allow")
    Observable<StandardResponse> crashAllow(@Header("Authorization") String Authorization);

    /**
     * 记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/cash_list")
    Observable<MentionRecordList> crashList(@Field("page") String pag, @Header("Authorization") String Authorization);
    /**
     * 记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/account_log")
    Observable<PaymentDetail> payDetail(@Field("page") String page,@Header("Authorization") String Authorization);

    /**
     * 拉黑列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/black_fans_list")
    Observable<BlacksList> getBlackLists(@Field("page") String page, @Header("Authorization") String Authorization);

    /**
     * 取消拉黑
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/cancle_black_fans")
    Observable<StandardResponse> postCancelBlack(@Field("member_id") String member_id, @Header("Authorization") String Authorization);

    /**
     * 身份认证
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/cash_true_info")
    Observable<IdentifyVerify> identifyVerify(@Field("member_id") String member_id,
                                              @Field("true_name") String true_name,
                                              @Field("sex") String sex,
                                              @Field("id_num") String id_num,
                                              @Field("id_front") String id_front,
                                              @Field("id_back") String id_back,
                                              @Header("Authorization") String Authorization);

    /**
     * 技能
     *
     * @return
     */
    @POST("api/member_cate_list")
    Observable<UserTypeSkillList> getUserTypeSkillList();

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @POST("v1/User/ChangePassword")
    Observable<ResponseStatus> postModifyPwd(@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword
            , @Header("signKey") String signKey);

    /**
     * 意见反馈
     *
     * @param Content 意见内容
     * @param Images  图片 url,路径 多个中间用逗号分割
     * @param Email   用户邮箱
     * @return
     */
    @FormUrlEncoded
    @POST("v1/Suggest/Post")
    Observable<ResponseStatus> postFeedBack(@Field("Content") String Content, @Field("Images") String Images
            , @Field("Email") String Email, @Header("signKey") String signKey);

    /**
     * 获取应用版本信息
     *
     * @return
     */
    @GET("v1/System/VersonInfo")
    Observable<VersionUpdate> getVersionInfo(@Query("type") Integer type);
}
