package com.ysxsoft.qxerkai.view.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.model.entity.InvitedRecord;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class YaoQingHaoYouAdapter extends BaseQuickAdapter<InvitedRecord.DataBeanX.DataBean, BaseViewHolder> {
    public YaoQingHaoYouAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitedRecord.DataBeanX.DataBean item) {
        TextView tvName=helper.getView(R.id.tv_name);
        tvName.setText(item.getNick_name());
    }
}
