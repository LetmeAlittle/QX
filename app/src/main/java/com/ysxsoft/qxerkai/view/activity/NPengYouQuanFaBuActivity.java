package com.ysxsoft.qxerkai.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.utils.CollectionToStringUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

public class NPengYouQuanFaBuActivity extends NBaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.et_public)
    EditText etPublic;
    @BindView(R.id.tv_public)
    TextView tvPublic;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;

    private String authorization ="";

    private void onToast(String s) {
        ToastUtil.show(this, s, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npengyouquan_fabu);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initSnpl();
        initData();
        setListener();
    }

    private void setListener() {
        etPublic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //改变之后 文本内容统计
                String content = etPublic.getText().toString();
                if (content.length() <= 500) {
                    tvPublic.setText(String.valueOf(content.length()) + "/500");
                } else {
                    etPublic.setText(content.substring(0, 500));
                    etPublic.setSelection(500);
                    onToast("超出文本限制长度！");
                }
            }
        });
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
        tvPublicTitlebarCenter.setText("发布");
    }

    private void initView() {

    }

    /**
     * 初始化九宫格图片控件
     */
    private void initSnpl() {
        //设置最大选择张数
        mPhotosSnpl.setMaxItemCount(9);
        //设置是否可编辑
        mPhotosSnpl.setEditable(true);
        //显示九图控件的加号按钮
        mPhotosSnpl.setPlusEnable(true);
        //是否可拖拽排序
        mPhotosSnpl.setSortable(true);
        //设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
    }

    private void initData() {
        imgLists = new ArrayList<>();

        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            String token = userBean.getToken() + "";
            authorization = "Bearer" + token;
        }
    }

    /**
     * 九宫格控件
     * 点击添加加号按钮执行的方法
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param models
     */
    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout,
                                        View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    /**
     * 九宫格控件
     * 点击删除按钮执行的方法
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param model
     * @param models
     */
    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout,
                                           View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
        imgLists.remove(position);
    }

    /**
     * 九宫格控件
     * 点击图片进入预览界面
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param model
     * @param models
     */
    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    /**
     * 九宫格控件
     * 拖拽排序发生变法执行的方法
     *
     * @param sortableNinePhotoLayout
     * @param fromPosition
     * @param toPosition
     * @param models
     */
    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout,
                                         int fromPosition, int toPosition, ArrayList<String> models) {
        //排序发生变化
    }

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
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    /**
     * 用户同意授权
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    /**
     * 用户拒绝授权
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
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
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }




    @OnClick(R.id.btn_public)
    public void onViewClicked() {
        String content = etPublic.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            onToast("说说内容不能为空！");
            return;
        }

        ArrayList<String> mPhotosSnplData = mPhotosSnpl.getData();
        if (mPhotosSnplData != null && mPhotosSnplData.size() > 0) {
            for (int i = 0; i < mPhotosSnplData.size(); i++) {
                String encode = ImageUtil.base64Encode(mPhotosSnplData.get(i), ImageUtil.imgSize);
                commitPic(encode, i, mPhotosSnplData.size()-1);
            }
        } else {
            onToast("请选择图片！");
        }
    }

    private List<String> imgLists;
    private void commitPic(String encodeString, int position, int size) {
        multipleStatusView.showLoading();
        //调用通用上传
        RegisterModel.getRegisterModel().commonUpload(new Subscriber<UploadImgResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                multipleStatusView.hideLoading();
            }

            @Override
            public void onNext(UploadImgResponse uploadImgResponse) {
                if (uploadImgResponse.getStatus_code() == 200) {
                    //将文件对象添加至集合中
                    UploadImgResponse.DataBean data = uploadImgResponse.getData();
                    //将图片 对应的url 添加之url集合中
                    imgLists.add(data.getImg());
                    if (size == position) {
                        postPublic(etPublic.getText().toString().trim());
                    }
                } else {
                    onToast(uploadImgResponse.getMessage());
                }
            }
        }, encodeString);
    }

    private void postPublic(String content) {
        FindModel.getFindModel().publishDynamic(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
            @Override
            public void onNext(StandardResponse standardResponse) {
                if (standardResponse.getStatus_code() == 200) {
                    multipleStatusView.hideLoading();
                    onToast("发表成功！");
                    finish();
                } else {
                    multipleStatusView.hideLoading();
                    onToast(standardResponse.getMessage());
                }
            }
        }, this), content,
                CollectionToStringUtil.collToString((ArrayList) imgLists, "|"),
                authorization);

    }
}
