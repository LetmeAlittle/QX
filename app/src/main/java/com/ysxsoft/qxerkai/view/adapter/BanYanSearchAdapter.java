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
import com.ysxsoft.qxerkai.net.response.SearchListResponse;
import com.ysxsoft.qxerkai.view.activity.NKaiTongShouHuActivity;


public class BanYanSearchAdapter extends BaseQuickAdapter<SearchListResponse.DataBean.ListBean, BaseViewHolder>{

    private OnChooseClick onChooseClick;

    public void setOnChooseClick(OnChooseClick onChooseClick){
        this.onChooseClick = onChooseClick;
    }

    public BanYanSearchAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, SearchListResponse.DataBean.ListBean item) {
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
            tvChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChooseClick.onClick(item);
                }
            });
        }
        Glide.with(mContext).load(item.getMember_avatar())
                .into((ImageView) helper.getView(R.id.iv_touxiang));

        helper.setText(R.id.tv_nickname, item.getNick_name());
    }

    public interface OnChooseClick{
        void onClick(SearchListResponse.DataBean.ListBean item);
    }
}
