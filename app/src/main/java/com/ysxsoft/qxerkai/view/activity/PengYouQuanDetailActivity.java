package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanAdapter;
import com.ysxsoft.qxerkai.view.adapter.PengYouQuanDetailAdapter;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class PengYouQuanDetailActivity extends NBaseActivity implements BaseQuickAdapter.RequestLoadMoreListener{

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

    private int pageIndex=1;
    private int pageTotal=1;
    private PengYouQuanDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_you_quan_detail);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
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
        tvPublicTitlebarCenter.setText("详情");
    }

    private void initView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PengYouQuanDetailAdapter(R.layout.activity_pengyouquan_detail_item);
        adapter.openLoadAnimation(com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnLoadMoreListener(this, swipeTarget);
        View headView = getLayoutInflater().inflate(R.layout.activity_peng_you_quan_detail_top, (ViewGroup) swipeTarget.getParent(), false);
        initHeadView(headView);
        adapter.addHeaderView(headView);
        swipeTarget.setAdapter(adapter);
    }

    private void initHeadView(View headView) {
        ArrayList<String> phones = new ArrayList<>();
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        BGANinePhotoLayout ninePhotoLayout = (BGANinePhotoLayout)headView.findViewById(R.id.snpl_moment_add_photos);
        ninePhotoLayout.setData(phones);
        ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
            @Override
            public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
                File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
                BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(PengYouQuanDetailActivity.this)
                        .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
                photoPreviewIntentBuilder
                        .previewPhotos((ArrayList<String>) models).currentPosition(position); // 当前预览图片的索引
                PengYouQuanDetailActivity.this.startActivity(photoPreviewIntentBuilder.build());
            }
        });
    }

    private void initData() {
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
    public void onLoadMoreRequested() {
        if (pageIndex < pageTotal) {
            pageIndex++;
            initData();
        } else {
            adapter.loadMoreEnd();
        }
    }
}
