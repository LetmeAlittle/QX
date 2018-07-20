package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.SearchListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.adapter.BanYanSearchAdapter;
import com.ysxsoft.qxerkai.view.adapter.ShouHuSearchAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NBanYanSearchActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.et_public_titlebar)
    EditText etPublicTitlebar;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private BanYanSearchAdapter adapter;
    private int time = 0, pageIndex = 1, pageTotal = 1;
    private String user_id = "",searchContent = "";
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nshou_hu_search);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        user_id = DBUtils.getUserId();
        Intent intent = getIntent();
        time = intent.getIntExtra(XuanZheShouHuActivity.TYPE_JUMP, 0);

        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BanYanSearchAdapter(R.layout.activity_xuan_zhe_shou_hu_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {

    }

    private void setListener() {
        adapter.setOnLoadMoreListener(this, swipeTarget);

        etPublicTitlebar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){//搜索按键action
                    SystemUtils.hideSoftInput(NBanYanSearchActivity.this);
                    searchContent = etPublicTitlebar.getText().toString();
                    if (TextUtils.isEmpty(searchContent)){
                        return true;
                    }
                    LogUtils.e("开始搜索");

                    pageIndex = 1;
                    getListData();
                    return true;
                }
                return false;
            }
        });

        adapter.setOnChooseClick(new BanYanSearchAdapter.OnChooseClick() {
            @Override
            public void onClick(SearchListResponse.DataBean.ListBean item) {
                //TODO 角色扮演     选择
                showToast(item.getNick_name());
            }
        });
    }


    //获取好友列表数据
    private void getListData() {
        map.clear();
        map.put("id", searchContent);
        map.put("page", pageIndex + "");

        RetrofitTools.getSearchList(map).subscribe(new ResponseSubscriber<SearchListResponse>() {
            @Override
            public void onSuccess(SearchListResponse response, int code, String msg) {
                multipleStatusView.hideLoading();
                if (code == 200) {
                    List<SearchListResponse.DataBean.ListBean> data = response.getData().getList();
                    if (pageIndex == 1) {
                        pageTotal = response.getData().getPage().getLast_page();
                        if (data != null) {
                            adapter.setNewData(data);
                        }
                    } else {
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
            getListData();
        } else {
            adapter.loadMoreEnd();
        }
    }
    @OnClick(R.id.iv_public_titlebar_left_1)
    public void onViewClicked() {
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        SystemUtils.hideSoftInput(NBanYanSearchActivity.this);
    }
}
