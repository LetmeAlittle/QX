package com.ttt.qx.qxcall.function.register.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.register.presenter.IRegisterPresenter;
import com.ttt.qx.qxcall.function.start.BaseActivity;

import butterknife.ButterKnife;

/**
 * 注册视图逻辑
 * Created by wyd on 2017/7/19.
 */

public class RegisterActivity extends BaseActivity implements IRegisterView {
    //视图内共享常量。
//    @BindView(R.id.phone_num_et)
//    EditText phone_num_et;
//    @BindView(R.id.phone_identify_code_et)
//    EditText phone_identify_code_et;
//    @BindView(R.id.pwd_et)
//    EditText pwd_et;
//    @BindView(R.id.get_identify_code_tv)
//    TextView get_identify_code_tv;
//    @BindView(R.id.regain_get_tv)
//    TextView regain_get_tv;
//    @BindView(R.id.time_tv)
//    TextView time_tv;
//    @BindView(R.id.title)
//    TextView title;
    private IRegisterPresenter mIRegisterPresenter;
    private String mMDirection;
    private String mLoginSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
//        initData();
//        initView();
    }

    @Override
    public void onGetIDCodeCompleted() {

    }

    @Override
    public void onToast(String message) {

    }

    @Override
    public void onFinish() {

    }
//
//    /**
//     * 初始化view
//     */
//    private void initView() {
//        //如果是直接来自于登陆页 设置title为注册
//        if (mMDirection.equals("LoginActivity")) {
//            title.setText(getResources().getString(R.string.register_text));
//        }
//        //手机号文本内容改变事件监听
//        phone_num_et.addTextChangedListener(new MyTextWatcher());
//    }
//
//    /**
//     * 初始化view
//     */
//    private void initData() {
//        Intent intent = getIntent();
//        mMDirection = intent.getStringExtra("direction");
//        mLoginSession = intent.getStringExtra("loginSession");
//        mIRegisterPresenter = new RegisterPresenterImpl(this, this, mMDirection, mLoginSession);
//    }
//
//    @Override
//    public void onToast(String message) {
//        //消息弹出
//        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
//    }
//
//    @Override
//    public void onFinish() {
//        finish();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (mMDirection.equals("LoginActivity")) {
//            mActivityManager.removeActivityNotFinish(LoginActivity.class);
//        }
//    }
//
//    @OnClick({R.id.get_identify_code_tv, R.id.time_tv, R.id.regain_get_tv, R.id.publish_ll})
//    public void click(View v) {
//        String phone = phone_num_et.getText().toString().trim();
//        String phoneCode = phone_identify_code_et.getText().toString().trim();
//        String pwd = pwd_et.getText().toString().trim();
//        switch (v.getId()) {
//            case R.id.get_identify_code_tv:
//                mIRegisterPresenter.onGetIdentifyCode(phone);
//                break;
//            case R.id.regain_get_tv:
//                mIRegisterPresenter.onGetIdentifyCode(phone);
//                break;
//            case R.id.publish_ll:
////                if (phone.matches(CommonConstant.PHONE_FORMAT_REG_YD)
////                        || phone.matches(CommonConstant.PHONE_FORMAT_REG_YD) ||
////                        phone.matches(CommonConstant.PHONE_FORMAT_REG_YD)) {
////                if (phone.matches(CommonConstant.PHONE_FORMAT_REG)) {
////                    if (phoneCode.length() == 4) {
////                        if (pwd.matches(CommonConstant.PWD_FORMAT_REG)) {
//                mIRegisterPresenter.onRegister(phone, phoneCode, pwd);
////                        } else {
////                            onToast(getString(R.string.pwd_format_not_errect_text));
////                        }
////                    } else {
////                        onToast(getString(R.string.verify_code_format_not_correct_text));
////                    }
////                } else {
////                    onToast(getString(R.string.phone_format_not_correct_text));
////                }
//                break;
//        }
//    }
//
//    @Override
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
