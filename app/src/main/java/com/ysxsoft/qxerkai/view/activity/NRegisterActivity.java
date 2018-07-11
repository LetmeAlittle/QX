package com.ysxsoft.qxerkai.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.FinishLogin;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.eventbus.RegisterOrForgetPwd;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.login.model.entity.ThreeLoginResponse;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseResult;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UserInfoSave;
import com.ttt.qx.qxcall.function.register.view.UserBaseInfoActivity;
import com.ttt.qx.qxcall.utils.AppActivityManager;
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

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.mUmShareAPI;

/**
 * 注册
 *
 * @author zhaozhipeng
 */
public class NRegisterActivity extends NBaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.cb_yonghuxieyi)
    TextView cbYonghuxieyi;
    @BindView(R.id.et_activity_register_username)
    EditText etActivityRegisterUsername;
    @BindView(R.id.et_activity_register_code)
    EditText etActivityRegisterCode;
    @BindView(R.id.tv_activity_register_getcode)
    TextView tvActivityRegisterGetcode;
    @BindView(R.id.et_activity_register_password)
    EditText etActivityRegisterPassword;
    @BindView(R.id.et_activity_register_password_again)
    EditText etActivityRegisterPasswordAgain;
    @BindView(R.id.tv_activity_register_button)
    TextView tvActivityRegisterButton;
    @BindView(R.id.iv_activity_register_qq)
    ImageView ivActivityRegisterQq;
    @BindView(R.id.iv_activity_register_weixin)
    ImageView ivActivityRegisterWeixin;
    @BindView(R.id.iv_activity_register_weibo)
    ImageView ivActivityRegisterWeibo;

    private int time = 60;
    private Handler smsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                time--;
                if (time > 0) {
                    tvActivityRegisterGetcode.setText(time + " s");
                    smsHandler.sendEmptyMessageDelayed(1, 1000);

                } else if (time == 0) {
                    tvActivityRegisterGetcode.setText("获取验证码");
                    tvActivityRegisterGetcode.setClickable(true);
                }
            }
        }
    };

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
        setContentView(R.layout.activity_nregister);
        ButterKnife.bind(this);
        setBackEnable(false);
        initStatusBar();
        initView();
        initData();
    }

    private void initView() {
        llTop.setLayoutParams(new LinearLayout.LayoutParams((int) SystemUtils.getScreenWidth(this), (int) SystemUtils.getScreenHeight(this) * 34 / 133));
        llCenter.setLayoutParams(new LinearLayout.LayoutParams((int) SystemUtils.getScreenWidth(this), (int) SystemUtils.getScreenHeight(this) * 63 / 133));
        llBottom.setLayoutParams(new LinearLayout.LayoutParams((int) SystemUtils.getScreenWidth(this), (int) SystemUtils.getScreenHeight(this) * 36 / 133));
        //首先是拼接字符串
        String content = "<font color=\"#ffffff\">我已阅读并同意</font><font color=\"#fd3d5c\">用户协议</font>";
        cbYonghuxieyi.setText(Html.fromHtml(content));
        tvActivityRegisterGetcode.setOnClickListener(this);
        tvActivityRegisterButton.setOnClickListener(this);
        ivActivityRegisterQq.setOnClickListener(this);
        ivActivityRegisterWeixin.setOnClickListener(this);
        ivActivityRegisterWeibo.setOnClickListener(this);
        cbYonghuxieyi.setOnClickListener(this);
    }

    public void initData() {
        registerModel = RegisterModel.getRegisterModel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.tv_activity_register_getcode:
                String phone = etActivityRegisterUsername.getText().toString().trim();
                if (!phone.equals("")) {
                    RegisterModel.getRegisterModel().getIdentifyCode(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseResult>() {
                        @Override
                        public void onNext(ResponseResult responseResult) {
                            if (responseResult.isSuccess()) {
                                //通过手机号获取验证码成功之后 跳转至注册页 同时 携带手机号过去
                                tvActivityRegisterGetcode.setClickable(false);
                                time = 60;
                                smsHandler.sendEmptyMessage(1);
                            } else {
                                onToast(responseResult.getMessage());
                            }
                        }
                    }, this), phone);
                } else {
                    onToast("手机号不能为空！");
                }
                break;
            //注册
            case R.id.tv_activity_register_button:
                String verifyCode = etActivityRegisterCode.getText().toString().trim();
                String pwd = etActivityRegisterPassword.getText().toString().trim();
                String confirmPwd = etActivityRegisterPasswordAgain.getText().toString().trim();
                String etPhone=etActivityRegisterUsername.getText().toString().trim();
                if(etPhone.equals("")){
                    onToast("手机号不能为空！");
                    return;
                }
                if (!verifyCode.equals("")) {
                    if (!pwd.equals("") && !confirmPwd.equals("")) {
                        if (confirmPwd.equals(pwd)) {
                            RegisterModel.getRegisterModel().register(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                @Override
                                public void onNext(StandardResponse standardResponse) {
                                    if (standardResponse.getStatus_code() == 200) {
                                        //注册成功之后 跳转到用户基本信息填写页
                                        startActivity(new Intent(NRegisterActivity.this,NRegisterUserEditActivity.class)
                                                .putExtra("token",standardResponse.getData().getToken())
                                                .putExtra("phone",etPhone)
                                                .putExtra("pwd",pwd));
                                        finish();
                                    } else {
                                        onToast(standardResponse.getMessage());
                                    }
                                }
                            }, NRegisterActivity.this), etPhone, verifyCode, pwd);
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
            case R.id.iv_activity_register_qq:
                LOGING_TYPE = QQ;
                mUmShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.iv_activity_register_weixin:
                LOGING_TYPE = WEIXIN;
                mUmShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.iv_activity_register_weibo:
                break;
            case R.id.cb_yonghuxieyi:
                startActivity(new Intent(this,NUserProtocolActivity.class));
                break;
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this, message, 0);
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
//            Toast.makeText(mContext, "授权开始", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Toast.makeText(mContext, "授权成功", Toast.LENGTH_LONG).show();
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
//            Toast.makeText(mContext, "授权错误" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(mContext, "授权取消" , Toast.LENGTH_LONG).show();
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
                                EventBus.getDefault().post(new FinishLogin());
                                finish();
                            } else {
                                closeDialog();
                                onToast("用户头像设置失败！");
                            }
                        }
                    }, base64Encode, authorization);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 2017/7/31 QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
