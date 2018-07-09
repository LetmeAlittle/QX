package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dialog.ImgZoomFragment;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class NXiangCheChaKanActivity extends NBaseActivity {

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
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;

    private XiangCheAdapter adapter;

    private ArrayList<UserDetailInfo.DataBean.XiangCheBean> photos;
    private ArrayList<String> photosStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nxiang_che_chakan);
        ButterKnife.bind(this);
        photos=(ArrayList<UserDetailInfo.DataBean.XiangCheBean>) getIntent().getSerializableExtra("photos");
        photosStr=new ArrayList<>();
        for(int i=0;i<photos.size();i++){
            photosStr.add(photos.get(i).getIcon());
        }
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        adapter.setNewData(photos);
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
        tvPublicTitlebarCenter.setText("相册");
    }

    private void initView() {
        swipeTarget.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        adapter=new XiangCheAdapter(R.layout.activity_xiangche_chakan_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        swipeTarget.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                openImgLookBig(photos.get(position).getIcon());
                File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
                BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(NXiangCheChaKanActivity.this)
                        .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
                photoPreviewIntentBuilder
                        .previewPhotos(photosStr).currentPosition(position); // 当前预览图片的索引
                NXiangCheChaKanActivity.this.startActivity(photoPreviewIntentBuilder.build());
            }
        });
    }

    private class XiangCheAdapter extends BaseQuickAdapter<UserDetailInfo.DataBean.XiangCheBean, BaseViewHolder> {

        public XiangCheAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, UserDetailInfo.DataBean.XiangCheBean item) {
            FrameLayout flBg=helper.getView(R.id.fl_bg);
            int bgWidth = (int) (SystemUtils.getScreenWidth(NXiangCheChaKanActivity.this) - DimenUtils.dp2px(NXiangCheChaKanActivity.this, 40)) / 3 ;
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth));
            ImageView ivImage=helper.getView(R.id.iv_image);
            Glide.with(NXiangCheChaKanActivity.this).load(item.getIcon()).into(ivImage);
        }
    }

    private void openImgLookBig(String avatar) {
        if (avatar != null && !avatar.equals("")) {
            ImgZoomFragment imgZoomFragment = new ImgZoomFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imgUrl", avatar);
            imgZoomFragment.setArguments(bundle);
            imgZoomFragment.show((this).getSupportFragmentManager(), "imgZoomFragment");
        }
    }

}
