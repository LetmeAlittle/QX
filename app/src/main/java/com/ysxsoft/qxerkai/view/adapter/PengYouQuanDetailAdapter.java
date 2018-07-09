package com.ysxsoft.qxerkai.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;

import java.util.List;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class PengYouQuanDetailAdapter extends BaseQuickAdapter<DynamicDetail.DataBean.ReplyListBean, BaseViewHolder> {

    public PengYouQuanDetailAdapter(int layoutResId, List<DynamicDetail.DataBean.ReplyListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicDetail.DataBean.ReplyListBean item) {

        helper.setText(R.id.tv_nickname,item.getMember_info().getNick_name());
        helper.setText(R.id.tv_time,item.getCreate_at());
        helper.setText(R.id.tv_content,item.getContent());

        Glide.with(mContext)
                .load(item.getMember_info().getAvatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) helper.getView(R.id.iv_tou));

    }
}
