package com.ysxsoft.qxerkai.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.fragment.OnePage;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * @author zhaozhipeng
 * 首页二级页面---查看更多
 */
public class NOneFragmentMoreActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.rv_more)
    RecyclerView rvMore;

    private YongHuAdapter adapter;
    private ArrayList<Integer> temp = new ArrayList<>();
    private String authorization;
    private int pageIndex=1;
    private int pageTotal=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_none_fragment_more);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            authorization = "Bearer " + userBean.getToken();
        }
        initTitleBar();
        initView();
        initItemData(getIntent().getStringExtra("tag"));
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText(getIntent().getStringExtra("title"));
    }

    private void initView() {
        adapter = new YongHuAdapter(R.layout.fragment_one_item);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                    IntentUtil.jumpIntent(NOneFragmentMoreActivity.this, NLoginActivity.class);
                    return;
                }
                UserListInfo.DataBean.ListBean data= (UserListInfo.DataBean.ListBean) adapter.getData().get(position);
                startActivity(new Intent(NOneFragmentMoreActivity.this, NZhiLiaoActivity.class)
                        .putExtra("id",data.getId())
                        .putExtra("accid",data.getWy_acid()));
            }
        });
        adapter.setOnLoadMoreListener(this,rvMore);
        adapter.isFirstOnly(true);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvMore.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvMore.setAdapter(adapter);
    }

    @Override
    public void onLoadMoreRequested() {
        if(pageIndex<pageTotal){
            pageIndex++;
            initItemData(getIntent().getStringExtra("tag"));
        }else {
            adapter.loadMoreEnd();
        }
    }

    private class YongHuAdapter extends BaseQuickAdapter<UserListInfo.DataBean.ListBean, BaseViewHolder> {

        public YongHuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, UserListInfo.DataBean.ListBean item) {
            FrameLayout flBg = helper.getView(R.id.fl_bg);
            int bgWidth = (int) SystemUtils.getScreenWidth(NOneFragmentMoreActivity.this) / 2 - DimenUtils.dp2px(NOneFragmentMoreActivity.this, 17.5f);
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth / 34 * 45));
            ImageView ivView = helper.getView(R.id.iv_image);
            Glide.with(mContext).load(item.getMember_avatar()).into(ivView);
            ImageView ivCall = helper.getView(R.id.iv_call);
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                        IntentUtil.jumpIntent(NOneFragmentMoreActivity.this, NLoginActivity.class);
                        return;
                    }
                    UserDao userDao = new UserDao();
                    UserBean userBean = userDao.queryFirstData();
                    StealListenModel.getStealListenModel().isAllowTalk(new Subscriber<StandardResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                //调起拨打界面。
                                AVChatProfile.getInstance().setAVChatting(true);
                                AVChatActivity.launch(NOneFragmentMoreActivity.this, item.getWy_acid(), AVChatType.AUDIO.getValue(), AVChatActivity.FROM_INTERNAL);
                            } else {
                                ToastUtils.showToast(NOneFragmentMoreActivity.this, standardResponse.getMessage(), 0);
                            }
                        }
                    }, String.valueOf(item.getId()), "Bearer " + userBean.getToken());
                }
            });
            TextView name = helper.getView(R.id.tv_name);
            name.setText(item.getNick_name());
            TextView age = helper.getView(R.id.tv_age);
            age.setText(item.getMember_age());
            if(item.getMember_sex().equals("1")){
                age.setTextColor(mContext.getResources().getColor(R.color.sex_nan));
            }else if(item.getMember_sex().equals("2")){
                age.setTextColor(mContext.getResources().getColor(R.color.sex_nv));
            }else {
                age.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            TextView price = helper.getView(R.id.tv_price);
            price.setText(item.getMember_price() + "砰砰豆/分钟");
            ImageView ivJiBie = helper.getView(R.id.iv_jibie);
            switch (item.getJb()) {
                case 0:
                    ivJiBie.setVisibility(View.GONE);
                    break;
                case 1:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_qingtong);
                    break;
                case 2:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_baiyin);
                    break;
                case 3:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_huangjin);
                    break;
                case 4:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_bojin);
                    break;
                case 5:
                    ivJiBie.setVisibility(View.VISIBLE);
                    ivJiBie.setImageResource(R.mipmap.lev_zhuansi);
                    break;
            }
        }
    }

    /**
     * 初始化数据
     *
     * @param tag 分类的ID
     */
    private void initItemData(String tag) {
        multipleStatusView.showLoading();
        HomeModel.getHomeModel().getUserList(new Subscriber<UserListInfo>() {
            @Override
            public void onCompleted() {
                LogUtils.e("onCompleted");
                multipleStatusView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("onError"+e.getMessage());
                multipleStatusView.hideLoading();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onNext(UserListInfo userListInfo) {
                LogUtils.e("onNext");
                if (userListInfo.getStatus_code() == 200) {
                    pageTotal=userListInfo.getData().getPage().getLast_page();
                    if(pageIndex==1){
                        adapter.setNewData(userListInfo.getData().getList());
                    }else {
                        adapter.addData(userListInfo.getData().getList());
                        adapter.loadMoreComplete();
                    }
                }else {
                    if(pageIndex>1){
                        pageIndex--;
                    }
                }
            }
        }, tag, "0", ""+pageIndex, authorization);
    }

}
