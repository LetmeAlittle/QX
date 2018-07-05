package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.InputPhone;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录视图
 * Created by 王亚东 on 2017/10/5.
 */

public class LoginFragment extends Fragment {
    @BindView(R.id.phone_num_et)
    EditText phone_num_et;
    @BindView(R.id.phone_clear_iv)
    ImageView phone_clear_iv;
    @BindView(R.id.pwd_rl)
    RelativeLayout pwd_rl;
    @BindView(R.id.pwd_et)
    EditText pwd_et;
    @BindView(R.id.pwd_clear_iv)
    ImageView pwd_clear_iv;
    @BindView(R.id.failed_iv)
    ImageView failed_iv;
    @BindView(R.id.login_tv)
    TextView login_tv;
    private Context context;
    boolean pwdFailed = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_login, null, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化View
     */
    private void initView() {
        phone_num_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = phone_num_et.getText().toString().trim();
                if (phone.equals("")) {
                    phone_clear_iv.setVisibility(View.INVISIBLE);
                } else {
                    phone_clear_iv.setVisibility(View.VISIBLE);
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
                if (pwdFailed) {//如果用户输入密码错误了，当密码框文本内容改变以后复原背景
                    pwd_rl.setBackgroundResource(R.drawable.fill_bg_f4f8f9_yj);
                    failed_iv.setVisibility(View.INVISIBLE);
                    login_tv.setBackgroundResource(R.drawable.fill_bg_9b5ada_yj);
                    pwdFailed = false;
                    login_tv.setClickable(true);
                }
            }
        });
    }

    @OnClick({R.id.login_tv, R.id.register_tv, R.id.phone_clear_iv, R.id.pwd_clear_iv, R.id.forget_pwd_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.login_tv://登录
                String phone = phone_num_et.getText().toString().trim();
                String pwd = pwd_et.getText().toString().trim();
                if (!phone.equals("")) {
                    if (!pwd.equals("")) {
                        LoginModel.getLoginModel().login(new ProgressSubscribe<>(new SubScribeOnNextListener<LoginedResponse>() {
                            @Override
                            public void onNext(LoginedResponse loginedResponse) {
                                if (loginedResponse.getStatus_code() == 200) {
                                    //通知 MinePager 用户已经登录成功
                                    LoginSuccess loginSuccess = new LoginSuccess();
                                    loginSuccess.token = loginedResponse.getData().getToken();
                                    EventBus.getDefault().post(loginSuccess);
                                    onToast("登录成功！");
                                    ((LoginTransferActivity) context).finish();
                                } else {
                                    onToast(loginedResponse.getMessage());
                                    if (loginedResponse.getMessage().contains("密码错误")) {
                                        //假设密码不正确
                                        login_tv.setClickable(false);
                                        pwdFailed = true;
                                        pwd_rl.setBackgroundResource(R.drawable.side_bg_ff5757_yj);
                                        failed_iv.setVisibility(View.VISIBLE);
                                        login_tv.setBackgroundResource(R.drawable.fill_bg_aabdd1_yj);
                                    }
                                }
                            }
                        }, context), phone, pwd);
                    } else {
                        onToast("密码不能为空！");
                    }
                } else {
                    onToast("手机号不能为空！");
                }
                break;
            case R.id.register_tv://注册新用户
                InputPhone inputPhoneRegister = new InputPhone();
                inputPhoneRegister.type = "注册";
                EventBus.getDefault().post(inputPhoneRegister);
                break;
            case R.id.phone_clear_iv://phone清除
                phone_num_et.setText("");
                break;
            case R.id.pwd_clear_iv://密码清除
                pwd_et.setText("");
                break;
            case R.id.forget_pwd_tv://忘记密码
                InputPhone forgetPwd = new InputPhone();
                forgetPwd.type = "找回密码";
                EventBus.getDefault().post(forgetPwd);
                break;
        }

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
