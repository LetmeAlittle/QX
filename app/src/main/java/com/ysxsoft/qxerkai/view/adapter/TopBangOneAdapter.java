package com.ysxsoft.qxerkai.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.response.HaoYouListResponse;


/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class TopBangOneAdapter extends BaseQuickAdapter<HaoYouListResponse.DataBeanX.DataBean, BaseViewHolder> {
    public TopBangOneAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HaoYouListResponse.DataBeanX.DataBean item) {
        int position = helper.getLayoutPosition();

        helper.setText(R.id.tv_nickname,item.getNick_name());
        helper.setText(R.id.tv_money,item.getCnt());
        Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.iv_touxiang));
        helper.setText(R.id.tv_no, position+1+"");
    }
}
