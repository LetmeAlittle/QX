package com.ysxsoft.qxerkai.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.AddressSetSuccess;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerHeaderModify;
import com.ttt.qx.qxcall.eventbus.NotifyMinePagerNickModify;
import com.ttt.qx.qxcall.eventbus.SetUserInfoSuccess;
import com.ttt.qx.qxcall.eventbus.UserInfoModifyed;
import com.ttt.qx.qxcall.eventbus.VerifySuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.record.AudioRecorder;
import com.ttt.qx.qxcall.function.login.record.RecordButton;
import com.ttt.qx.qxcall.function.login.view.IdentifyAuthActivity;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.SetUserInfoActivity;
import com.ttt.qx.qxcall.function.login.view.SetUserTagActivity;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.UriUtil;
import com.ttt.qx.qxcall.widget.FlowLayout;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.MemberListResponse;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.login;
import static com.ttt.qx.qxcall.QXCallApplication.options1Items;
import static com.ttt.qx.qxcall.QXCallApplication.options2Items;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;
import static com.ttt.qx.qxcall.function.login.view.SetUserInfoActivity.NICK_NAME;

/**
 * 个人中心
 *
 * @author zhaozhipeng
 */
public class NPersonCenterActivity extends NBaseActivity {
    private static final int UPLOAD_SUCCESS = 10;
    //用户修改头像操作
    public final String HEAD = "head";
    private final int BASE_SUCCESS = 0;
    //默认初始某次操作为修改头像
    public String imgType = HEAD;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_renzheng)
    TextView tvRenzheng;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_shenfen)
    TextView tvShenfen;
    @BindView(R.id.ll_shenfen)
    LinearLayout llShenfen;
    @BindView(R.id.paly_status_iv)
    ImageView palyStatusIv;
    @BindView(R.id.audio_time_tv)
    TextView audioTimeTv;
    @BindView(R.id.sounds_ll)
    LinearLayout soundsLl;
    @BindView(R.id.flow_tag_layout)
    FlowLayout flowTagLayout;
    @BindView(R.id.audio_record_iv)
    ImageView audioRecordIv;
    @BindView(R.id.btn_record)
    RecordButton btn_record;
    @BindView(R.id.close_tv)
    TextView closeTv;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    @BindView(R.id.ll_yuyin)
    LinearLayout llYuyin;

    private String mrecordTime;
    private Dialog mUploadingDialog;
    private String mSoundFile;
    private MediaPlayer mPlayer = new MediaPlayer();

    private Integer id;
    private UserBean mUserBean;
    private String Authorization;
    private UserDetailInfo.DataBean mInfoData;
    //用户是否对资料进行了修改标记
    private boolean modifyed = false;
    private int REQUEST_PERMISSION = 1111;
    private Dialog loadingDialog;
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
                    }
                    break;
            }
        }
    };
    //当前跳转标记
    private String type = "";
    private boolean verifyStatus = false;
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
                                initData();
                            }
                        }
                    }, NPersonCenterActivity.this), sound, Authorization);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nperson_center);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        btn_record.setAudioRecord(new AudioRecorder());
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
                TipDialog.showCenterTipDialog(NPersonCenterActivity.this, "录音已达到最大时长30s，您确定发送吗？"
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        id = mUserBean.getUserId();
        Authorization = "Bearer " + mUserBean.getToken();
        //根据id 获取当前用户信息
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo info) {
                if (info.getStatus_code() == 200) {
                    mInfoData = info.getData();
                    initView();
                } else {
                    ToastUtils.showToast(NPersonCenterActivity.this, info.getMessage(), 0);
                }
            }
        }, this), String.valueOf(id), Authorization);
    }

    private void initView() {
        Glide.with(this).load(mInfoData.getMember_avatar()).into(civHead);
        tvName.setText(mInfoData.getNick_name());
        tvId.setText(mInfoData.getId() + "");
        tvAge.setText(mInfoData.getMember_age());
        if (mInfoData.getMember_sex().equals("1")) {
            ivSex.setImageResource(R.mipmap.fragment_five_sex_nan);
        } else {
            ivSex.setImageResource(R.mipmap.fragment_five_sex_nv);
        }
        if (mInfoData.getLevel() == 0) {
            tvVip.setVisibility(View.VISIBLE);
            ivVip.setVisibility(View.GONE);
        } else {
            tvVip.setVisibility(View.GONE);
            ivVip.setVisibility(View.VISIBLE);
        }
        //认证状态设置
        if (mInfoData.getIs_true() == 1) {
            verifyStatus = true;
            tvRenzheng.setText("已认证");
        } else {
            verifyStatus = false;
            tvRenzheng.setText("未认证");
        }
        tvAddress.setText(mInfoData.getMember_province() + mInfoData.getMember_city());
        //标签处理
        List<UserDetailInfo.DataBean.MemberTagBean> member_tag = mInfoData.getMember_tag();
        if (member_tag.size() > 0) {
            flowTagLayout.setVisibility(View.VISIBLE);
        }
        flowTagLayout.removeAllViews();
        for (UserDetailInfo.DataBean.MemberTagBean memberTagBean : member_tag) {
            FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 25;
            layoutParams.topMargin = 10;
            layoutParams.bottomMargin = 10;
            TextView textView = new TextView(this);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setBackgroundResource(R.drawable.activity_biaoqian_bg);
            GradientDrawable myGrad = (GradientDrawable) textView.getBackground();
            myGrad.setColor(Color.parseColor(memberTagBean.getColor()));
            textView.setTextSize(10);
            textView.getPaint().setFakeBoldText(true);
            textView.setText(memberTagBean.getText());
            textView.setPadding(10, 2, 10, 2);
            textView.setLayoutParams(layoutParams);
            flowTagLayout.addView(textView);
        }
        mSoundFile = mInfoData.getSound_file();
        initMediaPlayer();
        tvShenfen.setText(mInfoData.getMember_cate_name());
    }

    private void initMediaPlayer() {
        if (mSoundFile != null && !mSoundFile.equals("")) {
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer player) {
                        palyStatusIv.setImageResource(R.mipmap.activity_zhiliao_bofang);
                        mPlayer.release();
                        mPlayer = null;
                    }
                });
                mPlayer.setDataSource(NPersonCenterActivity.this, Uri.parse(mSoundFile));
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        audioTimeTv.setText(String.valueOf(mPlayer.getDuration() / 1000) + "''");
                    }
                });
                soundsLl.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            soundsLl.setVisibility(View.GONE);
        }
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyed) {
                    EventBus.getDefault().post(new UserInfoModifyed());
                }
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("个人中心");
    }

    private void uploadRecordFile(final File file) {
        llYuyin.setVisibility(View.GONE);
        //录音成功
        OkHttpClient okHttpClient = new OkHttpClient();
        if (mUploadingDialog == null) {
            mUploadingDialog = CustomAlertDialogUtil.createLoadingDialog(this, "上传中...", true);
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

    @OnClick({R.id.ll_xiangche, R.id.ll_headimg, R.id.ll_gerenjiesao, R.id.ll_nickname, R.id.ll_age, R.id.ll_address,
            R.id.ll_sex, R.id.ll_realname, R.id.ll_shenfen, R.id.ll_shengyin, R.id.ll_biaoqian, R.id.ll_yuyin,
            R.id.sounds_ll, R.id.close_tv})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ll_xiangche:
                startActivity(new Intent(this, NXiangCheActivity.class));
                break;
            case R.id.ll_headimg:
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
            case R.id.ll_gerenjiesao:
                modifyed = true;
                type = SetUserInfoActivity.SIGNATURE;
                startSetUserInfo(SetUserInfoActivity.SIGNATURE, mInfoData.getMember_signature());
                break;
            case R.id.ll_nickname:
                modifyed = true;
                type = NICK_NAME;
                startSetUserInfo(NICK_NAME, mInfoData.getNick_name());
                break;
            case R.id.ll_age:
                modifyed = true;
                type = SetUserInfoActivity.BIRTHDAY;
                startSetUserInfo(SetUserInfoActivity.BIRTHDAY, mInfoData.getMember_age());
                break;
            case R.id.ll_address:
                modifyed = true;
                //地址修改
                showPickerView();
                break;
            case R.id.ll_sex:
                onToast("用户性别无法更改！");
                break;
            case R.id.ll_realname:
                if (login) {
                    if (verifyStatus) {
                        TipDialog.showCenterTipDialog(NPersonCenterActivity.this, "您已提交身份信息，确定要重新提交吗？", new TipDialog.OnComponentClickListener() {
                            @Override
                            public void onCancle() {
                            }

                            @Override
                            public void onConfirm() {
                                IntentUtil.jumpIntent(NPersonCenterActivity.this, IdentifyAuthActivity.class);
                            }
                        }, true);
                    } else {
                        IntentUtil.jumpIntent(NPersonCenterActivity.this, IdentifyAuthActivity.class);
                    }
                } else {
                    IntentUtil.jumpIntent(NPersonCenterActivity.this, LoginTransferActivity.class);
                }
                break;
//                修改我的身份
            case R.id.ll_shenfen:
                RetrofitTools.getMemberList().subscribe(new ResponseSubscriber<MemberListResponse>() {
                    @Override
                    public void onSuccess(MemberListResponse memberListResponse, int code, String msg) {
                        String[] items = new String[memberListResponse.getData().size()];
                        for (int i = 0; i < memberListResponse.getData().size(); i++) {
                            items[i] = memberListResponse.getData().get(i).getName();
                        }
                        new MaterialDialog.Builder(NPersonCenterActivity.this)
                                .title("设置身份")
                                .titleGravity(GravityEnum.CENTER)
                                .items(items)
                                .itemsCallback(new MaterialDialog.ListCallback() {//选中监听，同时dialog消失
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        multipleStatusView.showLoading();
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("user_id", "" + mUserBean.getUserId());
                                        hashMap.put("cate_id", memberListResponse.getData().get(position).getId());
                                        RetrofitTools
                                                .updateUserCate(hashMap)
                                                .subscribe(new ResponseSubscriber<BaseResponse>() {
                                                    @Override
                                                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                                                        multipleStatusView.hideLoading();
                                                        initData();
                                                    }

                                                    @Override
                                                    public void onFailed(Throwable e) {
                                                        multipleStatusView.hideLoading();
                                                        ToastUtils.showToast(NPersonCenterActivity.this, e.getMessage(), 0);
                                                    }
                                                });
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        ToastUtils.showToast(NPersonCenterActivity.this, e.getMessage(), 0);
                    }
                });
                break;
//                录入我的声音
            case R.id.ll_shengyin:
                llYuyin.setVisibility(View.VISIBLE);
                break;
//                设置我的标签
            case R.id.ll_biaoqian:
                startActivity(new Intent(this, SetUserTagActivity.class));
                break;
            case R.id.ll_yuyin:
                llYuyin.setVisibility(View.GONE);
                break;
            case R.id.close_tv:
                llYuyin.setVisibility(View.GONE);
                break;
            case R.id.sounds_ll:
                palyStatusIv.setImageResource(R.mipmap.audio_playing_iv);
                if (mPlayer != null) {
                    mPlayer.start();
                } else {
                    mPlayer = new MediaPlayer();
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer player) {
                            palyStatusIv.setImageResource(R.mipmap.activity_zhiliao_bofang);
                            mPlayer.release();
                            mPlayer = null;
                        }
                    });
                    try {
                        mPlayer.setDataSource(NPersonCenterActivity.this, Uri.parse(mSoundFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mPlayer.start();
                        }
                    });
                }
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
                            tvAddress.setText(province + city);
                            AddressSetSuccess addressSetSuccess = new AddressSetSuccess();
                            addressSetSuccess.address = province + "   " + city;
                            EventBus.getDefault().post(addressSetSuccess);
                        }
                    }
                }, NPersonCenterActivity.this), province, city, Authorization);
            }
        }).setTitleText("")
                .setDividerColor(Color.parseColor("#ebebeb"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .setContentTextSize(16)
                .setOutSideCancelable(true)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtils.showToast(this, message, Toast.LENGTH_SHORT);
    }

    private void openCamera() {
        //实例化intent,指向摄像头
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //根据路径实例化图片文件
        File photoFile = new File(photoPath);
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (photoFile != null && photoFile.exists()) {
                /*获取当前系统的android版本号*/
                int currentapiVersion = Build.VERSION.SDK_INT;
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

    @Subscribe
    public void onEventVerifySuccess(VerifySuccess verifySuccess) {
        verifyStatus = true;
        tvRenzheng.setText("已认证");
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

    /**
     * 图片上传处理
     *
     * @param file
     */
    private void upload(File file) {
        if (file != null && file.exists()) {
            //压缩图片 压缩判断
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 30;
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
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
        } else {
            onToast("选择图片失败");
        }
    }

    private void setUserHeadIcon(String avatar) {
        Glide.clear(civHead);
        Glide.with(NPersonCenterActivity.this)
                .load(avatar)
                .skipMemoryCache(true)//跳过内部缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(civHead);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (modifyed) {
            EventBus.getDefault().post(new UserInfoModifyed());
        }
    }

    @Subscribe
    public void onEventSetUserInfoSuccess(SetUserInfoSuccess setUserInfoSuccess) {
        modifyed = true;
        switch (type) {
            case NICK_NAME:
                mInfoData.setNick_name(setUserInfoSuccess.content);
                tvName.setText(setUserInfoSuccess.content);
                NotifyMinePagerNickModify notifyMinePagerNickModify = new NotifyMinePagerNickModify();
                notifyMinePagerNickModify.content = setUserInfoSuccess.content;
                EventBus.getDefault().post(notifyMinePagerNickModify);
                break;
            case SetUserInfoActivity.AGE:
                tvAge.setText(setUserInfoSuccess.content);
                mInfoData.setMember_age(setUserInfoSuccess.content);
                break;
            case SetUserInfoActivity.SIGNATURE:
                mInfoData.setMember_signature(setUserInfoSuccess.content);
                break;
            case SetUserInfoActivity.BIRTHDAY:
                mInfoData.setBirthday(setUserInfoSuccess.content);
                break;
        }
    }

}
