package com.ysxsoft.qxerkai.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.view.adapter.TopBangOneAdapter;
import com.ysxsoft.qxerkai.view.adapter.TouTingOneAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class TopBangOneFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;

    private TopBangOneAdapter adapter;
    private int pageIndex = 1;
    private int pageTotal = 1;
    private HashMap<String, String> map = new HashMap<>();
    private ImageView ivTouXiang;
    private TextView tvNick, tvMoney;
    private String type = "1";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    HaoYouListResponse.DataBeanX.DataBean dataBean = (HaoYouListResponse.DataBeanX.DataBean) msg.obj;
                    Glide.with(getContext()).load(dataBean.getIcon()).into(ivTouXiang);
                    tvNick.setText(dataBean.getNick_name());
                    tvMoney.setText(dataBean.getCnt());
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sgl_one, container);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TopBangOneAdapter(R.layout.activity_top_bang_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getActivity().getLayoutInflater().inflate(R.layout.activity_top_bang_top, (ViewGroup) swipeTarget.getParent(), false);
        ivTouXiang = (ImageView) headView.findViewById(R.id.iv_touxiang);
        tvMoney = (TextView) headView.findViewById(R.id.tv_money);
        tvNick = (TextView) headView.findViewById(R.id.tv_nickname);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
    }

    private void initData() {
        pageIndex = 1;
        getTopList();
    }


    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            getTopList();
        } else {
            adapter.loadMoreEnd();
        }
    }


    //获取好友列表数据
    private void getTopList() {
        map.clear();
        map.put("page", pageIndex + "");
        map.put("type", type);

        RetrofitTools.getTopList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
            @Override
            public void onSuccess(HaoYouListResponse response, int code, String msg) {
                multipleStatusView.hideLoading();
                adapter.loadMoreComplete();
                if (code == 200) {
                    List<HaoYouListResponse.DataBeanX.DataBean> data = response.getData().getData();
                    if (pageIndex == 1) {
                        pageTotal = response.getData().getLast_page();
                        if (data != null) {
                            if (data.size() > 1) {
                                Message message = new Message();
                                message.what = 0;
                                message.obj = data.get(0);
                                handler.sendMessage(message);

                                data.remove(0);
                                adapter.setNewData(data);
                            }
                        }

                    } else {
                        adapter.addData(data);
                    }
                }
            }

            @Override
            public void onFailed(Throwable e) {
                multipleStatusView.hideLoading();
                adapter.loadMoreComplete();
            }

            @Override
            public void onStart() {
                super.onStart();
                multipleStatusView.showLoading();
            }
        });
    }
}
