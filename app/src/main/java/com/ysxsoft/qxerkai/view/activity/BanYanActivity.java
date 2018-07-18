package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.net.response.SearchListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BanYanActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    public static final String TYPE_BY = "TYPE_BY";
    //1111
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_hao_you)
    LinearLayout llHaoYou;
    @BindView(R.id.ll_fu_jin)
    LinearLayout llFuJin;
    @BindView(R.id.et_public_titlebar)
    EditText etPublicTitlebar;


    private int pageType = 0,pageIndex = 1,pageTotal = 1,urlType = 0;
    private int picLeft = R.mipmap.touxiang_dashu, picRight = R.mipmap.touxiang_luoli;
    private String user_id= "";

    private OnChooseClick onChooseClick;
    private BanYanAdapter adapter;
    private HashMap<String,String> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_yan);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
//        initTitleBar();
        initView();
        initData();
    }

   /* private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("");
    }*/

    private void initView() {
        etPublicTitlebar.setClickable(true);
        etPublicTitlebar.setFocusable(false);

        user_id = DBUtils.getUserId();

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BanYanAdapter(R.layout.activity_xuan_zhe_shou_hu_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        swipeTarget.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this,swipeTarget);

        Intent intent = getIntent();
        pageType = intent.getIntExtra(TYPE_BY, 0);
        switch (pageType) {
            case 0://大叔---萝莉
                picLeft = R.mipmap.touxiang_dashu;
                picRight = R.mipmap.touxiang_luoli;
                break;
            case 1://教师---学生
                picLeft = R.mipmap.touxiang_jiaoshi;
                picRight = R.mipmap.touxiang_xuesheng;
                break;
            case 2://空姐---乘客
                picLeft = R.mipmap.touxing_kongjie;
                picRight = R.mipmap.touxing_chengke;
                break;
            case 3://老板---秘书
                picLeft = R.mipmap.touxiang_laoban;
                picRight = R.mipmap.touxing_mishu;
                break;
            case 4://护士---病人
                picLeft = R.mipmap.touxing_hushi;
                picRight = R.mipmap.touxing_bingren;
                break;
            case 5://亲王---宠妃
                picLeft = R.mipmap.touxing_qinwang;
                picRight = R.mipmap.touxing_chongfei;
                break;
            default:
                break;
        }

        ivLeft.setImageResource(picLeft);
        ivRight.setImageResource(picRight);

        urlType = 0;
    }



    private void initData() {
        pageIndex =1;
        if (urlType == 0) {
            getHyData();
        } else {
            getFjData();
        }

        onChooseClick = new OnChooseClick() {
            @Override
            public void onClick(HaoYouListResponse.DataBeanX.DataBean item) {
                showToast(item.getNick_name());
            }
        };
    }

    @OnClick({R.id.iv_public_titlebar_left_1, R.id.et_public_titlebar,
            R.id.tv_hy, R.id.tv_fj,R.id.tv_sysPipei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_public_titlebar_left_1:
                finish();
                break;
            case R.id.et_public_titlebar:
                startActivity(new Intent(this,NBanYanSearchActivity.class));
                break;
            case R.id.tv_hy://我的好友
                if (llHaoYou.getVisibility() != View.VISIBLE )  {
                    urlType =0;
                    llHaoYou.setVisibility(View.VISIBLE);
                    llFuJin.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_fj://附近的人
                if (llFuJin.getVisibility() != View.VISIBLE)  {
                    urlType =1;
                    llFuJin.setVisibility(View.VISIBLE);
                    llHaoYou.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_sysPipei://系统匹配
                //TODO   系统匹配

                break;
        }
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            if (urlType == 0) {
                getHyData();
            } else {
                getFjData();
            }
        } else {
            adapter.loadMoreEnd();
        }
    }


    //获取好友列表数据
    private void getHyData() {
        map.clear();
        map.put("user_id",user_id);
        map.put("page",pageIndex+"");

        RetrofitTools.getHaoYouList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
            @Override
            public void onSuccess(HaoYouListResponse response, int code, String msg) {
                multipleStatusView.hideLoading();
                if (code == 200){
                    List<HaoYouListResponse.DataBeanX.DataBean> data = response.getData().getData();
                    if (pageIndex ==1){
                        pageTotal = response.getData().getLast_page();
                        if ( data != null) {
                            adapter.setNewData(data);
                        }
                    }else {
                        adapter.addData(data);
                    }
                }
            }

            @Override
            public void onFailed(Throwable e) {
                multipleStatusView.hideLoading();

            }

            @Override
            public void onStart() {
                super.onStart();
                multipleStatusView.showLoading();
            }
        });

    }

    //获取附近的人列表数据
    private void getFjData() {
        map.clear();
        map.put("user_id",user_id);
        map.put("page",pageIndex+"");
        map.put("lat","34.801765");//纬度
        map.put("lng","113.611325");//精度

        RetrofitTools.getFjRenList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
            @Override
            public void onSuccess(HaoYouListResponse response, int code, String msg) {
                multipleStatusView.hideLoading();
                if (code == 200){
                    List<HaoYouListResponse.DataBeanX.DataBean> data = response.getData().getData();
                    if (pageIndex ==1){
                        pageTotal = response.getData().getLast_page();
                        if ( data != null) {
                            adapter.setNewData(data);
                        }
                    }else {
                        adapter.addData(data);
                    }
                }
            }

            @Override
            public void onFailed(Throwable e) {
                multipleStatusView.hideLoading();

            }

            @Override
            public void onStart() {
                super.onStart();
                multipleStatusView.showLoading();
            }
        });
    }



    private class BanYanAdapter extends BaseQuickAdapter<HaoYouListResponse.DataBeanX.DataBean, BaseViewHolder> {

        public BanYanAdapter(int layoutResId) {
            super(layoutResId);
        }


        @Override
        protected void convert(BaseViewHolder helper, HaoYouListResponse.DataBeanX.DataBean item) {

            TextView tvStatus = helper.getView(R.id.tv_status);
            TextView tvChoose = helper.getView(R.id.tv_choose);

            int isOnLine = item.getIs_online();//0:离线 1：在线
            if (isOnLine == 0){
                tvStatus.setTextColor(Color.parseColor("#30FFFFFF"));
                tvStatus.setText("离线");
                tvChoose.setVisibility(View.INVISIBLE);
            }else {
                tvStatus.setTextColor(Color.parseColor("#fd3d5c"));
                tvStatus.setText("在线");
                tvChoose.setVisibility(View.VISIBLE);

                tvChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onChooseClick.onClick(item);
                    }
                });

            }
            Glide.with(mContext).load(item.getIcon())
                    .into((ImageView) helper.getView(R.id.iv_touxiang));

            helper.setText(R.id.tv_nickname,item.getNick_name());
        }

    }


    public interface OnChooseClick{
        void onClick(HaoYouListResponse.DataBeanX.DataBean item);
    }

}
