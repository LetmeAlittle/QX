package com.ttt.qx.qxcall.function.login.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.HaveAccount;
import com.ttt.qx.qxcall.eventbus.InputPhone;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.eventbus.RegisterOrForgetPwd;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.ThreeLoginResponse;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UserInfoSave;
import com.ttt.qx.qxcall.function.register.view.InputPhoneFragment;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.AppActivityManager;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.mUmShareAPI;

/**
 * 登录视图中转站
 * Created by wyd on 2017/7/19.
 */
public class LoginTransferActivity extends BaseActivity {

    private static final int BASE_CODE_SUCCESS = 0;
    private Context mContext;
    private final static int QQ = 0;
    private final static int WEIXIN = 1;
    private int LOGING_TYPE = QQ;
    private String mMark;
    private FragmentManager supportFragmentManager;
    @BindView(R.id.title_tv)
    TextView title_tv;
    private List<String> fragmentTitles = new ArrayList<>();
    private RegisterModel registerModel;
    private String base64Encode;
    private String authorization;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_transfer);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    //    @OnClick({R.id.back_del_iv, R.id.secret_ll, R.id.qq_login_ll, R.id.weixin_login_ll, R.id.phone_login_ll})
//    public void click(View v) {
//        switch (v.getId()) {
//            case R.id.back_del_iv:
//                onFinish();
//                break;
//            case R.id.secret_ll:
//                Intent intent = new Intent(this, AboutActivity.class);
//                intent.putExtra("mark", "secret_provision");
//                intent.putExtra("aboutUrl", "http://m.fxbtg.com/pages/privacy.html");
//                startActivity(intent);
//                break;
//            case R.id.qq_login_ll:
//                //使用授权方式
//                LOGING_TYPE = QQ;
//                mUmShareAPI.getPlatformInfo(LoginTransferActivity.this, SHARE_MEDIA.QQ, authListener);
//                break;
//            case R.id.weixin_login_ll:
//                LOGING_TYPE = WEIXIN;
//                mUmShareAPI.getPlatformInfo(LoginTransferActivity.this, SHARE_MEDIA.WEIXIN, authListener);
//                break;
//            case R.id.phone_login_ll:
//                IntentUtil.jumpIntent(this, LoginActivity.class);
//                //将中专页面添加至任务栈中
//                mActivityManager.addActivity(this);
//                break;
//        }
//    }
    @OnClick({R.id.top_back_rl, R.id.qq_login_ll, R.id.wx_login_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                transferHandle();
//                Intent intent = new Intent(this, UserBaseInfoActivity.class);
//                intent.putExtra("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1MDc5ODg4NjEsImV4cCI6MTUwODA3NTI2MSwibmJmIjoxNTA3OTg4ODYxLCJqdGkiOiJJUFhvdUE0NlVMWklNbzBSIiwic3ViIjoxOCwicHJ2IjoiYWZkMGZkOWJkZDlhYzcyZmYzOTgzNDFmMWQ2Mjg0MGNiZjRjNzE2NyJ9.6DrXOXlopUj3R4LNwlATxsZ83xSo0O6FGTPsPkSThZ4");
//                startActivity(intent);
                break;
            case R.id.qq_login_ll:
                //使用授权方式
                LOGING_TYPE = QQ;
                mUmShareAPI.getPlatformInfo(LoginTransferActivity.this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.wx_login_ll:
                LOGING_TYPE = WEIXIN;
                mUmShareAPI.getPlatformInfo(LoginTransferActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    /**
     * 页面切换处理
     */
    private void transferHandle() {
        //首先判断transferFragmentMaps 集合是否只有一个元素
        if (fragmentTitles.size() <= 1) {
            finish();
        } else {
            //移除之前首先获取上次操作 type
            String lastType = fragmentTitles.get(fragmentTitles.size() - 1);
            //否则移除最后一个元素
            fragmentTitles.remove(fragmentTitles.size() - 1);
            //移除之后再获取结果集合最后一个元素
            String title = fragmentTitles.get(fragmentTitles.size() - 1);
            title_tv.setText(title);
            Fragment fragment = null;
            switch (title) {
                case "登录":
                    //如果重新返回到登录页将LoginTransferActivity 保存到堆栈集合中
                    AppActivityManager.getInstance().removeActivityNotFinish(this);
                    fragment = new LoginFragment();
                    break;
                case "填写手机号":
                    fragment = new InputPhoneFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", lastType);
                    fragment.setArguments(bundle);
                    break;
                case "找回密码":
                    break;

            }
            supportFragmentManager.beginTransaction()
                    .replace(R.id.login_register_frame_layout, fragment, title)
                    .commit();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        mMark = getIntent().getStringExtra("mark");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        registerModel = RegisterModel.getRegisterModel();
    }

    /**
     * 初始化view
     */
    private void initView() {
        //初始化登录frament
        supportFragmentManager = getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        supportFragmentManager.beginTransaction()
                .replace(R.id.login_register_frame_layout, loginFragment, "登录")
                .commit();
        //首次添加至集合中
        fragmentTitles.add("登录");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        transferHandle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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
    private Dialog mDialog;

    /**
     * QQ第三方登录
     *
     * @param accessToken
     * @param openId
     * @param key
     */
    private void threeLoginQQHandle(String accessToken, String openId, String key) {
        LoginModel.getLoginModel().qqLogin(new ProgressSubscribe<>(new SubScribeOnNextListener<ThreeLoginResponse>() {
            @Override
            public void onNext(ThreeLoginResponse threeLoginResponse) {
                int code = threeLoginResponse.getStatus_code();
//                threeLoginHandle(user);
            }
        }, LoginTransferActivity.this), openId);
    }

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
                        Toast.makeText(mContext, "QQ登录接口请求失败"+e.getMessage() , Toast.LENGTH_LONG).show();
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
                        Toast.makeText(mContext, "微信登录接口请求失败"+e.getMessage() , Toast.LENGTH_LONG).show();
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

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    private void loginSuccessHandle(String name, String gender, final String iconurl) {
        registerModel.infoSave(new Subscriber<UserInfoSave>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, "用户信息保存失败" , Toast.LENGTH_LONG).show();
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
                            Toast.makeText(mContext, "上传头像接口请求失败" , Toast.LENGTH_LONG).show();
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
    };

    @Subscribe
    public void onEventInputPhone(InputPhone inputPhone) {
        //将LoginTransferActivity 保存到堆栈集合中
        AppActivityManager.getInstance().addActivity(this);
        InputPhoneFragment inputPhoneFragment = new InputPhoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", inputPhone.type);
        inputPhoneFragment.setArguments(bundle);
        supportFragmentManager.beginTransaction()
                .replace(R.id.login_register_frame_layout, inputPhoneFragment, "填写手机号")
                .commitAllowingStateLoss();
        fragmentTitles.add("填写手机号");
        title_tv.setText("填写手机号");
    }

    @Subscribe
    public void onEventRegisterOrForgetPwd(RegisterOrForgetPwd registerOrForgetPwd) {
        RegisterOrForgetPwdFragment registerOrForgetPwdFragment = new RegisterOrForgetPwdFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", registerOrForgetPwd.type);
        bundle.putString("phone", registerOrForgetPwd.phone);
        registerOrForgetPwdFragment.setArguments(bundle);
        String tag = "";
        if (registerOrForgetPwd.type.equals("注册")) {
            tag = "注册";
        } else {
            tag = "找回密码";
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.login_register_frame_layout, registerOrForgetPwdFragment, tag)
                .commitAllowingStateLoss();
        fragmentTitles.add(tag);
        title_tv.setText(tag);
    }

    @Subscribe
    public void onEventHaveAccount(HaveAccount haveAccount) {
        LoginFragment loginFragment = new LoginFragment();
        supportFragmentManager.beginTransaction()
                .replace(R.id.login_register_frame_layout, loginFragment, "登录")
                .commit();
        //从确认密码页 切换过来 清空集合
        fragmentTitles.clear();
        //同时再进行添加
        fragmentTitles.add("登录");
        title_tv.setText("登录");
    }

    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 2017/7/31 QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }
}
