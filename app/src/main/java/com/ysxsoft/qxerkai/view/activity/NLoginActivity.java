package com.ysxsoft.qxerkai.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.FinishLogin;
import com.ttt.qx.qxcall.eventbus.InputPhone;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.login.model.entity.ThreeLoginResponse;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UserInfoSave;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.mUmShareAPI;

/**
 * 登录
 *
 * @author zhaozhipeng
 */
public class NLoginActivity extends NBaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.et_activity_login_username)
    EditText etActivityLoginUsername;
    @BindView(R.id.et_activity_login_password)
    EditText etActivityLoginPassword;
    @BindView(R.id.tv_activity_login_button)
    TextView tvActivityLoginButton;
    @BindView(R.id.iv_activity_login_qq)
    ImageView ivActivityLoginQq;
    @BindView(R.id.iv_activity_login_weixin)
    ImageView ivActivityLoginWeixin;
    @BindView(R.id.iv_activity_login_weibo)
    ImageView ivActivityLoginWeibo;
    @BindView(R.id.phone_clear_iv)
    ImageView phoneClearIv;
    @BindView(R.id.pwd_clear_iv)
    ImageView pwdClearIv;

    private RegisterModel registerModel;
    private String base64Encode;
    private String authorization;
    private String token;
    private final static int QQ = 0;
    private final static int WEIXIN = 1;
    private int LOGING_TYPE = QQ;
    private Dialog mDialog;
    private static final int BASE_CODE_SUCCESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nlogin);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        setBackEnable(false);
        initStatusBar();
        initView();
        initData();
    }

    public void initView() {
        llTop.setLayoutParams(new LinearLayout.LayoutParams((int) SystemUtils.getScreenWidth(this), (int) SystemUtils.getScreenHeight(this) * 40 / 134));
        llCenter.setLayoutParams(new LinearLayout.LayoutParams((int) SystemUtils.getScreenWidth(this), (int) SystemUtils.getScreenHeight(this) * 41 / 134));
        llBottom.setLayoutParams(new LinearLayout.LayoutParams((int) SystemUtils.getScreenWidth(this), (int) SystemUtils.getScreenHeight(this) * 53 / 134));
        ivActivityLoginQq.setOnClickListener(this);
        ivActivityLoginWeixin.setOnClickListener(this);
        ivActivityLoginWeibo.setOnClickListener(this);
        tvActivityLoginButton.setOnClickListener(this);
        phoneClearIv.setOnClickListener(this);
        pwdClearIv.setOnClickListener(this);
        etActivityLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = etActivityLoginUsername.getText().toString().trim();
                if (phone.equals("")) {
                    phoneClearIv.setVisibility(View.INVISIBLE);
                } else {
                    phoneClearIv.setVisibility(View.VISIBLE);
                }
            }
        });
        etActivityLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pwd = etActivityLoginPassword.getText().toString().trim();
                if (pwd.equals("")) {
                    pwdClearIv.setVisibility(View.INVISIBLE);
                } else {
                    pwdClearIv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initData() {
        registerModel = RegisterModel.getRegisterModel();
    }

    public void onRegister(View view) {
        startActivity(new Intent(this, NRegisterActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //QQ登录
            case R.id.iv_activity_login_qq:
                LOGING_TYPE = QQ;
                mUmShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
                break;
            //微信
            case R.id.iv_activity_login_weixin:
                LOGING_TYPE = WEIXIN;
                mUmShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            //微博
            case R.id.iv_activity_login_weibo:
                break;
            //登录
            case R.id.tv_activity_login_button:
                onHttpLogin();
                break;
            //清除账号
            case R.id.phone_clear_iv:
                etActivityLoginUsername.setText("");
                break;
            //清除密码
            case R.id.pwd_clear_iv:
                etActivityLoginPassword.setText("");
                break;
        }
    }

    private void onHttpLogin() {
        String phone = etActivityLoginUsername.getText().toString().trim();
        String pwd = etActivityLoginPassword.getText().toString().trim();
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
                            finish();
                        } else {
                            onToast(loginedResponse.getMessage());
                        }
                    }
                }, this), phone, pwd);
            } else {
                onToast("密码不能为空！");
            }
        } else {
            onToast("手机号不能为空！");
        }
    }

    /**
     * 第三方登录授权监听
     */
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            Toast.makeText(NLoginActivity.this, "授权开始", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Toast.makeText(NLoginActivity.this, "授权成功", Toast.LENGTH_LONG).show();
            String accessToken = data.get("accessToken").toString();
            String openId = data.get("openid").toString();
            threeLoginHandle(accessToken, data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Toast.makeText(NLoginActivity.this, "授权错误" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(NLoginActivity.this, "授权取消" , Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 微信三方登录
     *
     * @param accessToken
     * @param data
     */
    private void threeLoginHandle(String accessToken, Map<String, String> data) {
        showDialog();
        String openId = data.get("openid").toString();
        String iconurl = data.get("iconurl").toString();
        String name = data.get("name").toString();
        String gender = data.get("gender").toString();
        switch (LOGING_TYPE) {
            case QQ:
                LoginModel.getLoginModel().qqLogin(new Subscriber<ThreeLoginResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onToast("QQ登录接口请求失败"+ e.getMessage());
                        closeDialog();
                    }

                    @Override
                    public void onNext(ThreeLoginResponse threeLoginResponse) {
                        loginedHandle(threeLoginResponse, name, gender, iconurl);
                    }
                }, openId);
                break;
            case WEIXIN:
                LoginModel.getLoginModel().weixinLogin(new Subscriber<ThreeLoginResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onToast("微信登录接口请求失败"+ e.getMessage());
                        closeDialog();
                    }

                    @Override
                    public void onNext(ThreeLoginResponse threeLoginResponse) {
                        loginedHandle(threeLoginResponse, name, gender, iconurl);
                    }
                }, accessToken, openId);
                break;
        }
    }

    private void showDialog() {
        if (mDialog == null) {
            mDialog = CustomAlertDialogUtil.createLoadingDialog(this, "加载中...", false);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 第三方登录后处理
     *
     * @param threeLoginResponse
     * @param name
     * @param gender
     * @param iconurl
     */
    private void loginedHandle(ThreeLoginResponse threeLoginResponse, String name, String gender, String iconurl) {
        int code = threeLoginResponse.getStatus_code();
        if (code == 200) {
            authorization = "Bearer " + threeLoginResponse.getData().getToken();
            token = threeLoginResponse.getData().getToken();
            loginSuccessHandle(name, gender, iconurl);
        } else {
            closeDialog();
            onToast("登录接口请求失败！");
        }
    }

    private void loginSuccessHandle(String name, String gender, final String iconurl) {
        registerModel.infoSave(new Subscriber<UserInfoSave>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                onToast("用户信息保存失败");
                closeDialog();
            }

            @Override
            public void onNext(UserInfoSave userInfoSave) {
                if (userInfoSave.getStatus_code() == 200) {
                    new Thread() {
                        @Override
                        public void run() {
                            Bitmap bitmap = ImageUtil.getBitmap(iconurl);
                            base64Encode = ImageUtil.base64Encode(bitmap);
                            //微信登录 成功后 调用上传头像以及用户信息保存接口
                            Message message = Message.obtain();
                            message.what = BASE_CODE_SUCCESS;
                            handler.sendMessage(message);
                        }
                    }.start();
                } else {
                    closeDialog();
                    onToast("保存昵称信息失败！" + userInfoSave.getStatus_code());
                }
            }
        }, name, gender.equals("男") ? "1" : "2", "", authorization);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BASE_CODE_SUCCESS:
                    registerModel.uploadHeadImg(new Subscriber<UploadImgResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            onToast("上传头像接口请求失败");
                            closeDialog();
                        }

                        @Override
                        public void onNext(UploadImgResponse uploadImgResponse) {
                            if (uploadImgResponse.getStatus_code() == 200) {
                                closeDialog();
                                LoginSuccess loginSuccess = new LoginSuccess();
                                loginSuccess.token = token;
                                EventBus.getDefault().post(loginSuccess);
                                onFinish();
                            } else {
                                closeDialog();
                                onToast("用户头像设置失败！");
                            }
                        }
                    }, base64Encode, authorization);
                    break;
            }
        }
    }; @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 2017/7/31 QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this,message,0);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(FinishLogin status){
        finish();
    }

}
