package com.ysxsoft.qxerkai.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.SGLTwoAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.ResizableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class SGLTwoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;

    private SGLTwoAdapter adapter;
    private int pageIndex = 1;
    private int pageTotal = 1;
    private List<SaGouLiangListResponse.DataBean.ListBean> data = new ArrayList<>();
    private TextView tvContent;

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
        adapter = new SGLTwoAdapter(R.layout.activity_sgl_item, data);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getActivity().getLayoutInflater().inflate(R.layout.fragment_sgl_top, (ViewGroup) swipeTarget.getParent(), false);
        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initHeadView(View headView) {
        tvContent = (TextView) headView.findViewById(R.id.tv_content);
        tvContent.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/jybks.TTF"));
        LinearLayout llContent = (LinearLayout) headView.findViewById(R.id.ll_content);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 33, ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 41);
        lp.leftMargin = ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 8;
        lp.topMargin = ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 5;
        lp.bottomMargin = ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 4;
        llContent.setLayoutParams(lp);
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

    private void getList() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");//1.故事  2.图片
        map.put("page", pageIndex + "");
        RetrofitTools.getSaGouLiangList(map)
                .subscribe(new ResponseSubscriber<SaGouLiangListResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangListResponse saGouLiangListResponse, int code, String msg) {
                        SaGouLiangListResponse.DataBean dataBean = saGouLiangListResponse.getData();
                        if (dataBean == null) {
                            return;
                        }
                        if (code == 200) {
                            List<SaGouLiangListResponse.DataBean.ListBean> list = dataBean.getList();
                            if (pageIndex == 1) {
                                adapter.setNewData(list);
                            } else {
                                adapter.addData(list);
                            }
                        } else {
                            if (pageIndex > 1) {
                                pageIndex--;
                            }
                            ToastUtils.showToast(getActivity(), msg, 0);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        Log.e("tag", e.getMessage());
                        if (pageIndex > 1) {
                            pageIndex--;
                        }
                    }
                });
    }
}
