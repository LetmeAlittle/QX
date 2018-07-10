package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.ysxsoft.qxerkai.net.response.LiaoRenResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.ObserverMap;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.view.adapter.LiaoRenQuAdapter;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanDetailAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NLiaoRenQuActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, ObserverMap.IPageDataChangeObserver {

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
    private LiaoRenQuAdapter adapter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nliao_ren_qu);
        userId = getIntent().getIntExtra("userId", -1);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
        ObserverMap.reg(this.getClass().getSimpleName(),this);
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
        tvPublicTitlebarCenter.setText("撩人区");
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LiaoRenQuAdapter(R.layout.fragment_two_content_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
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
     * 请求撩汉区帖子列表
     */
    private void getList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DBUtils.getUserId());
        map.put("type", "6");//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区  TODO:待修改
        map.put("page", pageIndex + "");

        RetrofitTools.getLiaoRenList(map).subscribe(new ResponseSubscriber<LiaoRenResponse>() {
            @Override
            public void onSuccess(LiaoRenResponse liaoRenResponse, int code, String msg) {
                multipleStatusView.hideLoading();
                if (code == 200) {
                    if (liaoRenResponse != null && liaoRenResponse.getData() != null && liaoRenResponse.getData().getList() != null) {
                        List<LiaoRenResponse.DataBeanX.ListBean.DataBean> data = liaoRenResponse.getData().getList().getData();
                        if (data == null) {
                            return;
                        }
                        if (pageIndex == 1) {
                            adapter.setNewData(data);
                            pageTotal = liaoRenResponse.getData().getList().getLast_page();
                        } else {
                            adapter.addData(data);
                        }
                    }
                } else {
                    if (pageIndex > 1) {
                        pageIndex--;
                    }
                    ToastUtil.showToast(NLiaoRenQuActivity.this, msg);
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

    @Override
    public void change() {

    }

    @Override
    public void change(int likeNum, int commonNum, boolean isChanged, int position, int readNum) {
        if (adapter != null && adapter.getItemCount() > position) {
//            Log.e("tag", "刷新Item" + " likeNum:" + likeNum + " commonNum:" + commonNum + " isChanged:" + isChanged + " position:" + position + " readNum:" + readNum);
            LiaoRenResponse.DataBeanX.ListBean.DataBean d = adapter.getItem(position);
            d.setLikes(likeNum );
            d.setIs_like(isChanged ? "1" : "0");
            d.setCom_num(commonNum);
            d.setLooks(readNum+"");
            adapter.notifyDataSetChanged();
        }
    }

    //撩人区适配器
    class LiaoRenQuAdapter extends BaseQuickAdapter<LiaoRenResponse.DataBeanX.ListBean.DataBean, BaseViewHolder> {
        private int clickPosition;

        public LiaoRenQuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, LiaoRenResponse.DataBeanX.ListBean.DataBean item) {
            helper.setText(R.id.cardTitle, StringUtils.convert(item.getTitle()));
            com.ysxsoft.qxerkai.view.widget.RoundAngleImageView image = helper.getView(R.id.cardImage);
            Glide.with(mContext).load(item.getImgss()).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);
            helper.setText(R.id.cardContent, StringUtils.convert(item.getContent()));
            helper.setText(R.id.lookNum, StringUtils.convert(item.getLooks() + ""));
            helper.setText(R.id.goodNum, StringUtils.convert(item.getLikes() + ""));
            helper.setText(R.id.sayNum, StringUtils.convert(item.getCom_num() + ""));
            ImageView likeImage = helper.getView(R.id.likeImage);
            if ("1".equals(item.getIs_like())) {//1 已点赞  0未点赞
                likeImage.setImageResource(R.mipmap.activity_pengyouquan_detail_zan_r);
            } else {
                likeImage.setImageResource(R.mipmap.fragment_two_dianzan);
            }
            likeImage.setOnClickListener(new OnLikeClickListener(item.getTid(), helper.getAdapterPosition()));
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
                clickPosition = (position - 1);
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
                int likeNum = mData.get(clickPosition).getLikes();
                mData.get(clickPosition).setLikes(likeNum + 1);
                mData.get(clickPosition).setIs_like("1");
            }
            notifyDataSetChanged();
        }
    }
}
