package com.ttt.qx.qxcall.function.login.view;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.UsingTagListAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerHeaderModify;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerNickModify;
import com.ttt.qx.qxcall.eventbus.NotifyUserInfoLabel;
import com.ttt.qx.qxcall.eventbus.SetUserInfoSuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.MemberTagBean;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.UriUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;
import static com.ttt.qx.qxcall.function.login.view.SetUserInfoActivity.NICK_NAME;


/**
 * 用户个人信息
 * Created by wyd on 2017/7/19.
 */
public class UserInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.user_head_icon_iv)
    CircleImageView user_head_icon_iv;
    @BindView(R.id.member_level_tv)
    TextView member_level_tv;
    @BindView(R.id.user_nick_name_tv)
    TextView user_nick_name_tv;
    @BindView(R.id.user_nick_name_tv2)
    TextView user_nick_name_tv2;
    @BindView(R.id.sign_name_tv)
    TextView sign_name_tv;
    @BindView(R.id.sex_tv)
    TextView sex_tv;
    @BindView(R.id.tag_recycler_view)
    RecyclerView tag_recycler_view;
    @BindView(R.id.birthday_tv)
    TextView birthday_tv;
    private String mMark;
    //当前通过网络首次获取过来的用户信息实体对象
    private UserDetailInfo.DataBean mUserDetailInfoData;
    private UserBean mUserBean;
    //当前跳转标记
    private String type = "";
    private UsingTagListAdapter mUsingTagListAdapter;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private int REQUEST_PERMISSION = 1111;

    @OnClick({R.id.top_back_rl, R.id.rl, R.id.nick_rl, R.id.sign_rl, R.id.sex_rl, R.id.label_rl, R.id.birthday_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.rl://用户头像设置
                String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};//PERMISSIONS
                if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
                    showSelectDialog();
                } else {
                    // Do not have permissions, request them now
                    EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                            REQUEST_PERMISSION, PERMISSIONS);
                }
                break;
            case R.id.nick_rl:
                type = NICK_NAME;
                startSetUserInfo(NICK_NAME, mUserDetailInfoData.getNick_name());
                break;
            case R.id.sign_rl:
                type = SetUserInfoActivity.SIGNATURE;
                startSetUserInfo(SetUserInfoActivity.SIGNATURE, mUserDetailInfoData.getMember_signature());
                break;
            case R.id.sex_rl:
//                startSetUserInfo(SetUserInfoActivity.NICK_NAME, mUserDetailInfoData.getMember_sex());
                onToast("用户性别无法更改！");
                break;
            case R.id.label_rl:
                type = SetUserInfoActivity.LABEL;
                IntentUtil.jumpIntent(this, SetUserTagActivity.class);
                break;
            case R.id.birthday_rl:
                type = SetUserInfoActivity.BIRTHDAY;
                startSetUserInfo(SetUserInfoActivity.BIRTHDAY, mUserDetailInfoData.getBirthday());
                break;
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

    /**
     * 启动用户信息设置视图
     *
     * @param type
     * @param content
     */
    private void startSetUserInfo(String type, String content) {
        Intent intent = new Intent(this, SetUserInfoActivity.class);
        intent.putExtra("mark", type);
        intent.putExtra("content", content);
        startActivity(intent);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserInfoActivity.this, LinearLayoutManager.HORIZONTAL, false);
        tag_recycler_view.setLayoutManager(linearLayoutManager);
        Context mContext = this;
        mMark = getIntent().getStringExtra("mark");
        //默认查询当前用户 数据库保存的用户
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //初始化控件值
                    mUserDetailInfoData = userDetailInfo.getData();
                    Glide.clear(user_head_icon_iv);
                    Glide.with(UserInfoActivity.this).load(mUserDetailInfoData.getMember_avatar())
                            .skipMemoryCache(true)//跳过内部缓存
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(user_head_icon_iv);
                    member_level_tv.setText("VIP" + mUserDetailInfoData.getLevel());
                    user_nick_name_tv.setText(mUserDetailInfoData.getNick_name());
                    user_nick_name_tv2.setText(mUserDetailInfoData.getNick_name());
                    sign_name_tv.setText(mUserDetailInfoData.getMember_signature());
                    sex_tv.setText(mUserDetailInfoData.getMember_sex().equals("1") ? "男" : "女");
                    birthday_tv.setText(mUserDetailInfoData.getBirthday() == null ? "" : mUserDetailInfoData.getBirthday().toString());
                    List<UserDetailInfo.DataBean.MemberTagBean> member_tag = mUserDetailInfoData.getMember_tag();
                    List<MemberTagBean> tags = null;
                    if (member_tag != null) {
                        tags = new ArrayList<MemberTagBean>();
                        for (int i = 0; i < member_tag.size(); i++) {
                            UserDetailInfo.DataBean.MemberTagBean memberTagBean = member_tag.get(i);
                            MemberTagBean memberTagBean1 = new MemberTagBean();
                            memberTagBean1.setId(memberTagBean.getId());
                            memberTagBean1.setText(memberTagBean.getText());
                            memberTagBean1.setColor(memberTagBean.getColor());
                            tags.add(memberTagBean1);
                        }
                        setTagData(tags);
                    }
                }
            }
        }, this), "", "Bearer " + mUserBean.getToken());
    }

    /**
     * 更新数据
     *
     * @param tags
     */
    private void setTagData(List<MemberTagBean> tags) {

        if (tags == null) {
            tags = new ArrayList<>();
        }
        if (mUsingTagListAdapter == null) {
            mUsingTagListAdapter = new UsingTagListAdapter(UserInfoActivity.this, tags);
            tag_recycler_view.setAdapter(mUsingTagListAdapter);
        } else {
            mUsingTagListAdapter.setTags(tags);
            mUsingTagListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化view
     */
    private void initView() {

    }

    @Subscribe
    public void onEventSetUserInfoSuccess(SetUserInfoSuccess setUserInfoSuccess) {
        switch (type) {
            case SetUserInfoActivity.NICK_NAME:
                mUserDetailInfoData.setNick_name(setUserInfoSuccess.content);
                user_nick_name_tv.setText(setUserInfoSuccess.content);
                user_nick_name_tv2.setText(setUserInfoSuccess.content);
                NotifyMinePagerNickModify notifyMinePagerNickModify = new NotifyMinePagerNickModify();
                notifyMinePagerNickModify.content = setUserInfoSuccess.content;
                EventBus.getDefault().post(notifyMinePagerNickModify);
                break;
            case SetUserInfoActivity.AGE:
                mUserDetailInfoData.setMember_age(setUserInfoSuccess.content);
                break;
            case SetUserInfoActivity.SIGNATURE:
                sign_name_tv.setText(setUserInfoSuccess.content);
                mUserDetailInfoData.setMember_signature(setUserInfoSuccess.content);
                break;
            case SetUserInfoActivity.BIRTHDAY:
                birthday_tv.setText(setUserInfoSuccess.content);
                mUserDetailInfoData.setBirthday(setUserInfoSuccess.content);
                break;
        }
    }

    @Subscribe
    public void onEventNotifyUserInfoLabel(NotifyUserInfoLabel notifyUserInfoLabel) {
        setTagData(notifyUserInfoLabel.tags);
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
                    // 调用头像上传接口
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
                                mUserDetailInfoData.setMember_avatar(data.getAvatar());//更新头像路径值
                                //通知minePager 头像刷新
                                NotifyMinePagerHeaderModify notifyMinePagerHeaderModify = new NotifyMinePagerHeaderModify();
                                notifyMinePagerHeaderModify.avatar = data.getAvatar();
                                EventBus.getDefault().post(notifyMinePagerHeaderModify);
                                Glide.clear(user_head_icon_iv);
                                Glide.with(UserInfoActivity.this)
                                        .load(data.getAvatar())
                                        .skipMemoryCache(true)//跳过内部缓存
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .into(user_head_icon_iv);
                            } else {
                                onToast(uploadImgResponse.getMessage());
                            }
                        }
                    }, encodeString, "Bearer " + mUserBean.getToken());
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

    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
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
}
