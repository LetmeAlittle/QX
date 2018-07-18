package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;
import com.ysxsoft.qxerkai.view.activity.NKaiTongShouHuActivity;
import com.ysxsoft.qxerkai.view.activity.XuanZheShouHuActivity;

import java.util.List;


public class XuanZeShouHuAdapter extends BaseQuickAdapter<HaoYouListResponse.DataBeanX.DataBean, BaseViewHolder>{

    private int time;

    public XuanZeShouHuAdapter(int layoutResId, int time) {
        super(layoutResId);
        this.time = time;
    }


    @Override
    protected void convert(BaseViewHolder helper, HaoYouListResponse.DataBeanX.DataBean item) {
        TextView tvStatus = helper.getView(R.id.tv_status);
        TextView tvChoose = helper.getView(R.id.tv_choose);

        int isOnLine = item.getIs_online();//0:离线 1：在线
        if (isOnLine == 0) {
            tvStatus.setTextColor(Color.parseColor("#30FFFFFF"));
            tvStatus.setText("离线");
            tvChoose.setVisibility(View.INVISIBLE);
        } else {
            tvStatus.setTextColor(Color.parseColor("#fd3d5c"));
            tvStatus.setText("在线");
            tvChoose.setVisibility(View.VISIBLE);
            tvChoose.setOnClickListener(new ChooseClick("" + item.getId(), item.getNick_name(), item.getIcon()));
        }
        Glide.with(mContext).load(item.getIcon())
                .into((ImageView) helper.getView(R.id.iv_touxiang));

        helper.setText(R.id.tv_nickname, item.getNick_name());
    }

    private class ChooseClick implements View.OnClickListener {
        private String uid;
        private String nickName;
        private String avatar;

        public ChooseClick(String uid, String nickName, String avatar) {
            this.uid = uid;
            this.nickName = nickName;
            this.avatar = avatar;
        }

        @Override
        public void onClick(View view) {
            mContext.startActivity(new Intent(mContext, NKaiTongShouHuActivity.class)
                    .putExtra("time", time)
                    .putExtra("uid", uid)
                    .putExtra("nickname", nickName)
                    .putExtra("avatar", avatar));
        }
    }
}
