package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.UserTagAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.model.entity.UserTagListResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


/**
 * 设置用户标签
 * Created by wyd on 2017/7/19.
 */
public class SetUserTagActivity extends BaseActivity {
    private Context mContext;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private UserTagAdapter mUserTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_tag);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mContext = this;
        //首先获取 标签
        MineModel.getMineModel().getTagList(new ProgressSubscribe<>(new SubScribeOnNextListener<UserTagListResponse>() {
            @Override
            public void onNext(UserTagListResponse userTagListResponse) {
                if (userTagListResponse.getStatus_code() == 200) {
                    List<UserTagListResponse.DataBean> userTagListResponseData = userTagListResponse.getData();
                    //查询当前用户所选择的
                    UserDao userDao = new UserDao();
                    UserBean userBean = userDao.queryFirstData();
                    String Authorization = "Bearer " + userBean.getToken();
                    HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(UserDetailInfo userDetailInfo) {
                            if (userDetailInfo.getStatus_code() == 200) {
                                UserDetailInfo.DataBean userDetailInfoData = userDetailInfo.getData();
                                List<UserDetailInfo.DataBean.MemberTagBean> member_tag = userDetailInfoData.getMember_tag();
                                mUserTagAdapter = new UserTagAdapter(SetUserTagActivity.this, userTagListResponseData, member_tag, Authorization);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SetUserTagActivity.this, LinearLayoutManager.VERTICAL, false);
                                recycler_view.setLayoutManager(linearLayoutManager);
                                recycler_view.setAdapter(mUserTagAdapter);
                            }
                        }
                    }, "", Authorization);
                }
            }
        }, this));
    }

    /**
     * 初始化view
     */
    private void initView() {
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
