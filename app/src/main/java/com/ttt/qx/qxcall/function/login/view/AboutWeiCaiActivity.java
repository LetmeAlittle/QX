package com.ttt.qx.qxcall.function.login.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * 关于微财
 * Created by wyd on 2017/8/12.
 */

public class AboutWeiCaiActivity extends BaseActivity implements ISettingView {

    public static final String MESSAGE_PROGRESS = "message_progress";
    private LocalBroadcastManager bManager;
    private ProgressBar progress;
    private TextView progress_text;
    private Dialog mProgressialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_weicai);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        registerReceiver();
    }

    /**
     * 初始化view
     */
    private void initView() {
//        //设置当前应用版本
//        version_tv.setText("V" + DeviceUtil.getVersionName(this));
//        //设置版权日期 为当年
//        String dealTime = DealTimeUtil.dealTime(System.currentTimeMillis());
//        copyright_end_time_tv.setText(dealTime.split("-")[0]);
    }
//
//    @OnClick({R.id.top_letf_back_iv, R.id.version_update_rl, R.id.about_us_rl, R.id.secret_provision_rl, R.id.danger_explain_rl})
//    public void click(View v) {
//        Intent intent = null;
//        switch (v.getId()) {
//            case R.id.top_letf_back_iv:
//                //判断是否是第一次点击返回
//                onFinish();
//                break;
//            case R.id.version_update_rl:
//                //微财App版本更新
//                if (NetUtil.isConnected(this)) {
//                    MineModel.getMineModel().getVersionInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<VersionUpdate>() {
//                        @Override
//                        public void onNext(VersionUpdate update) {
//                            if (update.getStatus().equals("200")) {
//                                if (update.isIsSuccess()) {
//                                    VersionUpdate.DataBean data = update.getData();
//                                    String serverVersion = data.getVersion();
//                                    //首先判断服务器版本与当前应用版本的大小
//                                    String appVersion = DeviceUtil.getVersionName(AboutWeiCaiActivity.this);
//                                    if (Double.parseDouble(serverVersion) > Double.parseDouble(appVersion)) {
//                                        //如果有最新版本首先给出提示对话框
//                                        showIsContinueDialog("检测到最新版本V" + data.getVersion() + "！", data);
//                                    } else {
//                                        //提示已是最新版本
//                                        onToast("已是最新版本！");
//                                    }
//                                } else {
//                                    errorMessageShow(update.getErrorMessage() == null ? "处理失败！" : update.getErrorMessage());
//                                }
//                            } else {
//                                errorMessageShow(update.getErrorMessage() == null ? "处理失败！" : update.getErrorMessage());
//                            }
//                        }
//                    }, this));
//                } else {
//                    onToast(getResources().getString(R.string.not_net_connect_text));
//                }
//                break;
//            case R.id.about_us_rl:
//                intent = new Intent(this, AboutActivity.class);
//                intent.putExtra("mark", "about_us");
//                intent.putExtra("aboutUrl", "http://m.fxbtg.com/pages/m-we.html");
//                break;
//            case R.id.secret_provision_rl:
//                intent = new Intent(this, AboutActivity.class);
//                intent.putExtra("mark", "secret_provision");
//                intent.putExtra("aboutUrl", "http://m.fxbtg.com/pages/m-privacy.html");
//                break;
//            case R.id.danger_explain_rl:
//                intent = new Intent(this, AboutActivity.class);
//                intent.putExtra("mark", "danger_explain");
//                intent.putExtra("aboutUrl", "http://m.fxbtg.com/pages/m-state.html");
//                break;
//        }
//        if (intent != null) {
//            startActivity(intent);
//        }
//    }

//    /**
//     * 给出提示对话框
//     */
//    private void showIsContinueDialogNotWifi(String tip, VersionUpdate.DataBean dataBean) {
//        Dialog dialog = new Dialog(AboutWeiCaiActivity.this, R.style.dialogStyle);
//        View view = View.inflate(AboutWeiCaiActivity.this, R.layout.tip_dialog, null);
//        TextView tip_content = (TextView) view.findViewById(R.id.tip_content);
//        tip_content.setText(tip);
//        TextView cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
//        TextView confirm_tv = (TextView) view.findViewById(R.id.confirm_tv);
//        cancel_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        confirm_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                //显示下载进度对话框
//                showDownloadDialog();
//                DownloadService.apkUrl = dataBean.getLinkAndriod();
//                Intent intent = new Intent(AboutWeiCaiActivity.this, DownloadService.class);
//                startService(intent);
//            }
//        });
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.addContentView(view, params);
//        dialog.show();
//    }
//
//    /**
//     * 给出提示对话框
//     */
//    private void showIsContinueDialog(String tip, VersionUpdate.DataBean dataBean) {
//        Dialog dialog = new Dialog(AboutWeiCaiActivity.this, R.style.dialogStyle);
//        View view = View.inflate(AboutWeiCaiActivity.this, R.layout.tip_dialog, null);
//        TextView tip_content = (TextView) view.findViewById(R.id.tip_content);
//        tip_content.setText(tip);
//        TextView cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
//        TextView confirm_tv = (TextView) view.findViewById(R.id.confirm_tv);
//        cancel_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        confirm_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                if (!NetUtil.isWifi(AboutWeiCaiActivity.this)) {
//                    showIsContinueDialogNotWifi("当前处于非WIFI网络，是否继续？", dataBean);
//                } else {
//                    //显示下载进度对话框
//                    showDownloadDialog();
//                    DownloadService.apkUrl = dataBean.getLinkAndriod();
//                    Intent intent = new Intent(AboutWeiCaiActivity.this, DownloadService.class);
//                    startService(intent);
//                }
//            }
//        });
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.addContentView(view, params);
//        dialog.show();
//    }
//
//    /**
//     * 显示下载进度对话框
//     */
//    private void showDownloadDialog() {
//        mProgressialog = new Dialog(AboutWeiCaiActivity.this, R.style.dialogStyle2);
//        View view = View.inflate(AboutWeiCaiActivity.this, R.layout.download_dialog, null);
//        progress = (ProgressBar) view.findViewById(R.id.progress);
//        progress_text = (TextView) view.findViewById(R.id.progress_text);
//        mProgressialog.setCancelable(false);
//        //将布局设置给Dialog
//        mProgressialog.setContentView(view);
//        //获取当前Activity所在的窗体
//        Window dialogWindow = mProgressialog.getWindow();
//        //设置Dialog从窗体底部弹出
//        dialogWindow.setGravity(Gravity.CENTER);
//        //获得窗体的属性
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////       将属性设置给窗体
//        dialogWindow.setAttributes(lp);
//        dialogWindow.getDecorView().setPadding(20, 0, 20, 0);
//        mProgressialog.show();//显示对话框
//    }
//
//
//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(MESSAGE_PROGRESS)) {
//                Download download = intent.getParcelableExtra("download");
//                progress.setProgress(download.getProgress());
//                if (download.getProgress() == 100) {
//                    mProgressialog.dismiss();
//                } else {
//                    progress_text.setText(
//                            StringUtils.getDataSize(download.getCurrentFileSize())
//                                    + "/" +
//                                    StringUtils.getDataSize(download.getTotalFileSize()));
//                }
//            }
//        }
//    };

    /**
     * 消息提醒
     *
     * @param message
     */
    private void errorMessageShow(Object message) {
        if (message != null) {
            onToast(message.toString());
        }
    }

    @Override
    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onFinish() {
        //销毁当前
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册时，使用注册时的manager解绑
//        bManager.unregisterReceiver(broadcastReceiver);
    }

    private void registerReceiver() {
//        bManager = LocalBroadcastManager.getInstance(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(MESSAGE_PROGRESS);
//        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }
}
