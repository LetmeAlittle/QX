package com.ysxsoft.qxerkai.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GetLuYinListResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.adapter.TouTingOneAdapter;
import com.ysxsoft.qxerkai.view.adapter.TouTingTwoAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class TouTingTwoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;

    private TouTingTwoAdapter adapter;
    private int pageIndex=1;
    private int pageTotal=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_touting_two, container);
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
        adapter = new TouTingTwoAdapter(R.layout.activity_touting_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {
        getList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
     * 获取偷听列表
     *
     */
    private void getList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DBUtils.getUserId());
        map.put("page",""+pageIndex );
//        map.put("tag_id",""+pageIndex );

        RetrofitTools.getLuYinList(map)
                .subscribe(new ResponseSubscriber<GetLuYinListResponse>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        multipleStatusView.showLoading();
                    }
                    @Override
                    public void onSuccess(GetLuYinListResponse getLuYinListResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        adapter.loadMoreComplete();
                        if (code == 200) {
                            if (getLuYinListResponse == null)return;
                            GetLuYinListResponse.DataBeanX dataBeanX = getLuYinListResponse.getData();
                            if (dataBeanX == null) {
                                return;
                            }
                            List<GetLuYinListResponse.DataBeanX.DataBean> list=getLuYinListResponse.getData().getData();
                            if (pageIndex == 1) {
                                adapter.setNewData(list);
                                pageTotal = dataBeanX.getLast_page();
                            } else {
                                adapter.addData(list);
                            }
                        } else {
                            if (pageIndex > 1) {
                                pageIndex--;
                            }
                            ToastUtil.showToast(getActivity(), msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        adapter.loadMoreComplete();
                        multipleStatusView.hideLoading();
                    }
                });
    }
}
