package com.ysxsoft.qxerkai.view.activity;

import android.Manifest;
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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.FinishLogin;
import com.ttt.qx.qxcall.eventbus.LoginSuccess;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.login.model.entity.LoginedResponse;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UserInfoSave;
import com.ttt.qx.qxcall.function.register.view.UserBaseInfoActivity;
import com.ttt.qx.qxcall.utils.AppActivityManager;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.UriUtil;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;

public class NRegisterUserEditActivity extends NBaseActivity implements EasyPermissions.PermissionCallbacks,View.OnClickListener{

    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.et_activity_nuser_username)
    EditText etActivityNuserUsername;
    @BindView(R.id.username_clear_iv)
    ImageView usernameClearIv;
    @BindView(R.id.rb_nan)
    RadioButton rbNan;
    @BindView(R.id.rb_nv)
    RadioButton rbNv;
    @BindView(R.id.tv_activity_nuser_button)
    TextView tvActivityNuserButton;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    private Dialog loadingDialog;
    //用于保存用户上传过后的头像路径
    private String userHeadPath = "";
    //用于性别 默认男
    private String sex = "1";
    private String token;
    private String phone;
    private String pwd;
    private int REQUEST_PERMISSION = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nregister_user_edit);
        token = getIntent().getStringExtra("token");
        phone = getIntent().getStringExtra("phone");
        pwd = getIntent().getStringExtra("pwd");
        ButterKnife.bind(this);
        initStatusBar();
        initView();
    }

    private void initView() {
        rbNan.setChecked(true);
        rbNan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sex="1";
                }
            }
        });
        rbNv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sex="2";
                }
            }
        });
        civHead.setOnClickListener(this);
        tvActivityNuserButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        onToast("请完善用户信息");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    private final int BASE_SUCCESS = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BASE_SUCCESS:
                    String encodeString = (String) msg.obj;
                    RegisterModel.getRegisterModel().uploadHeadImg(new Subscriber<UploadImgResponse>() {
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
                                UploadImgResponse.DataBean data = uploadImgResponse.getData();
                                userHeadPath = data.getAvatar();
                                Glide.clear(civHead);
                                Glide.with(NRegisterUserEditActivity.this)
                                        .load(userHeadPath)
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .into(civHead);
                            } else {
                                onToast(uploadImgResponse.getMessage());
                            }
                        }
                    }, encodeString, "Bearer " + token);
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
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap != null) {
            if (loadingDialog == null) {
                loadingDialog = CustomAlertDialogUtil.createLoadingDialog(this, "头像上传中...", true);
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

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this,message,0);
    }

    public void onFinish() {
        //销毁当前
        finish();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //上传头像
            case R.id.civ_head:
                String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};//PERMISSIONS
                if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
                    showSelectDialog();
                } else {
                    // Do not have permissions, request them now
                    EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                            REQUEST_PERMISSION, PERMISSIONS);
                }
                break;
            case R.id.tv_activity_nuser_button:
                if (!userHeadPath.equals("")) {
                    String nickName = etActivityNuserUsername.getText().toString().trim();
                    if (!nickName.equals("")) {
                        RegisterModel.getRegisterModel().infoSave(new Subscriber<UserInfoSave>() {
                            @Override
                            public void onCompleted() {
                                System.out.println("");
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.out.println("e = " + e);
                            }

                            @Override
                            public void onNext(UserInfoSave userInfoSave) {
                                if (userInfoSave.getStatus_code() == 200) {
                                    LoginModel.getLoginModel().login(new Subscriber<LoginedResponse>() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                        }

                                        @Override
                                        public void onNext(LoginedResponse loginedResponse) {
                                            //通知 MinePager 用户已经登录成功
                                            LoginSuccess loginSuccess = new LoginSuccess();
                                            loginSuccess.token = loginedResponse.getData().getToken();
                                            EventBus.getDefault().post(loginSuccess);
                                            onToast(userInfoSave.getMessage());
                                            EventBus.getDefault().post(new FinishLogin());
                                            finish();
                                        }
                                    }, phone, pwd);
                                } else {
                                    onToast(userInfoSave.getMessage());
                                }
                            }
                        }, nickName, sex, "", "Bearer " + token);
                    } else {
                        onToast("请填写用户昵称！");
                    }
                } else {
                    onToast("请上传用户头像！");
                }
                break;
        }
    }
}
