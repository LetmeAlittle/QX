package com.ysxsoft.qxerkai.view.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.GiftRankList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class LiWuBangAdapter extends BaseQuickAdapter<GiftRankList.DataBeanX.DataBean, BaseViewHolder> {
    public LiWuBangAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftRankList.DataBeanX.DataBean item) {
        CircleImageView civHead1=helper.getView(R.id.civ_head1);
        CircleImageView civHead2=helper.getView(R.id.civ_head2);
        TextView tvName1=helper.getView(R.id.tv_name1);
        TextView tvName2=helper.getView(R.id.tv_name2);
        ImageView ivLiwu=helper.getView(R.id.iv_liwu);
        TextView tvMoney=helper.getView(R.id.tv_money);
        TextView tvTime=helper.getView(R.id.tv_time);
        Glide.with(mContext).load(item.getMember_info().getAvatar()).into(civHead1);
        Glide.with(mContext).load(item.getTo_member_info().getAvatar()).into(civHead2);
        tvName1.setText(item.getMember_info().getNick_name());
        tvName2.setText(item.getTo_member_info().getNick_name());
        Glide.with(mContext).load(item.getGift_info().getGift_img()).into(ivLiwu);
        tvMoney.setText(item.getGift_info().getGift_name());
        tvTime.setText(item.getCreate_at());
    }
}
