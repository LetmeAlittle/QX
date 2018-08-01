package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.ObserverMap;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.view.adapter.LiaoRenQuAdapter;
import com.ysxsoft.qxerkai.view.fragment.TwoPage;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NQingQuListActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, ObserverMap.IPageDataChangeObserver {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private int pageIndex = 1;
    private int pageTotal = 1;
    private QingQuListAdapter adapter;
    private String type = "";
    private static final int GO_DETAIL = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nliao_ren_qu);
        type = getIntent().getStringExtra("type");//类型 6个 1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
        ObserverMap.reg(this.getClass().getSimpleName(), this);
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
        tvPublicTitlebarCenter.setText("小情趣");
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QingQuListAdapter(R.layout.fragment_two_content_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetCardListResponse.DataBeanX.ListBean.DataBean bean = (GetCardListResponse.DataBeanX.ListBean.DataBean) adapter.getItem(position);
                if (bean != null) {
                    NQingQuDetailActivity.start(NQingQuListActivity.this, NQingQuListActivity.this.getClass().getSimpleName(), bean.getTid(), GO_DETAIL, position);
                }
            }
        });
        adapter.setOnLoadMoreListener(this, swipeTarget);
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {
        getList();
    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            initData();
        } else {
            adapter.loadMoreEnd();
        }
    }

    /**
     * 获取小情趣列表
     */
    private void getList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DBUtils.getUserId());
        map.put("type", type);//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        map.put("page", pageIndex + "");

        RetrofitTools.getCardList(map).subscribe(new ResponseSubscriber<GetCardListResponse>() {
            @Override
            public void onSuccess(GetCardListResponse getCardListResponse, int code, String msg) {
                multipleStatusView.hideLoading();
                adapter.loadMoreComplete();
                if (code == 200) {
                    if (getCardListResponse != null && getCardListResponse.getData() != null && getCardListResponse.getData().getList() != null) {
                        fillData(getCardListResponse.getData().getList());
                    }
                } else {
                    if (pageIndex > 1) {
                        pageIndex--;
                    }
                    ToastUtil.showToast(NQingQuListActivity.this, msg);
                }
            }

            @Override
            public void onFailed(Throwable e) {
                adapter.loadMoreComplete();
                multipleStatusView.hideLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                multipleStatusView.showLoading();
            }
        });
    }

    private void fillData(GetCardListResponse.DataBeanX.ListBean list) {
        List<GetCardListResponse.DataBeanX.ListBean.DataBean> data = list.getData();
        if (data == null) {
            return;
        }
        if (pageIndex == 1) {
            adapter.setNewData(data);
        } else {
            adapter.addData(data);
        }
    }

    @Override
    public void change() {
        pageIndex = 1;
        getList();//点赞后刷新 暂未用到
    }

    @Override
    public void change(int likeNum, int commonNum, boolean isChanged, int position, int readNum) {
        if (adapter != null && adapter.getItemCount() > position) {
//            Log.e("tag", "刷新Item" + " likeNum:" + likeNum + " commonNum:" + commonNum + " isChanged:" + isChanged + " position:" + position + " readNum:" + readNum);
            GetCardListResponse.DataBeanX.ListBean.DataBean d = adapter.getItem(position);
            d.setLikes(likeNum + "");
            d.setIs_like(isChanged ? "1" : "0");
            d.setCom_num(commonNum);
            d.setLooks(readNum);
            adapter.notifyDataSetChanged();
        }
    }


    class QingQuListAdapter extends BaseQuickAdapter<GetCardListResponse.DataBeanX.ListBean.DataBean, BaseViewHolder> {
        private int clickPosition;

        public QingQuListAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, GetCardListResponse.DataBeanX.ListBean.DataBean item) {
            helper.setText(R.id.cardTitle, StringUtils.convert(item.getTitle()));
            com.ysxsoft.qxerkai.view.widget.RoundAngleImageView image = helper.getView(R.id.cardImage);
            Glide.with(mContext).load(item.getImgss()).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);
            helper.setText(R.id.cardContent, StringUtils.convert(item.getContent()));
            helper.setText(R.id.lookNum, StringUtils.convert(item.getLooks() + ""));
            helper.setText(R.id.goodNum, StringUtils.convert(item.getLikes()));
            helper.setText(R.id.sayNum, StringUtils.convert(item.getCom_num() + ""));

            ImageView likeImage = helper.getView(R.id.likeImage);
            if ("1".equals(item.getIs_like())) {//1 已点赞  0未点赞
                likeImage.setImageResource(R.mipmap.activity_pengyouquan_detail_zan_r);
            } else {
                likeImage.setImageResource(R.mipmap.fragment_two_dianzan);
            }
            likeImage.setOnClickListener(new OnLikeClickListener(item.getTid(), helper.getAdapterPosition()));

            //删除
            TextView delete = helper.getView(R.id.delete);
            if(item.getUser_id()== DBUtils.getIntUserId()){
                delete.setVisibility(View.VISIBLE);
            }else{
                delete.setVisibility(View.GONE);
            }
            delete.setOnClickListener(new OnDeleteClick(item.getTid(),helper.getAdapterPosition()));
        }

        private class OnDeleteClick implements View.OnClickListener{
            private int cid;
            private int p;
            public OnDeleteClick(int cid,int p) {
                this.cid = cid;
                this.p=p;
            }

            @Override
            public void onClick(View v) {
                clickPosition=p;
                new MaterialDialog.Builder(mContext)
                        .title("温馨提示")
                        .content("是否删除该动态")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                delete(cid);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }

        private void delete(int cid){
            Map<String, String> map2 = new HashMap<>();
            map2.put("cid", ""+cid);
            RetrofitTools.deleteQingQu(map2)
                    .subscribe(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                            if (code == 200) {
                                refreshAdapter();
                                ObserverMap.notify("MainActivity");
                            }
                        }

                        @Override
                        public void onFailed(Throwable e) {
                            Log.e("tag", "删除朋友圈失败");
                        }
                    });
        }

        private void refreshAdapter(){
            if(mData.size()>clickPosition&&clickPosition!=-1){
                mData.remove(clickPosition);
                notifyDataSetChanged();
            }
        }

        /**
         * 点赞
         */
        private class OnLikeClickListener implements View.OnClickListener {
            private int tid;
            private int position;

            public OnLikeClickListener(int tid, int position) {
                this.tid = tid;
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                clickPosition = position;
                like(tid);
            }
        }

        /**
         * 点赞
         *
         * @param tid
         */
        private void like(int tid) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", DBUtils.getUserId());
            map.put("cid", "" + tid);//列表的id
            map.put("type", "1");//	1帖子 2评论

            RetrofitTools.cardLike(map)
                    .subscribe(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                            if (code == 200) {
                                refresh();
                            } else {
                                ToastUtil.showToast(mContext, msg);
                            }
                        }

                        @Override
                        public void onFailed(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }

        private void refresh() {
            String isLike = mData.get(clickPosition).getIs_like();
            if ("1".equals(isLike)) {//1已点赞  0未点赞

            } else {
                String likeNum = mData.get(clickPosition).getLikes();
                if (likeNum != null && !"".equals(likeNum)) {
                    int n = Integer.parseInt(likeNum);
                    mData.get(clickPosition).setLikes("" + (n + 1));
                }
                mData.get(clickPosition).setIs_like("1");
            }
            notifyDataSetChanged();
        }
    }
}
