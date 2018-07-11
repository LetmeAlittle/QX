package com.ysxsoft.qxerkai.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.ObserverMap;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.QingQuTypeDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class NFaTieActivity extends NBaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
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
    @BindView(R.id.tv_public_titlebar_left)
    TextView tvPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_left_2)
    ImageView ivPublicTitlebarLeft2;
    @BindView(R.id.iv_public_titlebar_right_1)
    ImageView ivPublicTitlebarRight1;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.iv_public_titlebar_right_2)
    ImageView ivPublicTitlebarRight2;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar)
    LinearLayout llPublicTitlebar;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.titleSize)
    TextView titleSize;
    @BindView(R.id.typeName)
    TextView typeName;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.contentSize)
    TextView contentSize;
    @BindView(R.id.selectTypeLayout)
    LinearLayout selectTypeLayout;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.submit)
    TextView submit;
    private String clazzName;
    private String type;//发帖类型
    private String oldTitle = "";
    private String oldContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfa_tie);
        ButterKnife.bind(this);
        handlerIntent();
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initSnpl();
        initData();
    }

    /**
     * 小情趣跳转
     *
     * @param context
     * @param clazzName
     */
    public static void start(Context context, String clazzName) {
        Intent intent = new Intent(context, NFaTieActivity.class);
        intent.putExtra("clazzName", clazzName);
        context.startActivity(intent);
    }

    /**
     * 撩人区跳转
     *
     * @param context
     * @param clazzName
     */
    public static void start(Context context, String clazzName, String type) {
        Intent intent = new Intent(context, NFaTieActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("clazzName", clazzName);
        context.startActivity(intent);
    }

    private void handlerIntent() {
        if (getIntent() == null) {
            return;
        }
        Intent intent = getIntent();
        clazzName = intent.getStringExtra("clazzName");
        type = intent.getStringExtra("type");//1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
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
        if ("5".equals(type) || "6".equals(type)) {
            bottomLayout.setVisibility(View.GONE);//5撩妹区 6撩汉区  隐藏底部上传头像
        }

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 15) {
                    showToast("标题不能超过15字！");
                    title.setText(oldTitle);
                } else {
                    titleSize.setText(s.toString().length() + "/15");
                    oldTitle = s.toString();
                }
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 150) {
                    showToast("内容不能超过150字！");
                    title.setText(oldContent);
                } else {
                    contentSize.setText(s.toString().length() + "/150");
                    oldContent = s.toString();
                }
            }
        });
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
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
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
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
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
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
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

    @OnClick({R.id.selectTypeLayout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectTypeLayout:
                QingQuTypeDialog dialog = new QingQuTypeDialog(NFaTieActivity.this,R.style.dialogQingQuStyle);
                dialog.show(new QingQuTypeDialog.OnTypeSelectListener() {
                    @Override
                    public void onSelected(String typeId, String name) {
                        typeName.setText(name);
                        type = typeId;
                    }
                });
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    /**
     * 发布页面
     */
    private void submit() {
        //1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        if ("5".equals(type) || "6".equals(type)) {
            publishLiaoRen();
        } else {
            publishQingQu();
        }
    }

    /**
     * 发布撩人区
     */
    private void publishLiaoRen() {
        if (check()) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", DBUtils.getUserId());
            map.put("content", content.getText().toString());
            map.put("title", title.getText().toString());
            map.put("type", type);

            ArrayList<String> list = mPhotosSnpl.getData();
            int size = list.size();
            String[] names = new String[size];
            File[] files = new File[size];
            for (int i = 0; i < size; i++) {
                File file = new File(list.get(i));
                names[i] = "file" + i;
                files[i] = file;
            }
            RetrofitTools
                    .publishCard(map, names, files)
                    .subscribe(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                            if (code == 200) {
                                ToastUtil.showToast(NFaTieActivity.this, msg);
                                ObserverMap.notify(clazzName);
                                finish();
                            } else {
                                ToastUtil.showToast(NFaTieActivity.this, msg);
                            }
                        }

                        @Override
                        public void onFailed(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    /**
     * 发布小情趣
     */
    private void publishQingQu() {
        if (check()) {
            Map<String, String> map = new HashMap<>();
            map.put("user_id", DBUtils.getUserId());
            map.put("content", content.getText().toString());
            map.put("title", title.getText().toString());
            map.put("type", type);

            ArrayList<String> list = mPhotosSnpl.getData();
            int size = list.size();
            String[] names = new String[size];
            File[] files = new File[size];
            for (int i = 0; i < size; i++) {
                File file = new File(list.get(i));
                names[i] = "file" + i;
                files[i] = file;
            }
            RetrofitTools
                    .publishCard(map, names, files)
                    .subscribe(new ResponseSubscriber<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                            if (code == 200) {
                                ToastUtil.showToast(NFaTieActivity.this, msg);
                                ObserverMap.notify(clazzName);
                                finish();
                            } else {
                                ToastUtil.showToast(NFaTieActivity.this, msg);
                            }
                        }

                        @Override
                        public void onFailed(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private boolean check() {
        if (StringUtil.isEmpty(title.getText().toString())) {
            showToast("请输入标题!");
            return false;
        }
        if (StringUtil.isEmpty(content.getText().toString())) {
            showToast("请输入内容!");
            return false;
        }
        if (type == null) {
            showToast("请选择发布类型!");
            return false;
        }
        return true;
    }
}
