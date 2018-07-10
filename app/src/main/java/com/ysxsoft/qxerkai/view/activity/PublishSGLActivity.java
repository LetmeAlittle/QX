package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.SaGouLiangPublishResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishSGLActivity extends NBaseActivity {


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
    @BindView(R.id.public_content_et)
    EditText publicContentEt;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_sgl);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
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
        tvPublicTitlebarCenter.setText("编辑故事");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        tvPublicTitlebarRight.setText("提交");
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }


    /**
     * 撒狗粮 发照片
     */
    private void submit() {
        String content = publicContentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(PublishSGLActivity.this, "请输入您的故事!");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("user_id", DBUtils.getUserId());
        params.put("type", "1");
        params.put("content", publicContentEt.getText().toString());

        RetrofitTools
                .publishSaGouLiang(params)
                .subscribe(new ResponseSubscriber<SaGouLiangPublishResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangPublishResponse saGouLiangPublishResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        if (code == 200) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            ToastUtil.showToast(PublishSGLActivity.this, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        multipleStatusView.showLoading();
                    }
                });
    }

}
