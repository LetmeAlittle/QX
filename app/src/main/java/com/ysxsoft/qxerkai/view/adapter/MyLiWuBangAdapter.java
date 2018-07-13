package com.ysxsoft.qxerkai.view.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.response.MyLiWuResponse;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class MyLiWuBangAdapter extends BaseQuickAdapter<MyLiWuResponse.DataBeanX.DataBean, BaseViewHolder> {
    public MyLiWuBangAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyLiWuResponse.DataBeanX.DataBean item) {
        ImageView ivLiwu=helper.getView(R.id.iv_liwu);
        TextView tvNum=helper.getView(R.id.tv_num);
        Glide.with(mContext).load(item.getGift_img()).into(ivLiwu);
        tvNum.setText(""+item.getCnt());
    }
}
