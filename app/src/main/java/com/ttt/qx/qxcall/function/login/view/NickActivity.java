package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.model.NickHeadImgModel;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * 昵称修改
 * Created by wyd on 2017/7/28.
 */

public class NickActivity extends BaseActivity {
//    @BindView(R.id.nick_et)
//    EditText nick_et;
//    @BindView(R.id.save_text)
    TextView save_text;
    private String mNick;
    public static final String nickReg = "^[A-Za-z0-9\\u4E00-\\u9FA5]+$";
    private NickHeadImgModel mNickHeadImgModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nick_activity);
        ButterKnife.bind(this);
        initData();
//        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        //保存原始昵称
        mNick = getIntent().getStringExtra("nick");
//        nick_et.setText(mNick);
    }

//    /**
//     * 初始化view
//     */
//    private void initView() {
//        save_text.setClickable(false);
//        nick_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String result = nick_et.getText().toString().trim();
//                if (!result.equals(mNick)) {
//                    save_text.setClickable(true);
//                    save_text.setTextColor(getResources().getColor(R.color._333333));
//                } else {
//                    save_text.setClickable(false);
//                    save_text.setTextColor(getResources().getColor(R.color._818C97));
//                }
//            }
//        });
//    }
//
//    @OnClick({R.id.cancel_tv, R.id.save_text, R.id.del_iv})
//    public void click(View v) {
//        switch (v.getId()) {
//            case R.id.cancel_tv:
//                onFinish();
//                break;
//            case R.id.save_text:
//                String result = nick_et.getText().toString().trim();
//                if (result.matches(nickReg)) {
//                    //调用修改昵称的接口
//                    mNickHeadImgModel = NickHeadImgModel.getNickHeadImgModel();
//                    UserDao userDao=new UserDao();
//                    UserBean userBean = userDao.queryFirstData();
//                    mNickHeadImgModel.changeNickHeadImg(new ProgressSubscribe<>(new SubScribeOnNextListener<ResponseStatus>() {
//                        @Override
//                        public void onNext(ResponseStatus responseStatus) {
//                            if (responseStatus.getStatus().equals("200")) {
//                                if (responseStatus.isIsSuccess()) {
//                                    SetNick nick = new SetNick();
//                                    nick.nick = result;
//                                    EventBus.getDefault().post(nick);
//                                    onToast("昵称设置成功！");
//                                    onFinish();
//                                } else {
//                                    errorMessageShow(responseStatus);
//                                }
//                            } else {
//                                errorMessageShow(responseStatus);
//                            }
//                        }
//                    }, this), "111", result, "1",userBean.getLoginSession());
//                } else {
//                    onToast("昵称只能输入字母、数字、汉字！");
//                }
//                break;
//            case R.id.del_iv:
//                //清空
//                nick_et.setText("");
//                save_text.setClickable(false);
//                save_text.setTextColor(getResources().getColor(R.color._818C97));
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
