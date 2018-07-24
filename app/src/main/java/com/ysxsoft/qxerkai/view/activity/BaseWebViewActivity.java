package com.ysxsoft.qxerkai.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.WebViewUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseWebViewActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.tv_public_titlebar_left)
    TextView tvPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_left_2)
    ImageView ivPublicTitlebarLeft2;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_right_1)
    ImageView ivPublicTitlebarRight1;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.iv_public_titlebar_right_2)
    ImageView ivPublicTitlebarRight2;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.ll_public_titlebar)
    LinearLayout llPublicTitlebar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    public static void startWithUrl(Context context, String url, String title) {
        Intent intent = new Intent(context, BaseWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void startWithContent(Context context, String content, String title) {
        Intent intent = new Intent(context, BaseWebViewActivity.class);
        intent.putExtra("content", content);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_view);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        WebViewUtils.init(webView);
        initTitleBar();
        initWebView();
    }

    private void initWebView() {
        String url = getIntent().getStringExtra("url");
        String content = getIntent().getStringExtra("content");
        if (url != null) {
            webView.loadUrl(url);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(content);
            webView.loadData(stringBuilder.toString(),"text/html;charset=UTF-8", null);
        }
    }

    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText(StringUtils.convert(getIntent().getStringExtra("title")));
    }

}
