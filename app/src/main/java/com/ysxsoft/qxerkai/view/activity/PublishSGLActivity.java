package com.ysxsoft.qxerkai.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netease.nim.uikit.common.util.string.StringUtil;
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

    @BindView(R.id.sex_select_iv)
    ImageView sexSelectIv;
    @BindView(R.id.top_back_rl)
    RelativeLayout topBackRl;
    @BindView(R.id.submit)
    RelativeLayout submit;
    @BindView(R.id.public_content_et)
    EditText publicContentEt;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_sgl);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.top_back_rl, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.submit://发布撒狗粮
                submit();
                break;
        }
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
