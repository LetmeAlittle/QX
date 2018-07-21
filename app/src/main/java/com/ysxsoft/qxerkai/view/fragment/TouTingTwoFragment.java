package com.ysxsoft.qxerkai.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GetLuYinListResponse;
import com.ysxsoft.qxerkai.net.response.GetLuYinTagListResponse;
import com.ysxsoft.qxerkai.net.response.GetTouTingListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.adapter.TouTingOneAdapter;
import com.ysxsoft.qxerkai.view.adapter.TouTingTwoAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSetListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaozhipeng on 18/5/23.
 */

public class TouTingTwoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//标签
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private View rootView;

    private TouTingTwoAdapter adapter;
    private int pageIndex = 1;
    private int pageTotal = 1;
    private LuYinTagAdapter tagAdapter;
    private String tagid = "";

    private List<GetLuYinTagListResponse.DataBeanX.DataBean> tagLists;

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
        setListener();
    }


    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TouTingTwoAdapter(R.layout.activity_touting_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        swipeTarget.setAdapter(adapter);

        LinearLayoutManager layoutManager0 = new LinearLayoutManager(getContext());
        layoutManager0.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        recyclerView.setLayoutManager(layoutManager0);
        tagLists = new ArrayList<>();
        tagAdapter = new LuYinTagAdapter(R.layout.item_luyin_tag);
        recyclerView.setAdapter(tagAdapter);
    }

    private void initData() {
        getTagLists();
    }

    private void setListener() {
        tagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tagAdapter.setPositionSelected(position);

                tagid = tagLists.get(position).getId()+"";
                getPageOneList();
            }
        });
    }

    private void getPageOneList() {
        pageIndex = 1;
        getList();
    }

    /**
     * 获取标签列表
     */
    private void getTagLists() {
        tagid = "";
        Map<String, String> map = new HashMap<String, String>();
        RetrofitTools.getLuYinTagList(map)
                .subscribe(new ResponseSubscriber<GetLuYinTagListResponse>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onSuccess(GetLuYinTagListResponse response, int code, String msg) {
                        multipleStatusView.hideLoading();
                        adapter.loadMoreComplete();
                        if (code == 200) {
                            if (response == null)
                                return;

                            GetLuYinTagListResponse.DataBeanX dataBeanX = response.getData();
                            if (dataBeanX == null) {
                                return;
                            }
                            tagLists = dataBeanX.getData();
                            if (tagLists.size() > 0) {
                                tagLists.get(0).setSelected(true);
                                tagid = tagLists.get(0).getId() + "";
                                tagAdapter.setNewData(tagLists);

                            }
                            getPageOneList();
                        } else {
                            ToastUtil.showToast(getActivity(), msg);
                        }
                    }


                    @Override
                    public void onFailed(Throwable e) {
                        adapter.loadMoreComplete();
                        multipleStatusView.hideLoading();

                        getPageOneList();
                    }
                });
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
     */
    private void getList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", DBUtils.getUserId());
        map.put("page", "" + pageIndex);
        map.put("tag_id", tagid);

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
                            if (getLuYinListResponse == null)
                                return;
                            GetLuYinListResponse.DataBeanX dataBeanX = getLuYinListResponse.getData();
                            if (dataBeanX == null) {
                                return;
                            }
                            List<GetLuYinListResponse.DataBeanX.DataBean> list = getLuYinListResponse.getData().getData();
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


    class LuYinTagAdapter extends BaseQuickAdapter<GetLuYinTagListResponse.DataBeanX.DataBean, BaseViewHolder> {

        public LuYinTagAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, GetLuYinTagListResponse.DataBeanX.DataBean item) {
            TextView tvContent = helper.getView(R.id.tv_content);
            tvContent.setText(item.getTitle());
            tvContent.setSelected(item.isSelected());
        }


        public void setPositionSelected(int positionSelected) {
            for (int i = 0; i < tagLists.size(); i++) {
                if (positionSelected == i) {
                    tagLists.get(i).setSelected(true);
                } else {
                    tagLists.get(i).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }

    }
}
