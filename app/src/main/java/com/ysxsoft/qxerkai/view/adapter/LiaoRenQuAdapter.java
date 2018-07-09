package com.ysxsoft.qxerkai.view.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.net.response.GetCardListResponse;
import com.ysxsoft.qxerkai.utils.StringUtils;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class LiaoRenQuAdapter extends BaseQuickAdapter<GetCardListResponse.DataBeanX.ListBean.DataBean, BaseViewHolder> {
    public LiaoRenQuAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetCardListResponse.DataBeanX.ListBean.DataBean item) {
        helper.setText(R.id.cardTitle, StringUtils.convert(item.getTitle()));
        com.ysxsoft.qxerkai.view.widget.RoundAngleImageView image = helper.getView(R.id.cardImage);
        Glide.with(mContext).load(item.getImgss()).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);
        helper.setText(R.id.cardContent, StringUtils.convert(item.getContent()));
        helper.setText(R.id.lookNum, StringUtils.convert(item.getLooks() + ""));
        helper.setText(R.id.goodNum, StringUtils.convert(item.getLikes()));
        helper.setText(R.id.sayNum, StringUtils.convert(item.getCom_num() + ""));
    }
}
