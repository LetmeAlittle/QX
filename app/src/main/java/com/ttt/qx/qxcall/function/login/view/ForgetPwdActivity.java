package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.presenter.ForgetPwdPresenterImpl;
import com.ttt.qx.qxcall.function.login.presenter.IForgetPwdPresenter;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.RxCountDownUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * 忘记密码
 * Created by wyd on 2017/7/28.
 */

public class ForgetPwdActivity extends BaseActivity implements IForgetPwdView {
    private IForgetPwdPresenter mIForgetPwdPresenter;
//    @BindView(R.id.phone_num_et)
//    EditText phone_num_et;
//    @BindView(R.id.phone_identify_code_et)
//    EditText phone_identify_code_et;
//    @BindView(R.id.pwd_et)
//    EditText pwd_et;
//    @BindView(R.id.again_pwd_et)
//    EditText again_pwd_et;
//    @BindView(R.id.regain_get_tv)
//    TextView regain_get_tv;
//    @BindView(R.id.time_tv)
//    TextView time_tv;
//    @BindView(R.id.phone_ll)
//    LinearLayout phone_ll;
//    @BindView(R.id.other_ll)
//    LinearLayout other_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        initData();
//        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIForgetPwdPresenter = new ForgetPwdPresenterImpl(this, this);
    }

//    /**
//     * 初始化view
//     */
//    private void initView() {
//        String phone = getIntent().getStringExtra("phone");
//        if (null != phone) {
//            phone_num_et.setText(phone);
//        }
//    }
//
//    @OnClick({R.id.top_letf_back_iv, R.id.regain_get_tv, R.id.publish_ll, R.id.next_ll})
//    public void click(View v) {
//        String phone = phone_num_et.getText().toString().trim();
//        String phoneCode = phone_identify_code_et.getText().toString().trim();
//        String pwd = pwd_et.getText().toString().trim();
//        String again_pwd = again_pwd_et.getText().toString().trim();
//        switch (v.getId()) {
//            case R.id.top_letf_back_iv:
//                //判断是否是第一次点击返回
//                onFinish();
//                //将当前视图压栈
//                mActivityManager.removeActivityNotFinish(LoginActivity.class);
//                break;
//            case R.id.regain_get_tv:
//                mIForgetPwdPresenter.onGetIdentifyCode(phone);
//                break;
//            case R.id.next_ll:
//                //点击获取验证码
//                mIForgetPwdPresenter.onGetIdentifyCode(phone);
//                break;
//            case R.id.publish_ll:
//                // TODO: 2017/7/28 提交之前校验格式
//                mIForgetPwdPresenter.forgetPwd(phone, phoneCode, pwd, again_pwd);
//                break;
//        }
//    }

    @Override
    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onFinish() {
        //销毁当前
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //将当前视图压栈
        mActivityManager.removeActivityNotFinish(LoginActivity.class);
    }

    @Override
    public void onGetIdentifyCodeCompleted() {
        RxCountDownUtil.countdown(60).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                //开启 计时器 同时 将获取验证码隐藏 可见重新获取以及秒数倒计时控件
//                phone_ll.setVisibility(View.GONE);
//                other_ll.setVisibility(View.VISIBLE);
//                regain_get_tv.setVisibility(View.VISIBLE);
//                regain_get_tv.setClickable(false);
//                regain_get_tv.setTextColor(getResources().getColor(R.color._808A96));
//                time_tv.setVisibility(View.VISIBLE);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
//                //计时完成
//                time_tv.setVisibility(View.INVISIBLE);
//                time_tv.setText("");
//                regain_get_tv.setClickable(true);
//                regain_get_tv.setTextColor(getResources().getColor(R.color._333333));
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer integer) {
                //当前计时
//                time_tv.setText("(" + integer + ")");
            }
        });
    }
}
