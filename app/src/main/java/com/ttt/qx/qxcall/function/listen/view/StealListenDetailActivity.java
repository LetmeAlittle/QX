package com.ttt.qx.qxcall.function.listen.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.CoinNoEnough;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.entity.BlurEntity;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.listen.model.entity.RandomStealListen;
import com.ttt.qx.qxcall.function.listen.model.entity.StealDetailResponse;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.RechargeActivity;
import com.ttt.qx.qxcall.function.register.model.RegisterModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.register.model.entity.UploadImgResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.utils.UriUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.ALBUM_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.PHOTO_REQUEST_CODE;
import static com.ttt.qx.qxcall.dialog.ShowSelectImgDialog.photoPath;


/**
 * 偷听详情
 * Created by wyd on 2017/7/19.
 */
public class StealListenDetailActivity extends BaseActivity {
    private static final int UPDTAE_PROGRESS = 0;
    private Context context;
    private UserBean mUserBean;
    private String Authorization;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    //偷听详情id
    private int id;
    //当前剩余金币数memberAccount
    private int memberAccount;
    @BindView(R.id.call_avatar_iv)
    CircleImageView call_avatar_iv;
    @BindView(R.id.replay_avatar_iv)
    CircleImageView replay_avatar_iv;
    @BindView(R.id.dian_zan_iv)
    ImageView dian_zan_iv;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.gold_coin_num_tv)
    TextView gold_coin_num_tv;
    @BindView(R.id.total_time_tv)
    TextView total_time_tv;
    @BindView(R.id.current_time_tv)
    TextView current_time_tv;
    @BindView(R.id.have_listen_time_tv)
    TextView have_listen_time_tv;

    Timer timer;
    Timer playTimer;
    //视图关闭 标记
    private boolean viewclose = false;
    private SimpleDateFormat simpleDateFormat;
    //记录当前由于 播放金币不足的暂停的播放位置
    private int currentPosition;
    //用户打开视图的时间
    private int openViewTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steal_listen_detail);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.hang, R.id.dian_zan_iv, R.id.huan_ren_iv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                viewclose = true;
                finish();
                break;
            case R.id.hang:
                viewclose = true;
                finish();
                break;
            case R.id.huan_ren_iv:
                reset();
                openViewTime = 0;
                seekbar.setProgress(0);
                transferPerson();
                break;
            case R.id.dian_zan_iv:
                //点赞处理
                StealListenModel.getStealListenModel().giveThumbs(new Subscriber<StandardResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(StandardResponse standardResponse) {
                        if (standardResponse.getStatus_code() == 200) {
                            onToast("点赞成功！");
                            dian_zan_iv.setBackgroundResource(R.mipmap.steal_dian_zan_iv);
                        } else {
                            onToast(standardResponse.getMessage());
                        }
                    }
                }, String.valueOf(id), Authorization);
                break;
        }
    }

    /**
     * 换人处理
     */
    private void transferPerson() {
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        if (userBean != null) {
            String Authorization = "Bearer " + userBean.getToken();
            //随机偷听处理
            StealListenModel.getStealListenModel().getRandomStealListenList(new ProgressSubscribe<>(new SubScribeOnNextListener<RandomStealListen>() {
                @Override
                public void onNext(RandomStealListen stealListenList) throws IOException {
                    if (stealListenList.getStatus_code() == 200) {
                        RandomStealListen.DataBean data = stealListenList.getData();
                        if (data != null) {
                            int id = data.getId();
                            //首先判断用户是否可以偷听 偷听费用是否满足
                            StealListenModel.getStealListenModel().stealListenDeduction(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                @Override
                                public void onNext(StandardResponse standardResponse) {
                                    if (standardResponse.getStatus_code() == 200) {
                                        StandardResponse.DataBean data = standardResponse.getData();
                                        if (data.getIs_allow() == 1) {
                                            //换人 通过允许后处理
                                            StealListenDetailActivity.this.id = id;
                                            gold_coin_num_tv.setText(String.valueOf(data.getMember_account()));
                                            stealHandle();
                                        } else {
                                            //偷听费用不足 弹出是否充值对话框
                                            TipDialog.showCenterTipDialog(context, "当前剩余钻石" + data.getMember_account() + ",偷听钻石不足,是否前去充值？", new TipDialog.OnComponentClickListener() {
                                                @Override
                                                public void onCancle() {
                                                    //用户取消操作
                                                }

                                                @Override
                                                public void onConfirm() {
                                                    //用户点击确定执行 相关逻辑
                                                    IntentUtil.jumpIntent(context, RechargeActivity.class);
                                                }
                                            }, true);
                                        }

                                    } else {

                                    }
                                }
                            }, context), String.valueOf(id), "Bearer " + userBean.getToken());
                        }
                    }
                }
            }, context), Authorization);
        } else {
            IntentUtil.jumpIntent(context, LoginTransferActivity.class);
        }
    }

    /**
     * 扣费定时器
     */
    class MyDedcutionTimerTask extends TimerTask {

        @Override
        public void run() {
            StealListenModel.getStealListenModel().stealListenDeduction(new Subscriber<StandardResponse>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(StandardResponse standardResponse) {
                    if (standardResponse.getStatus_code() == 200) {
                        //如果 金币满足偷听 继续偷听
                        StandardResponse.DataBean data = standardResponse.getData();
                        if (data.getIs_allow() == 1) {
                            gold_coin_num_tv.setText(String.valueOf(data.getMember_account()));
                        } else {
                            CoinNoEnough coinNoEnough = new CoinNoEnough();
                            coinNoEnough.detainCoin = data.getMember_account();
                            EventBus.getDefault().post(coinNoEnough);
                        }
                    }
                }
            }, String.valueOf(id), Authorization);
        }
    }

    /**
     * 播放进度定时器
     */
    class MyPlayerTimerTask extends TimerTask {

        @Override
        public void run() {
            if (!viewclose) {
                if (mMediaPlayer != null) {
                    int currentPosition = mMediaPlayer.getCurrentPosition();
                    Message message = new Message();
                    message.what = UPDTAE_PROGRESS;
                    message.obj = currentPosition;
                    handler.sendMessage(message);
                }
            }
        }
    }

    /**
     * 初始化数据
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        context = this;
        simpleDateFormat = new SimpleDateFormat("mm:ss");
        id = getIntent().getIntExtra("id", 2);
        memberAccount = getIntent().getIntExtra("memberAccount", 0);
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        String token = mUserBean.getToken();
        Authorization = "Bearer " + token;
        seekbar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        //根据id 获取偷听文件
        stealHandle();
    }

    /**
     * 根据id偷听处理
     */
    private void stealHandle() {
        StealListenModel.getStealListenModel().getStealListenDetail(new Subscriber<StealDetailResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(StealDetailResponse stealDetailResponse) {
                if (stealDetailResponse.getStatus_code() == 200) {
                    try {
                        StealDetailResponse.DataBean data = stealDetailResponse.getData();
                        //设置控件头像
                        StealDetailResponse.DataBean.CallMemberBean call_member = data.getCall_member();
                        StealDetailResponse.DataBean.ReplyMemberBean reply_member = data.getReply_member();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = Glide.with(context)
                                            .load(call_member.getMember_avatar())
                                            .asBitmap() //必须
                                            .centerCrop()
                                            .into(500, 500)
                                            .get();
                                    BlurEntity blurEntity = new BlurEntity();
                                    blurEntity.bitmap = bitmap;
                                    blurEntity.imageView = call_avatar_iv;
                                    Message message = Message.obtain();
                                    message.what = SUCCESS;
                                    message.obj = blurEntity;
                                    handler.sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = Glide.with(context)
                                            .load(reply_member.getMember_avatar())
                                            .asBitmap() //必须
                                            .centerCrop()
                                            .into(500, 500)
                                            .get();
                                    BlurEntity blurEntity = new BlurEntity();
                                    blurEntity.bitmap = bitmap;
                                    blurEntity.imageView = replay_avatar_iv;
                                    Message message = Message.obtain();
                                    message.what = SUCCESS;
                                    message.obj = blurEntity;
                                    handler.sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        String playUrl = data.getFile();
                        //成功就开始播放
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(context, Uri.parse(playUrl));
//                        mMediaPlayer.setDataSource(context, Uri.parse("http://116.62.217.183/storage/sound/2017/10/27/193144962533761_mix.aac"));
                        mMediaPlayer.prepareAsync();
                        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer player) {
                                //设置 进度条最大进度
                                int duration = mMediaPlayer.getDuration();
                                seekbar.setMax(duration);
                                //初始化总时长
                                total_time_tv.setText(simpleDateFormat.format(new Date(duration)));
                                current_time_tv.setText("00:00");
                                playTimer = new Timer();
                                playTimer.schedule(new MyPlayerTimerTask(), 0, 1000);//每隔一秒刷新一下数据
                                mMediaPlayer.start();
                            }
                        });
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                reset();
                            }
                        });
                        //一分钟之后 开启定时 器 每隔一分钟请求一次
                        timer = new Timer();
                        timer.schedule(new MyDedcutionTimerTask(), 60000, 60000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }

            }
        }, String.valueOf(id), Authorization);
    }

    private void reset() {
        //播放完毕 关闭视图
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (playTimer != null) {
            playTimer.cancel();
            playTimer = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private final int SUCCESS = 22;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDTAE_PROGRESS:
                    openViewTime++;
                    int current = (int) msg.obj;
                    //更新 进度
                    seekbar.setProgress(current);
                    //更新当前播放的时间
                    current_time_tv.setText(simpleDateFormat.format(new Date(current)));
                    have_listen_time_tv.setText(simpleDateFormat.format(new Date(openViewTime * 1000)));
                    break;
                case SUCCESS:
                    BlurEntity blurEntity = (BlurEntity) msg.obj;
//                    blurEntity.imageView.setImageBitmap(blurEntity.bitmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        blurEntity.imageView.setImageBitmap(ImageUtil.blurBitmap(StealListenDetailActivity.this
                                , blurEntity.bitmap, 25f));
                    }
//                    ImageUtil.blur(blurEntity.bitmap, blurEntity.imageView);
                    break;
            }
        }
    };

    /**
     * 初始化view
     */
    private void initView() {
        gold_coin_num_tv.setText(String.valueOf(memberAccount));
    }

    /**
     * 自定义seekBar 进度监听
     */
    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (mMediaPlayer != null) {
                mMediaPlayer.seekTo(i);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewclose = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
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
