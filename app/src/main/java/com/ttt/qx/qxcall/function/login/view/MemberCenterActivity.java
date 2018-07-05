package com.ttt.qx.qxcall.function.login.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.UriUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;


/**
 * 会员中心
 * Created by wyd on 2017/7/19.
 */
public class MemberCenterActivity extends BaseActivity {
    private Context context;
    private UserBean mUserBean;
    private String Authorization;
    @BindView(R.id.user_default_icon)
    CircleImageView user_default_icon;
    @BindView(R.id.vip_status_iv)
    ImageView vip_status_iv;
    @BindView(R.id.member_tip_tv)
    TextView member_tip_tv;
    @BindView(R.id.level_start_tv)
    TextView level_start_tv;
    @BindView(R.id.level_end_tv)
    TextView level_end_tv;
    @BindView(R.id.vip_level_sb)
    SeekBar vip_level_sb;
    @BindView(R.id.vip_level_ll)
    LinearLayout vip_level_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        context = this;
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        String token = mUserBean.getToken();
        Authorization = "Bearer " + token;
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    //根据当前级别设置 会员等级进度
                    vip_level_ll.setVisibility(View.VISIBLE);
                    UserDetailInfo.DataBean dataBean = userDetailInfo.getData();
                    int level = dataBean.getLevel();
                    vip_level_sb.setProgress(level);
                    Glide.with(context).load(dataBean.getMember_avatar())
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(user_default_icon);
                    //根据级别设置显示 灰色或黄色
                    if (level == 1) {//会员开启
                        level_start_tv.setText("LV" + level);
                        level_end_tv.setText("LV" + (level + 1));
                        vip_status_iv.setBackgroundResource(R.mipmap.vip_badge_iv);
                    } else {
                        level_start_tv.setText("LV0");
                        level_end_tv.setText("LV1");
                        vip_status_iv.setBackgroundResource(R.mipmap.vip_badge_hui_iv);
                    }
                }
            }
        }, context), "", Authorization);
    }

    /**
     * 初始化view
     */
    private void initView() {
        vip_level_sb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
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
        //Base64 方式
        //压缩图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 7;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
            String encodeString = new String(encode);
            // 调用头像上传接口
            RegisterModel.getRegisterModel().uploadHeadImg(new ProgressSubscribe<>(new SubScribeOnNextListener<UploadImgResponse>() {
                @Override
                public void onNext(UploadImgResponse uploadImgResponse) {
                    if (uploadImgResponse.getStatus_code() == 200) {
                    } else {
                        onToast(uploadImgResponse.getMessage());
                    }
                }
            }, this), encodeString, Authorization);

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
}
