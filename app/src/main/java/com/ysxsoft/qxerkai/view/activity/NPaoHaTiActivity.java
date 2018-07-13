package com.ysxsoft.qxerkai.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetHuaTiListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;
import com.ysxsoft.qxerkai.view.widget.PublishHuaTiDialog;
import com.ysxsoft.qxerkai.view.widget.XCDanmuView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NPaoHaTiActivity extends NBaseActivity {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.iv_public_titlebar_right_2)
    ImageView ivPublicTitlebarRight2;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.iv_xin)
    ImageView ivXin;
    @BindView(R.id.xcDanmuView)
    XCDanmuView xcDanmuView;

    private String[] mStrItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_npao_ha_ti);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
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
        ivPublicTitlebarRight2.setVisibility(View.VISIBLE);
        ivPublicTitlebarRight2.setImageResource(R.mipmap.activity_paohuati_fabu);
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();//发布话题
            }
        });
        tvPublicTitlebarCenter.setText("抛话题");
    }

    private void initView() {
        ScaleAnimation animation = new ScaleAnimation(
                1f, 1.2f, 1f, 1.2f
                , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(800);
        animation.setRepeatCount(-1);//动画的重复次数
        animation.setInterpolator(new AnticipateInterpolator());
        ivXin.startAnimation(animation);//开始动画
    }

    private void initDanMu(List<GetHuaTiListResponse.DataBeanX.DataBean> list) {
//        mStrItems=new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            mStrItems[i]=list.get(i).get
//        }
//        mStrItems = new String[20];
//        for (int i = 0; i < 20; i++) {
//             = "话题名称话题名称话题" + i;
//        }
        xcDanmuView.initDanmuItemViews(list);
        xcDanmuView.start();
    }

    private void initData() {
        getList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xcDanmuView.stop();
    }

    /**
     * 获取漂浮的话题
     */
    private void getList() {
        Map<String, String> map = new HashMap<>();
//        map.put("page", "1");
        RetrofitTools.getHuaTiList(map)
                .subscribe(new ResponseSubscriber<GetHuaTiListResponse>() {
                    @Override
                    public void onSuccess(GetHuaTiListResponse ruleResponse, int code, String msg) {
                        if (code == 200) {
                            if (ruleResponse.getData() == null) {
                                return;
                            }
                            List<GetHuaTiListResponse.DataBeanX.DataBean> list = ruleResponse.getData().getData();
                            if (list == null) {
                                return;
                            }
                            size=list.size();
                            initDanMu(list);
                        } else {
                            ToastUtil.showToast(NPaoHaTiActivity.this, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                    }
                });
    }

    private int size;


    private void showDialog() {
        PublishHuaTiDialog dialog = new PublishHuaTiDialog(this, R.style.dialogHuaTiStyle);
        dialog.show(new PublishHuaTiDialog.OnHuaTiDialogListener() {
            @Override
            public void publish(String title,String selectedNum) {
                GetHuaTiListResponse.DataBeanX.DataBean data = new GetHuaTiListResponse.DataBeanX.DataBean();
                UserDao userDao = new UserDao();
                UserBean userBean = userDao.queryFirstData();
                data.setIcon(userBean.getMember_avatar());
                data.setGid(userBean.getId());
                data.setNick_name(userBean.getNick_name());
                data.setTitle(title);
                data.setIs_vip(userBean.getLevel().equals("0")?0:1);//是否是vip
                data.setNum(Integer.parseInt(selectedNum));//砰砰豆

                xcDanmuView.createDanmuView(0, data);
                xcDanmuView.requestLayout();
            }
        });
    }
}
