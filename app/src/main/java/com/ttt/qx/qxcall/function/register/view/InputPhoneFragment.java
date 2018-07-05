package com.ttt.qx.qxcall.function.register.view;

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
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.RegisterOrForgetPwd;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseResult;
import com.ttt.qx.qxcall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册视图
 * Created by 王亚东 on 2017/10/5.
 */

public class InputPhoneFragment extends Fragment {
    @BindView(R.id.phone_num_et)
    EditText phone_num_et;
    @BindView(R.id.phone_clear_iv)
    ImageView phone_clear_iv;
    private Context context;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_input_phone, null, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        type = getArguments().getString("type", "注册");
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

    }

    @OnClick({R.id.confirm_tv, R.id.phone_clear_iv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.confirm_tv://确定 下一步
                String phone = phone_num_et.getText().toString().trim();
                if (!phone.equals("")) {
                    RegisterModel.getRegisterModel().getIdentifyCode(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseResult>() {
                        @Override
                        public void onNext(ResponseResult responseResult) {
                            if (responseResult.isSuccess()) {
                                //通过手机号获取验证码成功之后 跳转至注册页 同时 携带手机号过去
                                RegisterOrForgetPwd registerOrForgetPwd = new RegisterOrForgetPwd();
                                registerOrForgetPwd.type = type;
                                registerOrForgetPwd.phone = phone;
                                EventBus.getDefault().post(registerOrForgetPwd);
                            } else {
                                onToast(responseResult.getMessage());
                            }
                        }
                    }, context), phone);
                } else {
                    onToast("手机号不能为空！");
                }
                break;
            case R.id.phone_clear_iv:
                phone_num_et.setText("");
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
