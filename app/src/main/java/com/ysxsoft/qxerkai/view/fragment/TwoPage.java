package com.ysxsoft.qxerkai.view.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.AVChatActivity;
import com.ttt.qx.qxcall.function.voice.AVChatProfile;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.net.response.RuleResponse;
import com.ysxsoft.qxerkai.net.response.TwoPageTuiJianResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.ObserverMap;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.activity.LiaoHanQuActivity;
import com.ysxsoft.qxerkai.view.activity.LiaoMeiQuActivity;
import com.ysxsoft.qxerkai.view.activity.NFaTieActivity;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoActivity;
import com.ysxsoft.qxerkai.view.activity.NQingQuDetailActivity;
import com.ysxsoft.qxerkai.view.activity.NQingQuListActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ResizableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.getApplication;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class TwoPage extends BasePager implements View.OnClickListener, ObserverMap.IPageDataChangeObserver {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.top_bar_bg_view)
    LinearLayout topBarBgView;
    @BindView(R.id.tv_top_bar_item1)
    TextView tvTopBarItem1;
    @BindView(R.id.tv_top_bar_item2)
    TextView tvTopBarItem2;
    @BindView(R.id.tv_top_bar_item3)
    TextView tvTopBarItem3;
    @BindView(R.id.tv_top_bar_item4)
    TextView tvTopBarItem4;
    @BindView(R.id.tv_gonggao)
    TextView tvGonggao;
    @BindView(R.id.rv_quanzi)
    RecyclerView rvQuanzi;
    @BindView(R.id.rv_tuijianyonghu)
    RecyclerView rvTuijianyonghu;
    @BindView(R.id.riv_loop)
    ResizableImageView rivLoop;
    @BindView(R.id.iv_liaomei)
    ImageView ivLiaomei;
    @BindView(R.id.iv_liaohan)
    ImageView ivLiaohan;

    private View rootView;

    private int offset = 0;// 动画图片偏移量
    private int currentTabIndex = 0;

    private QuanZiAdapter quanZiAdapter;
    private YongHuAdapter yongHuAdapter;
    public static final int GO_DETAIL = 0x02;

    public TwoPage(Context ctx) {
        super(ctx);
    }

    private ArrayList<Integer> loops = new ArrayList<>();
    private ArrayList<Integer> temp = new ArrayList<>();
    private String type = "1";//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_two, null);
        ButterKnife.bind(this, rootView);
        initStatusBar(statusBar);
        initTitleBar();
        initRv();

        ObserverMap.reg(activity.getClass().getSimpleName(), this);
        return rootView;
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("小情趣");
    }

    private void initRv() {
        rvQuanzi.setLayoutManager(new LinearLayoutManager(ctx));
        quanZiAdapter = new QuanZiAdapter(R.layout.fragment_two_content_item);
        rvQuanzi.setAdapter(quanZiAdapter);
        rvTuijianyonghu.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        yongHuAdapter = new YongHuAdapter(R.layout.fragment_one_item);
        rvTuijianyonghu.setAdapter(yongHuAdapter);

        quanZiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GetCardListResponse.DataBeanX.ListBean.DataBean bean = (GetCardListResponse.DataBeanX.ListBean.DataBean) adapter.getItem(position);
                if (bean != null) {
                    NQingQuDetailActivity.start(activity, activity.getClass().getSimpleName(), bean.getTid(), GO_DETAIL);//跳转小情趣详情页  回调通知
                }
            }
        });
        yongHuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TwoPageTuiJianResponse.DataBeanX.DataBean item = (TwoPageTuiJianResponse.DataBeanX.DataBean) adapter.getItem(position);
                int id = item.getId();//发表人id
                activity.startActivity(new Intent(activity, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", id + ""));//查看好友资料
            }
        });
    }

    @Override
    public void initData() {
        initCurrView();
        initHttpData();
        loops.clear();
        loops.add(R.mipmap.fragment_two_laoshiji);
        loops.add(R.mipmap.fragment_two_guimi);
        loops.add(R.mipmap.fragment_two_liangxing);
        loops.add(R.mipmap.fragment_two_juben);
        temp.clear();
        temp.add(R.mipmap.image1);
        temp.add(R.mipmap.image2);
        temp.add(R.mipmap.image3);
        temp.add(R.mipmap.image4);
        temp.add(R.mipmap.image5);
    }

    List<GetCardListResponse.DataBeanX.ListBean.DataBean> cardList = new ArrayList<>();

    private void initHttpData() {
        switch (currentTabIndex) {
            case 0://老司机开车
                type = "1";//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
                break;
            case 1://闺蜜私房话
                type = "2";
                break;
            case 2://两性研究所
                type = "3";
                break;
            case 3://剧本专区
                type = "4";
                break;
        }
        getList();
        getNotice();
        getTuiJianList();
    }

    private void initCurrView() {
        // 动画
        offset = (int) ((SystemUtils.getScreenWidth(activity) - DimenUtils.dp2px(ctx, 30)) / 4);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(offset, ViewGroup.LayoutParams.MATCH_PARENT);
        topBarBgView.setLayoutParams(params);
        tvTopBarItem1.setOnClickListener(this);
        tvTopBarItem2.setOnClickListener(this);
        tvTopBarItem3.setOnClickListener(this);
        tvTopBarItem4.setOnClickListener(this);
        tvGonggao.setSelected(true);
        ivLiaomei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, LiaoMeiQuActivity.class));
            }
        });
        ivLiaohan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, LiaoHanQuActivity.class));
            }
        });
    }

    @OnClick({R.id.tv_mores})
    public void onMores(View view) {
        ctx.startActivity(new Intent(ctx, NQingQuListActivity.class).putExtra("type", type));
    }

    /**
     * 发布
     *
     * @param view
     */
    @OnClick({R.id.iv_fabu})
    public void onFabu(View view) {
        NFaTieActivity.start(ctx, ctx.getClass().getSimpleName());
    }

    @Override
    public void onClick(View view) {
        int tag = Integer.valueOf(view.getTag().toString());
        if (tag == currentTabIndex) {
            return;
        }
        //必须用属性动画，否则回到这个页面会初始化位置
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topBarBgView, "translationX", offset * currentTabIndex, offset * tag);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        objectAnimator.start();
        rivLoop.setImageResource(loops.get(tag));
        currentTabIndex = tag;
        initHttpData();
    }

    @Override
    public void change() {
        getList();
    }

    @Override
    public void change(int likeNum, int commonNum, boolean isChanged, int position, int readNum) {
        //返回重新刷新数据
    }

    /**
     * 小情趣适配器
     */
    private class QuanZiAdapter extends BaseQuickAdapter<GetCardListResponse.DataBeanX.ListBean.DataBean, BaseViewHolder> {

        public QuanZiAdapter(int layoutResId) {
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
                like(tid);
            }
        }
    }

    private class YongHuAdapter extends BaseQuickAdapter<TwoPageTuiJianResponse.DataBeanX.DataBean, BaseViewHolder> {

        public YongHuAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TwoPageTuiJianResponse.DataBeanX.DataBean item) {
            FrameLayout flBg = helper.getView(R.id.fl_bg);
            int bgWidth = (int) SystemUtils.getScreenWidth(activity) / 2 - DimenUtils.dp2px(ctx, 17.5f);
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth / 34 * 45));
            ImageView ivView = helper.getView(R.id.iv_image);
            Glide.with(mContext).load(item.getMember_avatar()).into(ivView);
            ImageView ivCall = helper.getView(R.id.iv_call);
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                                AVChatActivity.launch(getApplication(), item.getWy_acid(), AVChatType.AUDIO.getValue(), AVChatActivity.FROM_INTERNAL);
                            } else {
                                ToastUtils.showToast(activity, standardResponse.getMessage(), 0);
                            }
                        }
                    }, String.valueOf(item.getId()),  "Bearer "+DBUtils.getUserToken());
//                    }, String.valueOf("10195"),  "Bearer "+DBUtils.getUserToken());
//                    }, String.valueOf("10195"),  "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTE2LjYyLjIxNy4xODMvYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1MzA3Nzk5NjIsIm5iZiI6MTUzMDc3OTk2MiwianRpIjoiWUx6V2x0MVNUdUZBaTZlVCIsInN1YiI6MTAxOTUsInBydiI6ImFmZDBmZDliZGQ5YWM3MmZmMzk4MzQxZjFkNjI4NDBjYmY0YzcxNjcifQ.TUr5VrjwTtGu74_unrDiiaSatHC42ILiYeK059A76B8");
                }
            });
            TextView name = helper.getView(R.id.tv_name);
            name.setText(item.getNick_name());
            TextView age = helper.getView(R.id.tv_age);
            if (item.getMember_age() == null || "".equals(item.getMember_age())) {
                age.setText("0岁");
            } else {
                age.setText(item.getMember_age() + "岁");
            }
            if (item.getMember_sex() == 1) {
                age.setTextColor(mContext.getResources().getColor(R.color.sex_nan));
            } else if (item.getMember_sex() == 2) {
                age.setTextColor(mContext.getResources().getColor(R.color.sex_nv));
            } else {
                age.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            TextView price = helper.getView(R.id.tv_price);
            price.setText(item.getMember_price() + "砰砰豆/分钟");
        }
    }

    /**
     * 根据类型获取通知
     */
    private void getNotice() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type);//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        RetrofitTools.getNoticeList(map)
                .subscribe(new ResponseSubscriber<GetNoticeListResponse>() {
                    @Override
                    public void onSuccess(GetNoticeListResponse getNoticeListResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        if (code == 200) {
                            if (getNoticeListResponse != null && getNoticeListResponse.getData() != null) {
                                fillData(getNoticeListResponse.getData().getData());
                            }
                        } else {
                            ToastUtil.showToast(activity, msg);
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

    /**
     * 填充通知
     *
     * @param data
     */
    private void fillData(List<GetNoticeListResponse.DataBeanX.DataBean> data) {
        if (data == null) {
            return;
        }
        if (data != null && data.size() > 0) {
            tvGonggao.setText(StringUtils.convert(data.get(0).getContent()));
        }
    }

    /**
     * 获取小情趣列表
     */
    private void getList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DBUtils.getUserId());
        map.put("type", type);//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        map.put("page", "1");

        RetrofitTools.getCardList(map)
                .subscribe(new ResponseSubscriber<GetCardListResponse>() {
                    @Override
                    public void onSuccess(GetCardListResponse getCardListResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        if (code == 200) {
                            if (getCardListResponse != null && getCardListResponse.getData() != null && getCardListResponse.getData().getList() != null) {
                                fillData(getCardListResponse.getData().getList());
                            }
                        } else {
                            ToastUtil.showToast(activity, msg);
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

    private void fillData(GetCardListResponse.DataBeanX.ListBean list) {
        List<GetCardListResponse.DataBeanX.ListBean.DataBean> data = list.getData();
        if (data == null) {
            return;
        }
        int size = data.size();
        List<GetCardListResponse.DataBeanX.ListBean.DataBean> temp1 = new ArrayList<>();
        if (size > 3) {//超过三条取3条
            for (int i = 0; i < size; i++) {
                temp1.add(data.get(i));
                if (temp1.size() > 2) {
                    break;
                }
            }
        } else {//未超过三条全取
            temp1.addAll(data);
        }
        quanZiAdapter.setNewData(temp1);
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
                            getList();
                        } else {
                            ToastUtil.showToast(activity, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 获取推荐的用户
     */
    private void getTuiJianList() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());

        RetrofitTools.getTuiJianList(map)
                .subscribe(new ResponseSubscriber<TwoPageTuiJianResponse>() {
                    @Override
                    public void onSuccess(TwoPageTuiJianResponse twoPageTuiJianResponse, int code, String msg) {
                        if (code == 200) {
                            if (twoPageTuiJianResponse.getData() != null && twoPageTuiJianResponse.getData().getData() != null) {
                                yongHuAdapter.setNewData(twoPageTuiJianResponse.getData().getData());
                            }
                        } else {
                            ToastUtil.showToast(activity, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                    }
                });
    }
}
