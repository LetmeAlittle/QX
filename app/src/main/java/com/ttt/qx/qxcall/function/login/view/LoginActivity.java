package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.presenter.ILoginPresenter;
import com.ttt.qx.qxcall.function.login.presenter.LoginPresenterImpl;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.pager.MinePager;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * 登录视图
 * Created by wyd on 2017/7/19.
 */
public class LoginActivity extends BaseActivity implements ILoginView {
//    @BindView(R.id.phone_num_et)
//    EditText phone_num_et;
//    @BindView(R.id.pwd_et)
//    EditText pwd_et;
//    @BindView(R.id.forget_pwd_tv)
//    TextView forget_pwd_tv;
//    @BindView(R.id.login_tv)
//    TextView login_tv;
//    @BindView(R.id.login_ll)
//    LinearLayout login_ll;
//    @BindView(R.id.register_tv)
//    TextView register_tv;
    private ILoginPresenter mLoginPresenter;
    private String mPhone;
    private String mPwd;
    private String mMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        initView();
    }
//
//    @OnClick({R.id.back_del_iv, R.id.secret_ll, R.id.forget_pwd_tv, R.id.login_ll, R.id.register_tv})
//    public void click(View v) {
//        mPhone = phone_num_et.getText().toString().trim();
//        mPwd = pwd_et.getText().toString().trim();
//        switch (v.getId()) {
//            case R.id.secret_ll:
//                Intent intent3 = new Intent(this, AboutActivity.class);
//                intent3.putExtra("mark", "secret_provision");
//                intent3.putExtra("aboutUrl", "http://m.fxbtg.com/pages/privacy.html");
//                startActivity(intent3);
//            case R.id.back_del_iv:
//                //同时从任务栈中移除中转视图
//                if (!message) {
//                    mActivityManager.removeActivityNotFinish(LoginTransferActivity.class);
//                }
//                onFinish();
//                break;
//            case R.id.forget_pwd_tv:
//                Intent intent = new Intent(this, ForgetPwdActivity.class);
//                if (!mPhone.equals("")) {// TODO: 2017/7/28  如果手机号符合规则直接传递给忘记密码页
//                    intent.putExtra("phone", mPhone);
//                }
//                startActivity(intent);
//                //将当前视图压栈
//                mActivityManager.addActivity(this);
//                break;
//            case R.id.login_ll:
//                //登录
////                if (phone.matches(CommonConstant.PHONE_FORMAT_REG_YD)
////                        || phone.matches(CommonConstant.PHONE_FORMAT_REG_YD) ||
////                        phone.matches(CommonConstant.PHONE_FORMAT_REG_YD)) {
//                if (mPhone.matches(CommonConstant.PHONE_FORMAT_REG)) {
////                    if (pwd.matches(CommonConstant.PWD_FORMAT_REG)) {
//                    mLoginPresenter.onLogin(mPhone, mPwd, LoginConstant.LOGIN);
////                    } else {
////                        onToast(getString(R.string.pwd_format_not_errect_text));
////                    }
//                } else {
//                    onToast(getString(R.string.phone_format_not_correct_text));
//                }
//                break;
//            case R.id.register_tv:
//                //跳转至绑定手机页面
//                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
//                intent2.putExtra("direction", "LoginActivity");
//                startActivity(intent2);
//                //将当前视图压栈
//                mActivityManager.addActivity(this);
//                break;
//        }
//    }

    /**
     * 初始化数据
     */
    private void initData() {
        mLoginPresenter = new LoginPresenterImpl(this, this);
        mMark = getIntent().getStringExtra("mark");
    }

    /**
     * 初始化view
     */
    private void initView() {
    }

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
//        //同时从任务栈中移除中转视图
//        if (!message) {
//            mActivityManager.removeActivityNotFinish(LoginTransferActivity.class);
//        }
    }

    @Override
    public void onLoginSuccess(User user) {
        onToast("登录成功！");
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
//        if (userBean == null) {//添加
//            User.DataBean userData = user.getData();
//            userBean = new UserBean();
//            userBean.setHeadImg(userData.getHeadImg());
//            userBean.setNickName(userData.getNickName());
//            userBean.setMobile(userData.getMobile());
//            userBean.setCreateOn(userData.getCreateOn());
//            userBean.setEmail(userData.getEmail() != null ? userData.getEmail().toString() : "");
//            userBean.setUserId(userData.getId());
//            userBean.setLoginIP(userData.getLoginIP());
//            userBean.setLoginName(userData.getLoginName());
//            userBean.setLoginSession(userData.getLoginSession());
//            userBean.setLoginTime(userData.getLoginTime());
//            userBean.setPassportType(userData.getPassportType());
//            userBean.setPassportUpdateTime(userData.getPassportUpdateTime());
//            userBean.setRealName(userData.getRealName() != null ? userData.getRealName().toString() : "");
//            //添加
//            userDao.add(userBean);
//        }
//        login = true;
        //只有来自于交易模块的
        if (mMark != null && mMark.equals("tradePager")) {
            MinePager.tradeLogined = true;
        }
//        //发送给我的页
//        LoginSuccess loginSuccess = new LoginSuccess();
//        loginSuccess.mUser = user;
//        EventBus.getDefault().post(loginSuccess);
//        onFinish();
//        if (!message) {
//            mActivityManager.killActivity(LoginTransferActivity.class);
//        }
    }
}
