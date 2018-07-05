package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * 昵称修改
 * Created by wyd on 2017/7/28.
 */

public class ModifyPwdActivity extends BaseActivity {
//    @BindView(R.id.old_pwd_et)
//    EditText old_pwd_et;
//    @BindView(R.id.new_pwd_et)
//    EditText new_pwd_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化view
     */
    private void initView() {
    }

//    @OnClick({R.id.top_letf_back_iv, R.id.publish_ll})
//    public void click(View v) {
//        String oldPwd = old_pwd_et.getText().toString().trim();
//        String newPwd = new_pwd_et.getText().toString().trim();
//        switch (v.getId()) {
//            case R.id.top_letf_back_iv:
//                onFinish();
//                break;
//            case R.id.publish_ll:
//                if (oldPwd.length() >= 6 && newPwd.length() >= 6) {
//                    if (!oldPwd.equals(newPwd)) {
//                        UserDao userDao=new UserDao();
//                        UserBean userBean = userDao.queryFirstData();
//                        MineModel.getMineModel().modifyPwd(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseStatus>() {
//                            @Override
//                            public void onNext(ResponseStatus responseStatus) {
//                                if (responseStatus.getStatus().equals("200")) {
//                                    if (responseStatus.isIsSuccess()) {
//                                        onFinish();
//                                        onToast("修改成功！");
//                                    } else {
//                                        errorMessageShow(responseStatus);
//                                    }
//                                } else {
//                                    errorMessageShow(responseStatus);
//                                }
//                            }
//                        }, this), oldPwd, newPwd, userBean.getLoginSession());
//                    } else {
//                        onToast("新旧密码不能一致！");
//                    }
//                } else {
//                    onToast("密码长度不能少于6个字符！");
//                }
//                break;
//
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }

    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }
}
