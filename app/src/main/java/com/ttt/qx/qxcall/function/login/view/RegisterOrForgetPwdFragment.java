package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.HaveAccount;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseResult;
import com.ttt.qx.qxcall.function.register.view.UserBaseInfoActivity;
import com.ttt.qx.qxcall.utils.AppActivityManager;
import com.ttt.qx.qxcall.utils.RxCountDownUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;


/**
 * 注册或者找回密码视图
 * Created by 王亚东 on 2017/10/5.
 */

public class RegisterOrForgetPwdFragment extends Fragment {
    @BindView(R.id.verify_code_et)
    EditText verify_code_et;
    @BindView(R.id.verify_code_clear_iv)
    ImageView verify_code_clear_iv;
    @BindView(R.id.get_verify_code_tv)
    TextView get_verify_code_tv;
    @BindView(R.id.pwd_et)
    EditText pwd_et;
    @BindView(R.id.pwd_clear_iv)
    ImageView pwd_clear_iv;
    @BindView(R.id.confirm_pwd_et)
    EditText confirm_pwd_et;
    @BindView(R.id.confirm_pwd_clear_iv)
    ImageView confirm_pwd_clear_iv;
    private Context context;
    boolean pwdFailed = false;
    //用户注册手机号
    private String phone;
    //操作类别
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_register_forget_pwd, null, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        phone = getArguments().getString("phone");
        type = getArguments().getString("type");
    }

    /**
     * 初始化View
     */
    private void initView() {
        //跳转过来首先显示倒计时
        timerShow();
        verify_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String verifyCode = verify_code_et.getText().toString().trim();
                if (verifyCode.equals("")) {
                    verify_code_clear_iv.setVisibility(View.INVISIBLE);
                } else {
                    verify_code_clear_iv.setVisibility(View.VISIBLE);
                }
            }
        });
        pwd_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pwd = pwd_et.getText().toString().trim();
                if (pwd.equals("")) {
                    pwd_clear_iv.setVisibility(View.INVISIBLE);
                } else {
                    pwd_clear_iv.setVisibility(View.VISIBLE);
                }
            }
        });
        confirm_pwd_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String confirmPwd = confirm_pwd_et.getText().toString().trim();
                if (confirmPwd.equals("")) {
                    confirm_pwd_clear_iv.setVisibility(View.INVISIBLE);
                } else {
                    confirm_pwd_clear_iv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.submit_tv, R.id.pwd_clear_iv, R.id.confirm_pwd_clear_iv, R.id.have_account_tv
            , R.id.forget_pwd_tv, R.id.verify_code_clear_iv, R.id.get_verify_code_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.submit_tv://提交
                String verifyCode = verify_code_et.getText().toString().trim();
                String pwd = pwd_et.getText().toString().trim();
                String confirmPwd = confirm_pwd_et.getText().toString().trim();
                if (!verifyCode.equals("")) {
                    if (!pwd.equals("") && !confirmPwd.equals("")) {
                        if (confirmPwd.equals(pwd)) {
                            if (type.equals("注册")) {
                                RegisterModel.getRegisterModel().register(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                    @Override
                                    public void onNext(StandardResponse standardResponse) {
                                        if (standardResponse.getStatus_code() == 200) {
                                            //注册成功之后 跳转到用户基本信息填写页
                                            Intent intent = new Intent(context, UserBaseInfoActivity.class);
                                            intent.putExtra("token", standardResponse.getData().getToken());
                                            intent.putExtra("phone", phone);
                                            intent.putExtra("pwd", pwd);
                                            context.startActivity(intent);
                                        } else {
                                            onToast(standardResponse.getMessage());
                                        }
                                    }
                                }, context), phone, verifyCode, pwd);
                            } else {//找回密码
                                RegisterModel.getRegisterModel().resetPwd(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                    @Override
                                    public void onNext(StandardResponse standardResponse) {
                                        if (standardResponse.getStatus_code() == 200) {
                                            //找回密码成功自动调用登录接口
                                            LoginModel.getLoginModel().login(new ProgressSubscribe<>(new SubScribeOnNextListener<LoginedResponse>() {
                                                @Override
                                                public void onNext(LoginedResponse loginedResponse) {
                                                    if (loginedResponse.getStatus_code() == 200) {
                                                        //通知 MinePager 用户已经登录成功
                                                        LoginSuccess loginSuccess = new LoginSuccess();
                                                        loginSuccess.token = loginedResponse.getData().getToken();
                                                        EventBus.getDefault().post(loginSuccess);
                                                        onToast("密码重置成功！");
                                                        AppActivityManager.getInstance().killActivity(LoginTransferActivity.class);
                                                    }
                                                }
                                            }, context), phone, pwd);
                                        } else {
                                            onToast(standardResponse.getMessage());
                                        }
                                    }
                                }, context), phone, verifyCode, pwd);
                            }
                        } else {
                            onToast("两次输入密码不一致！");
                        }
                    } else {
                        onToast("密码不能为空！");
                    }
                } else {
                    onToast("验证码不能为空！");
                }
                break;
            case R.id.confirm_pwd_clear_iv:
                confirm_pwd_et.setText("");
                break;
            case R.id.pwd_clear_iv:
                pwd_et.setText("");
                break;
            case R.id.verify_code_clear_iv:
                verify_code_et.setText("");
                break;
            case R.id.get_verify_code_tv:
                RegisterModel.getRegisterModel().getIdentifyCode(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseResult>() {
                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (responseResult.isSuccess()) {
                            //计时结束 之后重新调用获取验证码之后再进行计时
                            timerShow();
                        } else {
                            onToast(responseResult.getMessage());
                        }
                    }
                }, context), phone);
                break;
            case R.id.have_account_tv:
                EventBus.getDefault().post(new HaveAccount());
                break;
            case R.id.forget_pwd_tv:

                break;
        }

    }

    /**
     * 显示倒计时
     */
    private void timerShow() {
        RxCountDownUtil.countdown(60).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                get_verify_code_tv.setClickable(false);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                //计时完成
                get_verify_code_tv.setText("重新获取");
                get_verify_code_tv.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer integer) {
                //当前计时
                get_verify_code_tv.setText("(" + integer + "s" + ")");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(context, message, Toast.LENGTH_SHORT);
    }
}
