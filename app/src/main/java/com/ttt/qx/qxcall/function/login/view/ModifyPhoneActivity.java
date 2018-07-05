package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.start.BaseActivity;

import butterknife.ButterKnife;

/**
 * 昵称修改
 * Created by wyd on 2017/7/28.
 */

public class ModifyPhoneActivity extends BaseActivity {
//    @BindView(R.id.phone_tip_tv)
//    TextView phone_tip_tv;
//    @BindView(R.id.time_tv)
//    TextView time_tv;
//    @BindView(R.id.get_identify_code_tv)
//    TextView get_identify_code_tv;
//    @BindView(R.id.regain_get_tv)
//    TextView regain_get_tv;
//    @BindView(R.id.phone_num_et)
//    EditText phone_num_et;
//    @BindView(R.id.phone_identify_code_et)
//    EditText phone_identify_code_et;
//    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        initData();
//        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
//        mPhone = getIntent().getStringExtra("phone");
    }

//    /**
//     * 初始化view
//     */
//    private void initView() {
//        phone_num_et.addTextChangedListener(new MyTextWatcher());
//        phone_tip_tv.setText("您的账号目前绑定的手机号是：" + mPhone.substring(0, 3) + "****" + mPhone.substring(7, 11) + "，请输入您希望绑定的新手机号。");
//    }
//
//    @OnClick({R.id.top_letf_back_iv, R.id.regain_get_tv, R.id.get_identify_code_tv, R.id.publish_ll})
//    public void click(View v) {
//        String phoneNum = phone_num_et.getText().toString().trim();
//        String phoneCode = phone_identify_code_et.getText().toString().trim();
//        switch (v.getId()) {
//            case R.id.top_letf_back_iv:
//                onFinish();
//                break;
//            case R.id.regain_get_tv:
//                getVerifyCode(phoneNum);
//                break;
//            case R.id.get_identify_code_tv:
//                getVerifyCode(phoneNum);
//                break;
//            case R.id.publish_ll:
//                UserDao userDao=new UserDao();
//                UserBean userBean = userDao.queryFirstData();
//                MineModel.getMineModel().modifyPhone(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseStatus>() {
//                    @Override
//                    public void onNext(ResponseStatus responseStatus) {
//                        if (responseStatus.getStatus().equals("200")) {
//                            if (responseStatus.isIsSuccess()) {
//                                onToast("修改成功！");
//                                SetPhone phone = new SetPhone();
//                                phone.phone = phoneNum;
//                                EventBus.getDefault().post(phone);
//                                onFinish();
//                            } else {
//                                errorMessageShow(responseStatus);
//                            }
//                        } else {
//                            errorMessageShow(responseStatus);
//                        }
//                    }
//                }, this), phoneNum, phoneCode,userBean.getLoginSession());
//                break;
//
//        }
//    }
//
//    private void getVerifyCode(String phoneNum) {
//        RegisterModel.getRegisterModel().getIdentifyCode(new Subscriber<ResponseStatus>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                onToast("网络异常！");
//            }
//
//            @Override
//            public void onNext(ResponseStatus responseStatus) {
//                if (responseStatus.getStatus().equals("200")) {
//                    if (responseStatus.isIsSuccess()) {
//                        onToast(responseStatus.getData());
//                        //开启 计时器 同时 将获取验证码隐藏 可见重新获取以及秒数倒计时控件
//                        onGetIDCodeCompleted();
//                    } else {
//                        errorMessageShow(responseStatus);
//                    }
//                } else {
//                    errorMessageShow(responseStatus);
//                }
//            }
//        }, phoneNum, 2);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        if (EventBus.getDefault().isRegistered(this)) {
////            EventBus.getDefault().unregister(this);
////        }
//    }
//
//    public void onToast(String message) {
//        //消息弹出
//        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
//    }
//
//    public void onFinish() {
//        //销毁当前
//        finish();
//    }
//
//    public void onGetIDCodeCompleted() {
//        RxCountDownUtil.countdown(60).doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//                //开启 计时器 同时 将获取验证码隐藏 可见重新获取以及秒数倒计时控件
//                get_identify_code_tv.setVisibility(View.INVISIBLE);
//                regain_get_tv.setVisibility(View.VISIBLE);
//                regain_get_tv.setClickable(false);
//                regain_get_tv.setTextColor(getResources().getColor(R.color._808A96));
//                time_tv.setVisibility(View.VISIBLE);
//            }
//        }).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//                //计时完成
//                time_tv.setVisibility(View.INVISIBLE);
//                time_tv.setText("");
//                regain_get_tv.setClickable(true);
//                regain_get_tv.setTextColor(getResources().getColor(R.color._333333));
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                //当前计时
//                time_tv.setText("(" + integer + ")");
//            }
//        });
//    }
//
//    private void errorMessageShow(ResponseStatus responseStatus) {
//        Object message = responseStatus.getErrorMessage();
//        if (message != null) {
//            onToast(message.toString());
//        }
//    }
//
//    class MyTextWatcher implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            //手机号文本框内容改变后处理 根据手机号的长度以及是否符合手机号格式 控制获取验证码显隐
//            String phone = phone_num_et.getText().toString().trim();
//            if (phone.matches(CommonConstant.PHONE_FORMAT_REG)) {
//                get_identify_code_tv.setVisibility(View.VISIBLE);
//                regain_get_tv.setVisibility(View.INVISIBLE);
//                time_tv.setVisibility(View.INVISIBLE);
//            } else {
//                get_identify_code_tv.setVisibility(View.INVISIBLE);
//            }
//
//        }
//    }
}
