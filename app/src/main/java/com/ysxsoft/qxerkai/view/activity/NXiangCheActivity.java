package com.ysxsoft.qxerkai.view.activity;

import android.Manifest;
import android.content.Intent;
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
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class NXiangCheActivity extends NBaseActivity {

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

    private Integer id;
    private UserBean mUserBean;
    private String Authorization;
    private UserDetailInfo.DataBean mInfoData;
    private boolean isShowDel=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nxiang_che);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
    }

    public void onAddIcons(View view) {
        choicePhotoWrapper();
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
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("管理");
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowDel=!isShowDel;
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        swipeTarget.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        adapter = new XiangCheAdapter(R.layout.activity_xiangche_item);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<String> photosStr = new ArrayList<>();
                for (int i = 0; i < mInfoData.getXiangce().size(); i++) {
                    photosStr.add(mInfoData.getXiangce().get(i).getIcon());
                }
                File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
                BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(NXiangCheActivity.this)
                        .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
                photoPreviewIntentBuilder
                        .previewPhotos(photosStr).currentPosition(position); // 当前预览图片的索引
                startActivity(photoPreviewIntentBuilder.build());
            }
        });
        swipeTarget.setAdapter(adapter);
    }

    private void initData() {
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        id = mUserBean.getUserId();
        Authorization = "Bearer " + mUserBean.getToken();
        //根据id 获取当前用户信息
        multipleStatusView.showLoading();
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo info) {
                multipleStatusView.hideLoading();
                if (info.getStatus_code() == 200) {
                    mInfoData = info.getData();
                    adapter.setNewData(mInfoData.getXiangce());
                } else {
                    ToastUtils.showToast(NXiangCheActivity.this, info.getMessage(), 0);
                }
            }
        }, this), String.valueOf(id), Authorization);
    }

    private class XiangCheAdapter extends BaseQuickAdapter<UserDetailInfo.DataBean.XiangCheBean, BaseViewHolder> {

        public XiangCheAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, UserDetailInfo.DataBean.XiangCheBean item) {
            FrameLayout flBg = helper.getView(R.id.fl_bg);
            int bgWidth = (int) (SystemUtils.getScreenWidth(NXiangCheActivity.this) - DimenUtils.dp2px(NXiangCheActivity.this, 40)) / 3;
            flBg.setLayoutParams(new LinearLayout.LayoutParams(bgWidth, bgWidth));
            ImageView ivImage = helper.getView(R.id.iv_image);
            Glide.with(NXiangCheActivity.this).load(item.getIcon()).into(ivImage);
            ImageView ivDel=helper.getView(R.id.iv_del);
            if(isShowDel){
                ivDel.setVisibility(View.VISIBLE);
            }else {
                ivDel.setVisibility(View.GONE);
            }
            ivDel.setOnClickListener(new DelClickListener(item.getIcon_id()));
        }
    }

    /**
     * 删除某一张图片
     */
    private class DelClickListener implements View.OnClickListener{
        private String iconId;
        public DelClickListener(String iconId){
            this.iconId=iconId;
        }
        @Override
        public void onClick(View view) {
            delImages(iconId);
        }
    }

    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;

    /**
     * 九宫格控件
     * 添加图片，申请权限
     */
    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(9 - adapter.getData().size()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    /**
     * 九宫格控件
     * 图片选择对调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            ArrayList<String> images = BGAPhotoPickerActivity.getSelectedPhotos(data);
            uploadImages(images);
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            ArrayList<String> images = BGAPhotoPickerPreviewActivity.getSelectedPhotos(data);
            uploadImages(images);
        }
    }

    private void uploadImages(ArrayList<String> images) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",""+mUserBean.getUserId());
        String[] names = new String[images.size()];
        File[] files = new File[images.size()];
        for(int i=0;i<images.size();i++){
            File file = new File(images.get(i));
            names[i] = "file" + i;
            files[i] = file;
        }
        multipleStatusView.showLoading();
        RetrofitTools.uploadIcon(hashMap,names,files)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        initData();
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                        ToastUtils.showToast(NXiangCheActivity.this,e.getMessage(),0);
                    }
                });
    }

    private void delImages(String iconId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("user_id",""+mUserBean.getUserId());
        hashMap.put("icon_id",iconId);
        multipleStatusView.showLoading();
        RetrofitTools.delIcon(hashMap)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        initData();
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                        ToastUtils.showToast(NXiangCheActivity.this,e.getMessage(),0);
                    }
                });
    }

}
