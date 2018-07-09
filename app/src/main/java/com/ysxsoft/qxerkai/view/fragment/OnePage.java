package com.ysxsoft.qxerkai.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.TabsAdapter;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.SetSelectItem;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;
import com.ttt.qx.qxcall.function.home.view.HomeCategoryFragment;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.widget.viewhelper.TabLayoutHelper;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.NLoginActivity;
import com.ysxsoft.qxerkai.view.activity.NOneFragmentMoreActivity;
import com.ysxsoft.qxerkai.view.activity.NPaoHaTiActivity;
import com.ysxsoft.qxerkai.view.activity.NPengYouQuanActivity;
import com.ysxsoft.qxerkai.view.activity.NTouTingActivity;
import com.ysxsoft.qxerkai.view.activity.NYiJianPiPeiActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.SaGouLiangActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.MyScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.ttt.qx.qxcall.pager.HomePager.sex;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class OnePage extends BasePager implements View.OnClickListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.rv_xiaoxiannv)
    RecyclerView rvXiaoxiannv;
    @BindView(R.id.rv_xiaogege)
    RecyclerView rvXiaogege;
    @BindView(R.id.rv_yujie)
    RecyclerView rvYujie;
    @BindView(R.id.rv_dashu)
    RecyclerView rvDashu;
    @BindView(R.id.rv_shengyou)
    RecyclerView rvShengyou;
    @BindView(R.id.ll_center)
    LinearLayout llCenter;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.ll_paohuati)
    LinearLayout llPaohuati;
    @BindView(R.id.ll_pipei)
    LinearLayout llPipei;
    @BindView(R.id.ll_xiaoxiannv)
    LinearLayout llXiaoxiannv;
    @BindView(R.id.ll_xiaogege)
    LinearLayout llXiaogege;
    @BindView(R.id.ll_yujie)
    LinearLayout llYujie;
    @BindView(R.id.ll_dashu)
    LinearLayout llDashu;
    @BindView(R.id.ll_shengyou)
    LinearLayout llShengyou;
    @BindView(R.id.ll_pengyouquan)
    LinearLayout llPengyouquan;
    @BindView(R.id.ll_pengyouquan2)
    LinearLayout llPengyouquan2;
    @BindView(R.id.ll_shagouliang)
    LinearLayout llShagouliang;
    @BindView(R.id.ll_shagouliang2)
    LinearLayout llShagouliang2;
    @BindView(R.id.ll_toutingyou)
    LinearLayout llToutingyou;
    @BindView(R.id.ll_toutingyo2)
    LinearLayout llToutingyo2;

    private View rootView;
    private YongHuAdapter adapter1;
    private YongHuAdapter adapter2;
    private YongHuAdapter adapter3;
    private YongHuAdapter adapter4;
    private YongHuAdapter adapter5;

    private String authorization = "";

    public OnePage(Context ctx) {
        super(ctx);
    }

    //不同分类对应的id，这里写成了死的。
    private String xiaojiejie="46"; //小姐姐
    private String xiaogege="45";   //小哥哥
    private String yujie="48";        //御姐
    private String dashu="47";        //大叔
    private String shengyou="49";     //声优

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_one, null);
        ButterKnife.bind(this, rootView);
        initStatusBar(statusBar);
        initTitleBar();
        initRv();
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            authorization = "Bearer " + userBean.getToken();
        }
        llPaohuati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NPaoHaTiActivity.class));
            }
        });
        llPipei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NYiJianPiPeiActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        });
        llXiaoxiannv.setOnClickListener(this);
        llXiaogege.setOnClickListener(this);
        llYujie.setOnClickListener(this);
        llDashu.setOnClickListener(this);
        llShengyou.setOnClickListener(this);
        llPengyouquan.setOnClickListener(new ItemClickListener());
        llPengyouquan2.setOnClickListener(new ItemClickListener());
        llShagouliang.setOnClickListener(new ItemClickListener());
        llShagouliang2.setOnClickListener(new ItemClickListener());
        llToutingyou.setOnClickListener(new ItemClickListener());
        llToutingyo2.setOnClickListener(new ItemClickListener());
        return rootView;
    }

    private void initRv() {
        adapter1 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter2 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter3 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter4 = new YongHuAdapter(R.layout.fragment_one_item);
        adapter5 = new YongHuAdapter(R.layout.fragment_one_item);
        rvXiaoxiannv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvXiaogege.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvYujie.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvDashu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvShengyou.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvXiaoxiannv.setNestedScrollingEnabled(false);
        rvXiaogege.setNestedScrollingEnabled(false);
        rvYujie.setNestedScrollingEnabled(false);
        rvDashu.setNestedScrollingEnabled(false);
        rvShengyou.setNestedScrollingEnabled(false);
        rvXiaoxiannv.setAdapter(adapter1);
        rvXiaogege.setAdapter(adapter2);
        rvYujie.setAdapter(adapter3);
        rvDashu.setAdapter(adapter4);
        rvShengyou.setAdapter(adapter5);
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                    IntentUtil.jumpIntent(ctx, NLoginActivity.class);
                    return;
                }
                UserListInfo.DataBean.ListBean data=adapter1.getData().get(position);
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class)
                        .putExtra("id",data.getId())
                        .putExtra("accid",data.getWy_acid()));
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                    IntentUtil.jumpIntent(ctx, NLoginActivity.class);
                    return;
                }
                UserListInfo.DataBean.ListBean data=adapter2.getData().get(position);
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class)
                        .putExtra("id",data.getId())
                        .putExtra("accid",data.getWy_acid()));
            }
        });
        adapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                    IntentUtil.jumpIntent(ctx, NLoginActivity.class);
                    return;
                }
                UserListInfo.DataBean.ListBean data=adapter3.getData().get(position);
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class)
                        .putExtra("id",data.getId())
                        .putExtra("accid",data.getWy_acid()));
            }
        });
        adapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                    IntentUtil.jumpIntent(ctx, NLoginActivity.class);
                    return;
                }
                UserListInfo.DataBean.ListBean data=adapter4.getData().get(position);
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class)
                        .putExtra("id",data.getId())
                        .putExtra("accid",data.getWy_acid()));
            }
        });
        adapter5.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!QXCallApplication.login) {//如果是没有登录直接跳转至登陆页
                    IntentUtil.jumpIntent(ctx, NLoginActivity.class);
                    return;
                }
                UserListInfo.DataBean.ListBean data=adapter5.getData().get(position);
                ctx.startActivity(new Intent(ctx, NZhiLiaoActivity.class)
                        .putExtra("id",data.getId())
                        .putExtra("accid",data.getWy_acid()));
            }
        });
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("砰砰");
    }

    @Override
    public void initData() {
//        initContent();
        initItemData(xiaojiejie); //小姐姐
        initItemData(xiaogege); //小哥哥
        initItemData(yujie); //御姐
        initItemData(dashu); //大叔
        initItemData(shengyou); //声优
        tvGonggao.setSelected(true);
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY > llCenter.getY()) {
                    llTop.setVisibility(View.VISIBLE);
                } else {
                    llTop.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        String title = "";
        String tag="";
        switch (view.getId()) {
            case R.id.ll_xiaoxiannv:
                title = "小姐姐";
                tag=xiaojiejie;
                break;
            case R.id.ll_xiaogege:
                title = "小哥哥";
                tag=xiaogege;
                break;
            case R.id.ll_yujie:
                title = "御姐";
                tag=yujie;
                break;
            case R.id.ll_dashu:
                title = "大叔";
                tag=dashu;
                break;
            case R.id.ll_shengyou:
                title = "声优";
                tag=shengyou;
                break;
        }
        ctx.startActivity(new Intent(ctx, NOneFragmentMoreActivity.class)
                .putExtra("title", title)
                .putExtra("tag",tag));
    }

    private class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_pengyouquan:
                case R.id.ll_pengyouquan2:
                    ctx.startActivity(new Intent(ctx, NPengYouQuanActivity.class));
                    break;
                case R.id.ll_shagouliang:
                case R.id.ll_shagouliang2:
                    ctx.startActivity(new Intent(ctx, SaGouLiangActivity.class));
                    break;
                case R.id.ll_toutingyou:
                case R.id.ll_toutingyo2:
                    ctx.startActivity(new Intent(ctx, NTouTingActivity.class));
                    break;
            }
        }
    }

    private class YongHuAdapter extends BaseQuickAdapter<UserListInfo.DataBean.ListBean, BaseViewHolder> {

        public YongHuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, UserListInfo.DataBean.ListBean item) {
            FrameLayout flBg = helper.getView(R.id.fl_bg);
            int bgWidth = (int) SystemUtils.getScreenWidth(activity) / 2 - DimenUtils.dp2px(ctx, 17.5f);
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth / 34 * 45));
            ImageView ivView = helper.getView(R.id.iv_image);
            Glide.with(mContext).load(item.getMember_avatar()).into(ivView);
            ImageView ivCall = helper.getView(R.id.iv_call);
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctx.startActivity(new Intent(ctx, NHuaLiaoActivity.class));
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

        }
    }

    /**
     * 初始tab对应的fragment
     */
    private void initContent() {
        //获取首页标签分类列表
        HomeModel.getHomeModel().getHomeTagList(new Subscriber<CommonTagList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(CommonTagList commonTagList) {
                if (commonTagList.getStatus_code() == 200) {
                    LogUtils.e(commonTagList.toString());
                } else {
                }
            }
        });
    }

    /**
     * 初始化数据
     *
     * @param tag 分类的ID
     */
    private void initItemData(String tag) {
        Dialog loadingDialog=CustomAlertDialogUtil.createLoadingDialog(ctx, "加载中...", false);
        loadingDialog.show();
        HomeModel.getHomeModel().getUserList(new Subscriber<UserListInfo>() {
            @Override
            public void onCompleted() {
                if(loadingDialog!=null){
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(loadingDialog!=null){
                    loadingDialog.dismiss();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onNext(UserListInfo userListInfo) {
                if (userListInfo.getStatus_code() == 200) {
                    ArrayList<UserListInfo.DataBean.ListBean> temp = new ArrayList<>();
                    if (userListInfo.getData().getList().size() > 4) {
                        temp.addAll(userListInfo.getData().getList().subList(0, 4));
                    } else {
                        temp.addAll(userListInfo.getData().getList());
                    }
                    if(tag.equals(xiaojiejie)){
                        adapter1.setNewData(temp);
                    }else if(tag.equals(xiaogege)){
                        adapter2.setNewData(temp);
                    }else if(tag.equals(yujie)){
                        adapter3.setNewData(temp);
                    }else if(tag.equals(dashu)){
                        adapter4.setNewData(temp);
                    }else if(tag.equals(shengyou)){
                        adapter5.setNewData(temp);
                    }
                }
            }
        }, tag, "0", "1", authorization);
    }

}
