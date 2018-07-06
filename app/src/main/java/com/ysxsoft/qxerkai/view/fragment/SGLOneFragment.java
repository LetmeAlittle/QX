package com.ysxsoft.qxerkai.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.adapter.SGLOneAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.RoundAngleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class SGLOneFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;
    private TextView topLikeNum;
    private ImageView topImageView;

    private SGLOneAdapter adapter;
    private int pageIndex = 1;
    private int pageTotal = 1;
    private List<SaGouLiangListResponse> saGouLiangListResponseList = null;

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
        adapter = new SGLOneAdapter(R.layout.activity_sgl_item, new ArrayList<String>());
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getActivity().getLayoutInflater().inflate(R.layout.fragment_sgl_one_top, (ViewGroup) swipeTarget.getParent(), false);
        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initHeadView(View headView) {
        RoundAngleImageView raImageView = (RoundAngleImageView) headView.findViewById(R.id.ra_imageView);
        ImageView ivMengban = (ImageView) headView.findViewById(R.id.iv_mengban);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)), ((int) SystemUtils.getScreenWidth(getActivity()) - DimenUtils.dp2px(getActivity(), 40)) / 69 * 50);
        raImageView.setLayoutParams(lp);
        ivMengban.setLayoutParams(lp);
    }

    private void initData() {

        getList();

        ArrayList<String> temp = new ArrayList<>();
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        temp.add("");
        adapter.setNewData(temp);
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

        RetrofitTools.getSaGouLiangList(map)
                .subscribe(new ResponseSubscriber<SaGouLiangListResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangListResponse saGouLiangListResponse, int code, String msg) {
                        Log.e("tag", msg);
                        if (saGouLiangListResponse.getData() == null) {
                            return;
                        }
                        if (code == 200) {
                            topBean = saGouLiangListResponse.getData().getTop();
                        } else {

                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        Log.e("tag", e.getMessage());
                    }

                });
    }

    private SaGouLiangListResponse.DataBean.TopBean topBean;
}
