package com.ttt.qx.qxcall.function.login.model;


import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.function.base.helper.RetrofitHelper;
import com.ttt.qx.qxcall.function.base.interfacee.BaseModel;
import com.ttt.qx.qxcall.function.login.model.api.MineServiceApi;
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

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 昵称Model
 * Created by wyd on 2017/7/19.
 */

public class MineModel implements BaseModel {
    //保存当前类的静态实例对象
    private static MineModel mineModel;
    //ServiceApi接口对象
    private static MineServiceApi mineServiceApi;
    private Subscription subscription;

    //私有构造函数
    private MineModel() {
        //获取Retrofit对象实例
        Retrofit retrofit = RetrofitHelper.getRetrofitHelper(CommonConstant.COMMON_BASE_URL).getRetrofit();
        //获取ServiceApi接口对象
        mineServiceApi = retrofit.create(MineServiceApi.class);
    }

    //同步代码块提供给外界获取类的实例
    public static MineModel getMineModel() {
        if (mineModel == null) {
            synchronized (MineModel.class) {
                mineModel = new MineModel();
            }
        }
        return mineModel;
    }

    /**
     * 更改手机
     *
     * @param subscriber
     * @param phone
     * @param phoneCode
     * @param singKey
     */
    public void modifyPhone(Subscriber<ResponseStatus> subscriber, String phone, String phoneCode, String singKey) {
        Observable<ResponseStatus> observable = mineServiceApi.postModifyPhone(phone, phoneCode, singKey);
        onToSubscribe(observable, subscriber);
    }

    /**
     * 获取订单信息
     *
     * @param subscriber
     * @param pay_type
     * @param amount
     * @param Authorization
     */
    public void getPayOrderInfo(Subscriber<GetPayInfoResponse> subscriber, String pay_type, String amount, String Authorization) {
        Observable<GetPayInfoResponse> observable = mineServiceApi.getPayOrderInfo(pay_type, amount, Authorization);
        onToSubscribeOrderInfo(observable, subscriber);
    }

    /**
     * 获取订单信息
     *
     * @param subscriber
     * @param pay_type
     * @param amount
     * @param Authorization
     */
    public void getWXPayOrderInfo(Subscriber<WXPayData> subscriber, String pay_type, String amount, String Authorization) {
        Observable<WXPayData> observable = mineServiceApi.getWXPayOrderInfo(pay_type, amount, Authorization);
        onToSubscribeOrderInfo90(observable, subscriber);
    }

    /**
     * 获取vip 购买 订单信息
     *
     * @param subscriber
     * @param pay_type
     * @param vip_id
     * @param Authorization
     */
    public void getVipPayOrderInfo(Subscriber<GetPayInfoResponse> subscriber, String pay_type, String vip_id, String Authorization) {
        Observable<GetPayInfoResponse> observable = mineServiceApi.getVipPayOrderInfo(pay_type, vip_id, Authorization);
        onToSubscribeOrderInfo(observable, subscriber);
    }

    /**
     * 获取vip 购买 订单信息
     *
     * @param subscriber
     * @param pay_type
     * @param vip_id
     * @param Authorization
     */
    public void getVipWXPayOrderInfo(Subscriber<WXPayData> subscriber, String pay_type, String vip_id, String Authorization) {
        Observable<WXPayData> observable = mineServiceApi.getVipWXPayOrderInfo(pay_type, vip_id, Authorization);
        onToSubscribeOrderInfo90(observable, subscriber);
    }

    /**
     * 提现申请
     *
     * @param subscriber
     * @param amount
     * @param alipay_name
     * @param true_name
     * @param Authorization
     */
    public void mentionMoney(Subscriber<StandardResponse> subscriber, String amount, String alipay_name, String true_name, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.mentionMoney(amount, alipay_name, true_name, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 设置昵称
     *
     * @param subscriber
     * @param nick_name
     * @param Authorization
     */
    public void setUserNickName(Subscriber<StandardResponse> subscriber, String nick_name, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setUserNickName(nick_name, Authorization);
        onToSubscribe88(observable, subscriber);
    }


    /**
     * @param subscriber
     * @param content
     * @param mobile
     * @param Authorization
     */
    public void feedBack(Subscriber<StandardResponse> subscriber, String content, String mobile, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.feedBack(content, mobile, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param Authorization
     */
    public void vipEndTime(Subscriber<StandardResponse> subscriber, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.vipEndTime(Authorization);
        onToSubscribe88(observable, subscriber);
    }


    /**
     * 获取 应用标签列表
     *
     * @param subscriber
     */
    public void getTagList(Subscriber<UserTagListResponse> subscriber) {
        Observable<UserTagListResponse> observable = mineServiceApi.getTagList();
        onToSubscribe89(observable, subscriber);
    }

    /**
     * 获取用户充值比率
     *
     * @param subscriber
     */
    public void getVirtualCoinRatio(Subscriber<VirtualCoinRatio> subscriber) {
        Observable<VirtualCoinRatio> observable = mineServiceApi.getVirtualCoinRatio();
        onToSubscribe90(observable, subscriber);
    }

    /**
     * 设置偷听价格
     *
     * @param subscriber
     * @param member_price
     * @param Authorization
     */
    public void setMemberPrice(Subscriber<StandardResponse> subscriber, String member_price, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setMemberPrice(member_price, Authorization);
        onToSubscribe88(observable, subscriber);
    }


    /**
     * 设置签名
     *
     * @param subscriber
     * @param member_signature
     * @param Authorization
     */
    public void setMemberSignature(Subscriber<StandardResponse> subscriber, String member_signature, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setMemberSignature(member_signature, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 设置生日
     *
     * @param subscriber
     * @param birthday
     * @param Authorization
     */
    public void setBirthday(Subscriber<StandardResponse> subscriber, String birthday, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setBirthday(birthday, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 设置录音
     *
     * @param subscriber
     * @param member_sound
     * @param Authorization
     */
    public void setMemberSound(Subscriber<StandardResponse> subscriber, String member_sound, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setMemberSound(member_sound, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 设置录音
     *
     * @param subscriber
     * @param member_province
     * @param Authorization
     */
    public void setAddress(Subscriber<StandardResponse> subscriber, String member_province, String member_city, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setAddress(member_province, member_city, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 设置用户标签
     *
     * @param subscriber
     * @param member_tag
     * @param Authorization
     */
    public void setTag(Subscriber<StandardResponse> subscriber, String member_tag, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setTag(member_tag, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 设置用户 偷听状态
     *
     * @param subscriber
     * @param listen_state
     * @param Authorization
     */
    public void setListenState(Subscriber<StandardResponse> subscriber, String listen_state, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setListenState(listen_state, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 可提现金额
     *
     * @param subscriber
     * @param Authorization
     */
    public void crashAllow(Subscriber<StandardResponse> subscriber, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.crashAllow(Authorization);
        onToSubscribe88(observable, subscriber);
    }


    /**
     * @param subscriber
     * @param Authorization
     */
    public void crashList(Subscriber<MentionRecordList> subscriber, String page, String Authorization) {
        Observable<MentionRecordList> observable = mineServiceApi.crashList(page, Authorization);
        onToSubscribe886(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param Authorization
     */
    public void payDetail(Subscriber<PaymentDetail> subscriber, String page, String Authorization) {
        Observable<PaymentDetail> observable = mineServiceApi.payDetail(page, Authorization);
        onToSubscribe887(observable, subscriber);
    }


    /**
     * @param subscriber
     * @param Authorization
     */
    public void getBlackLists(Subscriber<BlacksList> subscriber, String page, String Authorization) {
        Observable<BlacksList> observable = mineServiceApi.getBlackLists(page, Authorization);
        onToSubscribe8808(observable, subscriber);
    }

    /**
     * @param subscriber
     * @param Authorization
     */
    public void postCancelBlack(Subscriber<StandardResponse> subscriber, String member_id, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.postCancelBlack(member_id, Authorization);
        onToSubscribe88(observable, subscriber);
    }


    /**
     * 身份认证
     *
     * @param subscriber
     * @param Authorization
     */
    public void identifyVerify(Subscriber<IdentifyVerify> subscriber, String Authorization
            , String member_id, String true_name, String sex, String id_num, String id_front
            , String id_back) {
        Observable<IdentifyVerify> observable = mineServiceApi.identifyVerify(member_id,
                true_name, sex, id_num, id_front, id_back, Authorization);
        onToSubscribe88990(observable, subscriber);
    }

    /**
     * 设置用户技能
     *
     * @param subscriber
     * @param member_cate_id
     * @param Authorization
     */
    public void setUserSkill(Subscriber<StandardResponse2> subscriber, String member_cate_id, String Authorization) {
        Observable<StandardResponse2> observable = mineServiceApi.setUserSkill(member_cate_id, Authorization);
        onToSubscribe8899(observable, subscriber);
    }

    /**
     * 设置用户在线状态
     *
     * @param subscriber
     * @param is_online
     * @param Authorization
     */
    public void setOnlineStatus(Subscriber<StandardResponse> subscriber, String is_online, String Authorization) {
        Observable<StandardResponse> observable = mineServiceApi.setOnlineStatus(is_online, Authorization);
        onToSubscribe88(observable, subscriber);
    }

    /**
     * 获取用户类型（用户技能列表）
     *
     * @param subscriber
     */
    public void getUserTypeSkillList(Subscriber<UserTypeSkillList> subscriber) {
        Observable<UserTypeSkillList> observable = mineServiceApi.getUserTypeSkillList();
        onToSubscribe888(observable, subscriber);
    }

    /**
     * 更改密码
     *
     * @param subscriber
     * @param oldPwd
     * @param newPwd
     * @param singKey
     */
    public void modifyPwd(Subscriber<ResponseStatus> subscriber, String oldPwd, String newPwd, String singKey) {
        Observable<ResponseStatus> observable = mineServiceApi.postModifyPwd(oldPwd, newPwd, singKey);
        onToSubscribe(observable, subscriber);
    }

    /**
     * 意见反馈
     *
     * @param subscriber
     * @param Content
     * @param Images
     * @param Email
     */
    public void feedBack(Subscriber<ResponseStatus> subscriber, String Content, String Images, String Email, String singKey) {
        Observable<ResponseStatus> observable = mineServiceApi.postFeedBack(Content, Images, Email, singKey);
        onToSubscribe(observable, subscriber);
    }

    /**
     * 获取应用版本信息
     *
     * @param subscriber
     */
    public void getVersionInfo(Subscriber<VersionUpdate> subscriber) {
        Observable<VersionUpdate> observable = mineServiceApi.getVersionInfo(1);
        onToSubscribe2(observable, subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe(Observable<ResponseStatus> observable, Subscriber<ResponseStatus> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribeOrderInfo(Observable<GetPayInfoResponse> observable, Subscriber<GetPayInfoResponse> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribeOrderInfo90(Observable<WXPayData> observable, Subscriber<WXPayData> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe88(Observable<StandardResponse> observable, Subscriber<StandardResponse> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe887(Observable<PaymentDetail> observable, Subscriber<PaymentDetail> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe8808(Observable<BlacksList> observable, Subscriber<BlacksList> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe886(Observable<MentionRecordList> observable, Subscriber<MentionRecordList> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe88990(Observable<IdentifyVerify> observable, Subscriber<IdentifyVerify> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe8899(Observable<StandardResponse2> observable, Subscriber<StandardResponse2> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe888(Observable<UserTypeSkillList> observable, Subscriber<UserTypeSkillList> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe89(Observable<UserTagListResponse> observable, Subscriber<UserTagListResponse> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe90(Observable<VirtualCoinRatio> observable, Subscriber<VirtualCoinRatio> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 将线程切换为一个统一方法
     *
     * @param observable 被观察者对象
     * @param subscriber 观察者对象
     */
    private void onToSubscribe2(Observable<VersionUpdate> observable, Subscriber<VersionUpdate> subscriber) {
        subscription = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void onUnSubscribe() {
        //取消订阅
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
