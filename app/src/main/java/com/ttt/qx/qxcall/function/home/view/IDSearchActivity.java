package com.ttt.qx.qxcall.function.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.login;


/**
 * 用户个人信息
 * Created by wyd on 2017/7/19.
 */
public class IDSearchActivity extends BaseActivity {

    private Context mContext;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.id_et)
    EditText id_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_search);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.search_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.search_tv:
                if (!login) {
                    IntentUtil.jumpIntent(this, LoginTransferActivity.class);
                } else {
                    //根据id 查询搜索
                    String id = id_et.getText().toString().trim();
                    HomeModel.getHomeModel().filterUserList(new Subscriber<UserListInfo>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(UserListInfo userListInfo) {
                            if (userListInfo.getStatus_code() == 200) {
                                UserListInfo.DataBean data = userListInfo.getData();
                                if (data.getList() != null) {
                                    if (data.getList().size() > 0) {
                                        UserListInfo.DataBean.ListBean listBean = data.getList().get(0);
                                        //搜索成功 之后 直接跳转到 改用户的主页
                                        Intent intent = new Intent(IDSearchActivity.this, UserMainActivity.class);
                                        intent.putExtra("id", listBean.getId());//用户id
                                        intent.putExtra("accid", listBean.getWy_acid());//网易accid
                                        startActivity(intent);
                                    }
                                } else {
                                    onToast("未搜索到数据！");
                                }
                            } else {
                                onToast("请求数据失败！" + userListInfo.getStatus_code());
                            }
                        }
                    }, id, "","1");
                }
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
    }

    /**
     * 初始化view
     */
    private void initView() {
        search_tv.setClickable(false);
        id_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = id_et.getText().toString().trim();
                if (!content.equals("")) {
                    search_tv.setClickable(true);
                    search_tv.setBackgroundResource(R.drawable.fill_bg_9b5ada_yj);
                } else {
                    search_tv.setClickable(false);
                    search_tv.setBackgroundResource(R.drawable.fill_bg_cdcdcd_yj);
                }
            }
        });
    }


    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
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
