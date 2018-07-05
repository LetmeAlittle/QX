package com.ttt.qx.qxcall.function.login.view;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.UserMainPicAdapter;
import com.ttt.qx.qxcall.adapter.UsingTagListAdapter;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.AddressSetSuccess;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerHeaderModify;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerNickModify;
import com.ttt.qx.qxcall.eventbus.NotifyUserInfoLabel;
import com.ttt.qx.qxcall.eventbus.SetUserInfoSuccess;
import com.ttt.qx.qxcall.eventbus.UserInfoModifyed;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.MemberTagBean;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.record.RecordButton;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.UriUtil;
import com.ttt.qx.qxcall.widget.FlowLayout;

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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.options1Items;
import static com.ttt.qx.qxcall.QXCallApplication.options2Items;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;
import static com.ttt.qx.qxcall.function.login.view.SetUserInfoActivity.NICK_NAME;

/**
 * 用户主页编辑
 * Created by wyd on 2017/7/19.
 */
public class UserMainEditActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final int UPLOAD_SUCCESS = 10;
    @BindView(R.id.user_img_recycler)
    RecyclerView user_img_recycler;
    @BindView(R.id.user_head_icon_iv)
    CircleImageView user_head_icon_iv;
    @BindView(R.id.user_nick_name_tv)
    TextView user_nick_name_tv;
    @BindView(R.id.sex_tv)
    TextView sex_tv;
    @BindView(R.id.birthday_tv)
    TextView birthday_tv;
    @BindView(R.id.sign_name_tv)
    TextView sign_name_tv;
    @BindView(R.id.location_tv)
    TextView location_tv;
    @BindView(R.id.layoutPlayAudio)
    FrameLayout layoutPlayAudio;
    @BindView(R.id.timer)
    Chronometer timer;
    @BindView(R.id.timer_tip_container)
    LinearLayout timer_tip_container;
    @BindView(R.id.timer_tip)
    TextView timer_tip;
    @BindView(R.id.record_rl)
    RelativeLayout record_rl;
    @BindView(R.id.yuyin_time_rl)
    RelativeLayout yuyin_time_rl;
    @BindView(R.id.audio_record_iv)
    ImageView audio_record_iv;
    @BindView(R.id.yun_yin_play_iv)
    ImageView yun_yin_play_iv;
    @BindView(R.id.close_tv)
    TextView close_tv;
    @BindView(R.id.yuyin_introduce_tv)
    TextView yuyin_introduce_tv;
    @BindView(R.id.yuyin_time_tv)
    TextView yuyin_time_tv;
    @BindView(R.id.btn_record)
    RecordButton btn_record;
    @BindView(R.id.flow_tag_layout)
    FlowLayout flow_tag_layout;
    private Context mContext;
    private UserMainPicAdapter userMainPicAdapter;
    private Integer id;
    private UserBean mUserBean;
    private String Authorization;
    private UserDetailInfo.DataBean mInfoData;
    //当前跳转标记
    private String type = "";
    private UsingTagListAdapter mUsingTagListAdapter;
    //用户是否对资料进行了修改标记
    private boolean modifyed = false;
    //用户修改头像操作
    public final String HEAD = "head";
    //用户编辑 自己的相册的操作
    public final String USER_ALBUM = "user_album";
    //默认初始某次操作为修改头像
    public String imgType = HEAD;
    //如果用户选择的是相册图片修改 标记当前修改的是第几张图片
    public int position = 0;
    private String[] fields = new String[]{"img_1", "img_2", "img_3", "img_4"};
    private String soundsIntroduce = "";
    private MediaPlayer mPlayer = new MediaPlayer();
    private AnimationDrawable animationDrawable;
    private Dialog mUploadingDialog;
    private String mrecordTime;
    private int size = 0;
    private int REQUEST_PERMISSION = 1111;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_edit);
        ButterKnife.bind(this);
        initData();
        btn_record.setAudioRecord(new com.ttt.qx.qxcall.function.login.record.AudioRecorder());
        btn_record.setRecordListener(new RecordButton.RecordListener() {
            @Override
            public void recordEnd(String filePath, String recordTime) {
                File file = new File(filePath);
                mrecordTime = recordTime;
                uploadRecordFile(file);
            }

            @Override
            public void limitTime(String filePath, String recordTime) {
                mrecordTime = recordTime;
                TipDialog.showCenterTipDialog(mContext, "录音已达到最大时长30s，您确定发送吗？"
                        , new TipDialog.OnComponentClickListener() {
                            @Override
                            public void onCancle() {

                            }

                            @Override
                            public void onConfirm() {
                                File file = new File(filePath);
                                uploadRecordFile(file);
                            }
                        }, false);
            }
        });
    }

    private void uploadRecordFile(final File file) {
        //录音成功
        OkHttpClient okHttpClient = new OkHttpClient();
        if (mUploadingDialog == null) {
            mUploadingDialog = CustomAlertDialogUtil.createLoadingDialog(mContext, "上传中...", true);
        }
        if (!mUploadingDialog.isShowing()) {
            mUploadingDialog.show();
        }
        new Thread() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                MultipartBody multipartBody = builder.build();
                Request request = new Request.Builder().url(CommonConstant.COMMON_BASE_URL + "api/file_upload")
                        .addHeader("Authorization", Authorization)
                        .post(multipartBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("error", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Gson gson = new Gson();
                        StandardResponse standardResponse = gson.fromJson(result, StandardResponse.class);
                        Message message = Message.obtain();
                        message.obj = standardResponse.getData().getPath();
                        message.what = UPLOAD_SUCCESS;
                        mHandler.sendMessage(message);
                    }
                });
            }
        }.start();
    }

    @OnClick({R.id.top_back_rl, R.id.head_rl, R.id.nick_rl, R.id.sign_rl
            , R.id.sex_rl, R.id.label_rl, R.id.birthday_rl
            , R.id.yuyin_introduce_rl, R.id.location_rl, R.id.close_tv, R.id.yuyin_time_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                onBackPressed();
                break;
            case R.id.head_rl://用户头像设置
                modifyed = true;
                imgType = HEAD;
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
                modifyed = true;
                type = NICK_NAME;
                startSetUserInfo(NICK_NAME, mInfoData.getNick_name());
                break;
            case R.id.yuyin_introduce_rl:
                modifyed = true;
                //显示录音布局
                record_rl.setVisibility(View.VISIBLE);
                break;
            case R.id.close_tv:
                modifyed = true;
                //显示录音布局
                record_rl.setVisibility(View.GONE);
                break;
            case R.id.yuyin_time_rl:
                modifyed = true;
                //播放
                if (!soundsIntroduce.equals("")) {
                    if (mPlayer != null) {
                        animationDrawable.start();
                        mPlayer.start();
                    }
                }
                break;
            case R.id.location_rl:
                modifyed = true;
                //地址修改
                showPickerView();
                break;
            case R.id.sign_rl:
                modifyed = true;
                type = SetUserInfoActivity.SIGNATURE;
                startSetUserInfo(SetUserInfoActivity.SIGNATURE, mInfoData.getMember_signature());
                break;
            case R.id.sex_rl:
                modifyed = true;
                onToast("用户性别无法更改！");
                break;
            case R.id.label_rl:
                modifyed = true;
                type = SetUserInfoActivity.LABEL;
                IntentUtil.jumpIntent(this, SetUserTagActivity.class);
                break;
            case R.id.birthday_rl:
                modifyed = true;
                type = SetUserInfoActivity.BIRTHDAY;
                startSetUserInfo(SetUserInfoActivity.BIRTHDAY, mInfoData.getBirthday());
                break;
        }
    }

    /**
     * 显示选择对话框
     */
    public void showSelectDialog() {
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
     * 显示地址选择器
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String text = options1Items.get(options1).getPickerViewText() +
//                        options2Items.get(options1).get(options2) +
//                        options3Items.get(options1).get(options2).get(options3);
                //获取省份数据
                String province = options1Items.get(options1).getPickerViewText();
                //获取城市数据
                String city = options2Items.get(options1).get(options2);
                //调用用户地址设置接口
                MineModel.getMineModel().setAddress(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                    @Override
                    public void onNext(StandardResponse standardResponse) throws IOException {
                        if (standardResponse.getStatus_code() == 200) {
                            modifyed = true;
                            onToast("地址设置成功！");
                            location_tv.setText(province + "   " + city);
                            AddressSetSuccess addressSetSuccess = new AddressSetSuccess();
                            addressSetSuccess.address = province + "   " + city;
                            EventBus.getDefault().post(addressSetSuccess);
                        }
                    }
                }, mContext), province, city, Authorization);
            }
        }).setTitleText("")
                .setDividerColor(Color.parseColor("#ebebeb"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .setContentTextSize(size)
                .setOutSideCancelable(true)
                .build();
          /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPLOAD_SUCCESS:
                    modifyed = true;
                    /**
                     * 文件上传成功 关闭对话框
                     */
                    if (mUploadingDialog != null && mUploadingDialog.isShowing()) {
                        mUploadingDialog.dismiss();
                    }
                    String sound = (String) msg.obj;
                    //调用设置接口
                    MineModel.getMineModel().setMemberSound(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse response) throws IOException {
                            if (response.getStatus_code() == 200) {
                                //上传成功
                                yuyin_time_rl.setVisibility(View.VISIBLE);
                                yuyin_introduce_tv.setVisibility(View.GONE);
                                record_rl.setVisibility(View.GONE);
                                //初始化
                                soundsIntroduce = sound;
                                mPlayer = new MediaPlayer();
                                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                        animationDrawable.stop();
                                    }
                                });
                                mPlayer.setDataSource(mContext, Uri.parse(sound));//url
                                mPlayer.prepareAsync();
                                yuyin_time_tv.setText(mrecordTime + "''");
                            }
                        }
                    }, mContext), sound, Authorization);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (modifyed) {
            EventBus.getDefault().post(new UserInfoModifyed());
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
        _ffffff = getResources().getColor(R.color._ffffff);
        tagSize = getResources().getInteger(R.integer.fm_anim_i12);
        mContext = this;
        Intent intent = getIntent();
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        String token = mUserBean.getToken();
        Authorization = "Bearer " + mUserBean.getToken();
        id = intent.getIntExtra("id", -1);
        //根据id 获取当前用户信息
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo info) {
                if (info.getStatus_code() == 200) {
                    mInfoData = info.getData();
                    initView();
                } else {
                    onToast(info.getMessage());
                }
            }
        }, this), String.valueOf(id), Authorization);
        size = getResources().getInteger(R.integer.i16);
    }

    /**
     * 初始化view
     */
    private void initView() {
        yun_yin_play_iv.setBackgroundResource(R.drawable.souds_player_anim);
        animationDrawable = (AnimationDrawable) yun_yin_play_iv.getBackground();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                animationDrawable.stop();
            }
        });
        //初始化相关控件
        if (mInfoData != null) {
            setUserHeadIcon(mInfoData.getMember_avatar());
            user_nick_name_tv.setText(mInfoData.getNick_name());
            sign_name_tv.setText(mInfoData.getMember_signature());
            birthday_tv.setText(mInfoData.getBirthday());
            if (mInfoData.getMember_sex().equals("1")) {
                sex_tv.setText("男");
            } else {
                sex_tv.setText("女");
            }
            String province = mInfoData.getMember_province();
            if (province != null && !province.equals("")) {
                location_tv.setText(province + "   " + mInfoData.getMember_city());
            } else {
                location_tv.setText("未添加");
            }
            String soundsFile = mInfoData.getSound_file();
            if (soundsFile != null && !soundsFile.equals("")) {

                soundsIntroduce = soundsFile;
                try {
                    mPlayer.setDataSource(mContext, Uri.parse(soundsFile));
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            int duration = mPlayer.getDuration();
                            yuyin_time_tv.setText("" + String.valueOf(duration / 1000) + "''");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                yuyin_introduce_tv.setVisibility(View.GONE);
                yuyin_time_rl.setVisibility(View.VISIBLE);
            } else {
                yuyin_introduce_tv.setVisibility(View.VISIBLE);
                yuyin_time_rl.setVisibility(View.GONE);
            }
            //设置当前
            List<String> imgs = new ArrayList<>();
            imgs.add(mInfoData.getMember_img_1());
            imgs.add(mInfoData.getMember_img_2());
            imgs.add(mInfoData.getMember_img_3());
            imgs.add(mInfoData.getMember_img_4());
            userMainPicAdapter = new UserMainPicAdapter(this, imgs, true);
            LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            user_img_recycler.setLayoutManager(layout);
            user_img_recycler.setAdapter(userMainPicAdapter);
            List<UserDetailInfo.DataBean.MemberTagBean> member_tag = mInfoData.getMember_tag();
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

    public void delUserMainPic(int position) {
        //调用删除操作
        RegisterModel.getRegisterModel().delUserMainPic(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
            @Override
            public void onNext(StandardResponse standardResponse) {
                if (standardResponse.getStatus_code() == 200) {
                    onToast("删除成功！");
                    userMainPicAdapter.setImg("", position);
                    userMainPicAdapter.notifyDataSetChanged();
                    modifyed = true;
                }

            }
        }, this), fields[position], "Bearer " + mUserBean.getToken());
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
                File photoFile = new File(photoPath);
                if (photoFile.exists()) {
                    upload(photoFile);
                } else {
                    onToast("图片文件不存在！");
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
                    if (imgType.equals(HEAD)) {//如果修改的是头像
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
                                    mInfoData.setMember_avatar(data.getAvatar());//更新头像路径值
                                    //通知minePager 头像刷新
                                    NotifyMinePagerHeaderModify notifyMinePagerHeaderModify = new NotifyMinePagerHeaderModify();
                                    notifyMinePagerHeaderModify.avatar = data.getAvatar();
                                    EventBus.getDefault().post(notifyMinePagerHeaderModify);
                                    setUserHeadIcon(data.getAvatar());
                                    modifyed = true;
                                } else {
                                    onToast(uploadImgResponse.getMessage());
                                }
                            }
                        }, encodeString, "Bearer " + mUserBean.getToken());
                    } else {
                        RegisterModel.getRegisterModel().uploadUserMainPic(new Subscriber<UploadImgResponse>() {
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
                                    //首先获取上传后的图片路径
                                    String img = data.getImg();
                                    userMainPicAdapter.setImg(img, position);
                                    userMainPicAdapter.notifyDataSetChanged();
                                    modifyed = true;
                                } else {
                                    onToast(uploadImgResponse.getMessage());
                                }

                            }
                        }, encodeString, fields[position], "Bearer " + mUserBean.getToken());
                    }
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
        if (file != null && file.exists()) {
            //压缩图片 压缩判断
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 30;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            if (bitmap != null) {
                if (loadingDialog==null) {
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
        } else {
            onToast("选择图片失败");
        }
    }

    @Subscribe
    public void onEventSetUserInfoSuccess(SetUserInfoSuccess setUserInfoSuccess) {
        modifyed = true;
        switch (type) {
            case NICK_NAME:
                mInfoData.setNick_name(setUserInfoSuccess.content);
                user_nick_name_tv.setText(setUserInfoSuccess.content);
                NotifyMinePagerNickModify notifyMinePagerNickModify = new NotifyMinePagerNickModify();
                notifyMinePagerNickModify.content = setUserInfoSuccess.content;
                EventBus.getDefault().post(notifyMinePagerNickModify);
                break;
            case SetUserInfoActivity.AGE:
                mInfoData.setMember_age(setUserInfoSuccess.content);
                break;
            case SetUserInfoActivity.SIGNATURE:
                sign_name_tv.setText(setUserInfoSuccess.content);
                mInfoData.setMember_signature(setUserInfoSuccess.content);
                break;
            case SetUserInfoActivity.BIRTHDAY:
                birthday_tv.setText(setUserInfoSuccess.content);
                mInfoData.setBirthday(setUserInfoSuccess.content);
                break;
        }
    }

    @Subscribe
    public void onEventNotifyUserInfoLabel(NotifyUserInfoLabel notifyUserInfoLabel) {
        setTagData(notifyUserInfoLabel.tags);
        modifyed = true;
    }

    private int _ffffff;
    private float tagSize;

    /**
     * 更新数据
     *
     * @param tags
     */
    private void setTagData(List<MemberTagBean> tags) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        flow_tag_layout.removeAllViews();
        for (MemberTagBean tag : tags) {
            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 35;
            TextView textView = new TextView(mContext);
            textView.setTextColor(_ffffff);
            textView.setTextSize(tagSize);
            textView.setText(tag.getText());
            textView.setLayoutParams(layoutParams);
            flow_tag_layout.addView(textView);
        }
    }

    private void setUserHeadIcon(String avatar) {
        Glide.clear(user_head_icon_iv);
        Glide.with(UserMainEditActivity.this)
                .load(avatar)
                .skipMemoryCache(true)//跳过内部缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_head_icon_iv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
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
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.release();
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
}
