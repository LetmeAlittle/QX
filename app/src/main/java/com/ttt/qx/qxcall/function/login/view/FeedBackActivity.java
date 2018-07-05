package com.ttt.qx.qxcall.function.login.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.DelImg;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.UriUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;

/***
 * Created by wyd on 2017/7/30.
 */

public class FeedBackActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.public_content_et)
    EditText public_content_et;
    @BindView(R.id.importability_text_tv)
    TextView importability_text_tv;
    private UserDao userDao;
    private UserBean userBean;
    private String authorization;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        public_content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //改变之后 文本内容统计
                String content = public_content_et.getText().toString();
                if (content.length() <= 200) {
                    importability_text_tv.setText(String.valueOf(content.length()) + "/200");
                } else {
                    public_content_et.setText(content.substring(0, 200));
                    public_content_et.setSelection(200);
                    onToast("超出文本限制长度！");
                }
            }
        });
    }

    /**
     * 初始数据
     */
    private void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        userDao = new UserDao();
        userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
    }

    private int REQUEST_PERMISSION = 1111;

    @OnClick({R.id.top_back_rl, R.id.add_iv, R.id.publish_ll})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                onBackPressed();
                break;
            case R.id.add_iv:
                String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};//PERMISSIONS
                if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
                    showSelectDialog();
                } else {
                    // Do not have permissions, request them now
                    EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                            REQUEST_PERMISSION, PERMISSIONS);
                }
                break;
            case R.id.publish_ll:
                String content = public_content_et.getText().toString().trim();
                if (!content.equals("")) {
                    MineModel.getMineModel().feedBack(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                onToast("提交成功！");
                                onFinish();
                            } else {
                                onToast(standardResponse.getMessage());
                            }
                        }
                    }, this), content, "", authorization);
                } else {
                    onToast("反馈内容不能为空！");
                }
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //用户授予权限，做业务逻辑
        showSelectDialog();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //用户点击了不再询问，弹出对话框去Settings界面开启,这段代码根据业务需求可以添加，也可删去
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * 显示选择对话框
     */
    private void showSelectDialog() {
        Dialog dialog = new Dialog(this, R.style.dialogStyle);
        View view = View.inflate(this, R.layout.header_set_select_pattern, null);
        TextView select_camera_tv = (TextView) view.findViewById(R.id.top_select_tv);
        TextView select_photo_tv = (TextView) view.findViewById(R.id.botttom_select_tv);
        LinearLayout cancel_ll = (LinearLayout) view.findViewById(R.id.cancel_ll);
        select_photo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, ALBUM_REQUEST_CODE);
                dialog.dismiss();
            }
        });
        select_camera_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取SD卡安装状态
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    //设置图片保存路径
                    photoPath = QXCallApplication.basePath + System.currentTimeMillis() + ".png";
                    File imageDir = new File(photoPath);
                    if (!imageDir.exists()) {
                        try {
                            //根据一个 文件地址生成一个新的文件用来存照片
                            imageDir.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    openCamera();
                } else {
                    onToast("SD卡未插入");
                }
                dialog.dismiss();

            }
        });
        cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.getWindow().getDecorView().setPadding(10, 0, 10, 0);
        dialog.show();//显示对话框
    }

    private void openCamera() {
        //实例化intent,指向摄像头
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //根据路径实例化图片文件
        File photoFile = new File(photoPath);
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (photoFile != null && photoFile.exists()) {
                 /*获取当前系统的android版本号*/
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion < 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(intent, PHOTO_REQUEST_CODE);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                    Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, PHOTO_REQUEST_CODE);
                }
            } else {
                Toast.makeText(this, "丢失图片文件！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "没有相机", Toast.LENGTH_SHORT).show();
        }
    }

    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ALBUM_REQUEST_CODE:
                    if (data != null) {
                        try {
                            Uri uri = data.getData();
                            String absolutePath = "";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                absolutePath = UriUtil.getPath_above19(this, uri);
                            } else {
                                absolutePath = UriUtil.getFilePath_below19(this, uri);
                            }
                            File file = new File(absolutePath);
                            upload(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case PHOTO_REQUEST_CODE:
                    if (data != null) {
                        File photoFile = new File(photoPath);
                        if (photoFile.exists()) {
                            upload(photoFile);
                        } else {
                            onToast("图片文件不存在！");
                        }
                    }
                    break;
            }
        }
    }

    private final int BASE_SUCCESS = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BASE_SUCCESS:
                    String encodeString = (String) msg.obj;
                    //调用通用上传
                    RegisterModel.getRegisterModel().commonUpload(new Subscriber<UploadImgResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (!isFinishing()) {
                                if (loadingDialog != null && loadingDialog.isShowing()) {
                                    loadingDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onNext(UploadImgResponse uploadImgResponse) {
                            if (!isFinishing()) {
                                if (loadingDialog != null && loadingDialog.isShowing()) {
                                    loadingDialog.dismiss();
                                }
                            }
                            if (uploadImgResponse.getStatus_code() == 200) {
                            } else {
                                onToast(uploadImgResponse.getMessage());
                            }
                        }
                    }, encodeString);
                    break;
            }
        }
    };

    /**
     * 图片上传处理
     *
     * @param file
     */
    private void upload(File file) {
        //压缩图片 压缩判断
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 30;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        if (bitmap != null) {
            if (loadingDialog == null) {
                loadingDialog = CustomAlertDialogUtil.createLoadingDialog(this, "加载中...", true);
            }
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String encode = ImageUtil.base64Encode(file.getPath(), ImageUtil.imgSize);
                    Message message = Message.obtain();
                    message.what = BASE_SUCCESS;
                    message.obj = encode;
                    handler.sendMessage(message);
                }
            }.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }

    @Subscribe
    public void onEventDelImg(DelImg img) {

    }
}
