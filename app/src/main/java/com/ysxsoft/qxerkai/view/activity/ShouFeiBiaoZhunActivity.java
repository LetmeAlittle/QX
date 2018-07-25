package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.view.SetCallPriceActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GetGuShiResponse;
import com.ysxsoft.qxerkai.net.response.ShouFeiResponse;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouFeiBiaoZhunActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_biaozhun)
    TextView tvBiaozhun;
    private String Authorization;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_fei_biao_zhun);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
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
        tvPublicTitlebarCenter.setText("收费标准");
    }

    private void initView() {
        tvBiaozhun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_id", "" + userBean.getUserId());
                RetrofitTools.getShouFeiList(hashMap).subscribe(new ResponseSubscriber<ShouFeiResponse>() {
                    @Override
                    public void onSuccess(ShouFeiResponse shouFeiResponse, int code, String msg) {
                        if ("0".equals(shouFeiResponse.getData().getFlag())) {
                            ToastUtils.showToast(ShouFeiBiaoZhunActivity.this, "请开通VIP后进行设置", 0);
                            return;
                        }
                        showDialog();
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        ToastUtils.showToast(ShouFeiBiaoZhunActivity.this, e.getMessage(), 0);
                    }
                });
//                IntentUtil.jumpIntent(ShouFeiBiaoZhunActivity.this, SetCallPriceActivity.class);
            }
        });
    }

    private void showDialog() {
        new MaterialDialog.Builder(ShouFeiBiaoZhunActivity.this)
                .title("收费标准")
                .inputType(InputType.TYPE_CLASS_PHONE)//可以输入的类型-电话号码
                //前2个一个是hint一个是预输入的文字
                .input("请输入收费价格", "", new MaterialDialog.InputCallback() {

                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String tempNick = dialog.getInputEditText().getText().toString();
                        if (tempNick.isEmpty()) {
                            ToastUtils.showToast(ShouFeiBiaoZhunActivity.this, "输入不能为空", 0);
                            return;
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("user_id", "" + userBean.getUserId());
                        hashMap.put("dd", tempNick);
                        LogUtils.e(hashMap.toString());
                        RetrofitTools.updateDouzi(hashMap).subscribe(new ResponseSubscriber<GetGuShiResponse>() {
                            @Override
                            public void onSuccess(GetGuShiResponse getGuShiResponse, int code, String msg) {
                                ToastUtils.showToast(ShouFeiBiaoZhunActivity.this, getGuShiResponse.getData(), 0);
                                initData();
                            }

                            @Override
                            public void onFailed(Throwable e) {
                                ToastUtils.showToast(ShouFeiBiaoZhunActivity.this, e.getMessage(), 0);
                            }
                        });
//                                MineModel.getMineModel().setMemberPrice(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
//                                    @Override
//                                    public void onNext(StandardResponse response) {
//                                        if (response.getStatus_code() == 200) {
//                                            //成功刷新列表
//                                            initData();
//                                        }
//                                    }
//                                }, ShouFeiBiaoZhunActivity.this), tempNick, Authorization);
                    }
                })
                .show();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //首先获取 应用配置的通话价格数组
        String[] prices = getResources().getStringArray(R.array.audio_call_price);
        //查询当前用户所设置的价格
        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        Authorization = "Bearer " + userBean.getToken();
        HomeModel.getHomeModel().getUserInfo(new ProgressSubscribe<>(new SubScribeOnNextListener<UserDetailInfo>() {
            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    UserDetailInfo.DataBean userDetailInfoData = userDetailInfo.getData();
                    String currentSelPrice = String.valueOf(userDetailInfoData.getMember_price());
                    tvBiaozhun.setText(currentSelPrice + "砰砰豆/分钟");
                }
            }
        }, this), "" + userBean.getUserId(), Authorization);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

}
