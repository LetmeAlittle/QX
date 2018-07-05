package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.ExitLogin;
import com.ttt.qx.qxcall.function.register.model.entity.ResponseStatus;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.DeviceUtil;
import com.ttt.qx.qxcall.utils.FileCacheUtils;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyd on 2017/7/28.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.cache_size_tip)
    TextView cache_size_tip;
    @BindView(R.id.version_code_tv)
    TextView version_code_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化view
     */
    private void initView() {
        //缓存大小处理
        try {
            cache_size_tip.setText(FileCacheUtils.getCacheSize(getCacheDir()).equals("0.0Byte") ? "0.0KB" : FileCacheUtils.getCacheSize(getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String versionName = DeviceUtil.getVersionName(this);
        version_code_tv.setText("V" + versionName);
    }

    //
    @OnClick({R.id.top_back_rl, R.id.exit_login_rl, R.id.mine_server_rl, R.id.mine_black_rl
            , R.id.advice_feed_back_rl, R.id.clear_cache_rl, R.id.about_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                onFinish();
                break;
            case R.id.exit_login_rl:
                onFinish();
                EventBus.getDefault().post(new ExitLogin());
                break;
            case R.id.advice_feed_back_rl:
                IntentUtil.jumpIntent(this, FeedBackActivity.class);
                break;
            case R.id.about_rl:
                IntentUtil.jumpIntent(this, AboutQxcallActivity.class);
                break;
            case R.id.clear_cache_rl:
                try {
                    if (FileCacheUtils.getCacheSize(getCacheDir()).equals("0.0Byte")) {
                        onToast("缓存已清理！");
                    } else {
                        FileCacheUtils.deleteFolderFile(getCacheDir().getPath(), true);
                        cache_size_tip.setText("0.0KB");
                        onToast("清除成功！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mine_server_rl:
                IntentUtil.jumpIntent(this, MyServerActivity.class);
                break;
            case R.id.mine_black_rl:
                IntentUtil.jumpIntent(this, MineBlacksActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }

    private void errorMessageShow(ResponseStatus responseStatus) {
        Object message = responseStatus.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

}
