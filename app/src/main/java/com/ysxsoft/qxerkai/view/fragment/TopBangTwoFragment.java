package com.ysxsoft.qxerkai.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.view.adapter.TopBangOneAdapter;
import com.ysxsoft.qxerkai.view.adapter.TopBangTwoAdapter;
import com.ysxsoft.qxerkai.view.adapter.TouTingOneAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/23.
 *
 * 没有用
 */
public class TopBangTwoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;

    private ImageView ivTouXiang;
    private TextView tvNick,tvMoney;

    private TopBangTwoAdapter adapter;
    private int pageIndex=1;
    private int pageTotal=1;
    private HashMap<String,String> map = new HashMap<>();

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
        adapter = new TopBangTwoAdapter(R.layout.activity_top_bang_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getActivity().getLayoutInflater().inflate(R.layout.activity_top_bang_top, (ViewGroup) swipeTarget.getParent(), false);
        ivTouXiang = (ImageView) headView.findViewById(R.id.iv_touxiang);
        tvMoney = (TextView) headView.findViewById(R.id.tv_money);
        tvNick = (TextView) headView.findViewById(R.id.tv_nickname);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {
        pageIndex =1;
        getTopList();
    }

    //获取好友列表数据
    private void getTopList() {
        map.clear();
        map.put("page",pageIndex+"");
        map.put("type","2");

        RetrofitTools.getTopList(map).subscribe(new ResponseSubscriber<HaoYouListResponse>() {
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


    @Override
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            getTopList();
        } else {
            adapter.loadMoreEnd();
        }
    }
}
