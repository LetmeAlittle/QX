package com.ysxsoft.qxerkai.view.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.model.entity.PaymentDetail;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class ShouZhiMingXiAdapter extends BaseQuickAdapter<PaymentDetail.DataBeanX.DataBean, BaseViewHolder> {
    public ShouZhiMingXiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentDetail.DataBeanX.DataBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tvMoney = helper.getView(R.id.tv_money);
        tvTitle.setText(item.getLog_desc());
        tvTime.setText(item.getCreate_at());
        tvMoney.setText("");
    }
}
