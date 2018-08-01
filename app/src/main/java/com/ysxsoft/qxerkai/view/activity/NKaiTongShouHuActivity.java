package com.ysxsoft.qxerkai.view.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NKaiTongShouHuActivity extends NBaseActivity {

    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.civ_head1)
    CircleImageView civHead1;
    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.civ_head2)
    CircleImageView civHead2;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.tv_name11)
    TextView tvName11;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_name22)
    TextView tvName22;
    @BindView(R.id.tv_querenshouhu)
    TextView tvQuerenshouhu;
    @BindView(R.id.tv_shouhuchenggong)
    TextView tvShouhuchenggong;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;

    private String uid;
    private String nickname;
    private String avatar;
    private UserBean userBean;
    private int time = 0; //0：7天  1：15天   2：30天

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time = getIntent().getIntExtra("time",0);
        uid = getIntent().getStringExtra("uid");
        nickname = getIntent().getStringExtra("nickname");
        avatar = getIntent().getStringExtra("avatar");
        setContentView(R.layout.activity_nkai_tong_shou_hu);
        ButterKnife.bind(this);
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
        tvPublicTitlebarCenter.setText("守护");
    }

    private void initView() {
        switch (time){
            case 0:
                tvTime.setText("开通7天守护");
                tvMoney.setText("100");
                break;
            case 1:
                tvTime.setText("开通15天守护");
                tvMoney.setText("200");
                break;
            case 2:
                tvTime.setText("开通30天守护");
                tvMoney.setText("300");
                break;
        }

        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        Glide.with(this).load(userBean.getMember_avatar()).into(civHead1);
        tvName1.setText(userBean.getNick_name());
        tvName11.setText(userBean.getNick_name());
        Glide.with(this).load(avatar).into(civHead2);
        tvName2.setText(nickname);
        tvName22.setText(nickname);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });
        tvQuerenshouhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaitong();
            }
        });
    }

    /**
     * 确认守护
     */
    private void kaitong() {
        multipleStatusView.showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id",""+userBean.getUserId());
        hashMap.put("f_user_id",uid);
        hashMap.put("type",""+time);
        RetrofitTools.getGuardsing(hashMap)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        multipleStatusView.hideLoading();
                        if(code==200){
                            llMoney.setVisibility(View.INVISIBLE);
                            tvQuerenshouhu.setVisibility(View.GONE);
                            tvShouhuchenggong.setVisibility(View.VISIBLE);
                            tvTime.setClickable(false);
                        }else if(code==400){
                            ToastUtils.showToast(NKaiTongShouHuActivity.this,msg,1);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        multipleStatusView.hideLoading();
                        ToastUtils.showToast(NKaiTongShouHuActivity.this, e.getMessage(), 0);
                    }
                });
    }

    PopupWindow popupWindow;

    private void showTimeDialog() {
        //长按分组
        View groupPopView = View.inflate(this, R.layout.activity_nkai_tong_shou_hu_dialog, null);
        popupWindow = new PopupWindow(groupPopView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        // 这里单独写一篇文章来分析
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        popupWindow.showAsDropDown(tvTime);
    }

    public void onChose(View view) {
        popupWindow.dismiss();
        int tag = Integer.valueOf(view.getTag().toString());
        switch (tag) {
            case 0:
                tvTime.setText("开通7天守护");
                tvMoney.setText("100");
                break;
            case 1:
                tvTime.setText("开通15天守护");
                tvMoney.setText("200");
                break;
            case 2:
                tvTime.setText("开通30天守护");
                tvMoney.setText("300");
                break;
        }
        time = tag;
    }

}
