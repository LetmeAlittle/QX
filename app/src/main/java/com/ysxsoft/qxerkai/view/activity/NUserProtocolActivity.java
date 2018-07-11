package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.ttt.qx.qxcall.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NUserProtocolActivity extends NBaseActivity {

    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.wv_webview)
    WebView wvWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web);
        ButterKnife.bind(this);
        initTitleBar();
        initView();
        initData();
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("用户协议");
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        WebSettings webSettings = wvWebview.getSettings();
        //支持缩放，默认为true。
        webSettings .setSupportZoom(false);
        //调整图片至适合webview的大小
        webSettings .setUseWideViewPort(true);
        //缩放至屏幕的大小
        webSettings .setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings .setDefaultTextEncodingName("utf-8");
        //设置自动加载图片
        webSettings .setLoadsImagesAutomatically(true);
        //开启javascript
        webSettings .setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings .setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //关闭webview中缓存
        webSettings .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private void initData() {
        wvWebview.loadUrl("file:///android_asset/userprotocol.html");
    }

    /**
     * 解决内存泄露问题
     */
    @Override
    protected void onDestroy() {
        if (wvWebview!= null) {
            wvWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wvWebview.clearHistory();
            wvWebview.destroy();
            wvWebview= null;
        }
        super.onDestroy();
    }

}
