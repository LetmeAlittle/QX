package com.ysxsoft.qxerkai.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.LoginModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.voice.DemoCache;
import com.ttt.qx.qxcall.pager.BasePager;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.view.activity.BanYanActivity;
import com.ysxsoft.qxerkai.view.activity.NShouHuBangActivity;
import com.ysxsoft.qxerkai.view.activity.NTopBangActivity;
import com.ysxsoft.qxerkai.view.activity.NZhuanShuPiPeiActivity;
import com.ysxsoft.qxerkai.view.activity.XuanZheShouHuActivity;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.ttt.qx.qxcall.QXCallApplication.getApplication;
import static com.ttt.qx.qxcall.QXCallApplication.login;
import static com.ttt.qx.qxcall.QXCallApplication.onToast;

/**
 * Created by zhaozhipeng on 18/5/3.
 */

public class ThreePage extends BasePager {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.price2)
    TextView price2;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_public_titlebar_right)
    TextView tvPublicTitlebarRight;
    @BindView(R.id.ll_public_titlebar_right)
    LinearLayout llPublicTitlebarRight;
    @BindView(R.id.circle_waiwei)
    ImageView circleWaiwei;
    @BindView(R.id.iv_zhuanshupipei)
    ImageView ivZhuanshupipei;
    @BindView(R.id.tv_public_titlebar_left)
    TextView tvPublicTitlebarLeft;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;

    private View rootView;
    UserDetailInfo.DataBean userDetailInfoData;

    public ThreePage(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        rootView = View.inflate(ctx, R.layout.fragment_three, null);
        ButterKnife.bind(this, rootView);
        initStatusBar(statusBar);
        initTitleBar();
        ivZhuanshupipei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NZhuanShuPiPeiActivity.class));
            }
        });
        getUserInfo("");
        getUserInfo();//获取个人信息
        return rootView;
    }

    private void initTitleBar() {
        tvPublicTitlebarCenter.setText("私人订制");
        tvPublicTitlebarRight.setText("TOP榜");
        tvPublicTitlebarRight.setVisibility(View.VISIBLE);
        llPublicTitlebarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, NTopBangActivity.class));
            }
        });
        tvPublicTitlebarLeft.setVisibility(View.VISIBLE);
        tvPublicTitlebarLeft.setText("守护墙");
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                UserDao userDao = new UserDao();
//                UserBean userBean = userDao.queryFirstData();
//                ctx.startActivity(new Intent(ctx, NShouHuBangActivity.class)
//                        .putExtra("uid", "" + userBean.getId())
//                        .putExtra("nickname", userBean.getNick_name())
//                        .putExtra("avatar", userBean.getMember_avatar())
//                        .putExtra("type", "1"));
                if (userDetailInfoData != null) {
                    ctx.startActivity(new Intent(ctx, NShouHuBangActivity.class)
                            .putExtra("uid", "" + userDetailInfoData.getId())
                            .putExtra("nickname", userDetailInfoData.getNick_name())
                            .putExtra("avatar", userDetailInfoData.getMember_avatar())
                            .putExtra("type", "1"));
                }
            }
        });
    }

    @Override
    public void initData() {
        Animation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setRepeatCount(-1);//动画的重复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        animation.setInterpolator(new LinearInterpolator());
        circleWaiwei.startAnimation(animation);//开始动画
    }

    @OnClick({R.id.tv_kaitong1, R.id.tv_kaitong2, R.id.tv_kaitong3})
    public void onKaitong(View view) {
        Intent intent = null;
        intent = new Intent(ctx, XuanZheShouHuActivity.class);
        switch (view.getId()) {
            case R.id.tv_kaitong1:
                intent.putExtra(XuanZheShouHuActivity.TYPE_JUMP, 0);
                break;
            case R.id.tv_kaitong2:
                intent.putExtra(XuanZheShouHuActivity.TYPE_JUMP, 1);
                break;
            case R.id.tv_kaitong3:
                intent.putExtra(XuanZheShouHuActivity.TYPE_JUMP, 2);
                break;
        }
        ctx.startActivity(intent);
    }

    @OnClick({R.id.riv_banyan1, R.id.riv_banyan2, R.id.riv_banyan3,
            R.id.riv_banyan4, R.id.riv_banyan5, R.id.riv_banyan6})
    public void onBanYan(View view) {
        Intent intent = null;
        intent = new Intent(ctx, BanYanActivity.class);
        switch (view.getId()) {
            case R.id.riv_banyan1://大叔---萝莉
                intent.putExtra(BanYanActivity.TYPE_BY, 0);
                break;
            case R.id.riv_banyan2://教师---学生
                intent.putExtra(BanYanActivity.TYPE_BY, 1);
                break;
            case R.id.riv_banyan3://空姐---乘客
                intent.putExtra(BanYanActivity.TYPE_BY, 2);
                break;
            case R.id.riv_banyan4://老板---秘书
                intent.putExtra(BanYanActivity.TYPE_BY, 3);
                break;
            case R.id.riv_banyan5://护士---病人
                intent.putExtra(BanYanActivity.TYPE_BY, 4);
                break;
            case R.id.riv_banyan6://亲王---宠妃
                intent.putExtra(BanYanActivity.TYPE_BY, 5);
                break;
        }
        ctx.startActivity(intent);
    }


    private void getUserInfo() {
        String authorization = "Bearer " + DBUtils.getUserToken();
        HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
//                    userDetailInfo.getData().getSckq();  角色扮演    zs 专属匹配

                    int sckq = userDetailInfo.getData().getSckq();
                    int zs = userDetailInfo.getData().getZs();
                    price.setText(sckq + "砰砰豆/分钟");
                    price2.setText(zs + "砰砰豆/次");
                }
            }
        }, DBUtils.getUserId(), authorization);
    }

    private void getUserInfo(String id) {
            HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
                @Override
                public void onCompleted() {
                    System.out.println("");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("e = " + e);
                    Toast.makeText(ctx, "获取用户信息失败3", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNext(UserDetailInfo userDetailInfo) {
                    if (userDetailInfo.getStatus_code() == 200) {
                        //获取用户详细信息成功之后 首先赋值给视图控件 然后将必要信息保存到数据中
                        userDetailInfoData = userDetailInfo.getData();
                    } else {
                        onToast(userDetailInfo.getMessage());
                    }

                }
            }, "", "Bearer " + DBUtils.getUserToken());
        }
}
