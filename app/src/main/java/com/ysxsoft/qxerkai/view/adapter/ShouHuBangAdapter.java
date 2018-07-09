package com.ysxsoft.qxerkai.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class ShouHuBangAdapter extends BaseQuickAdapter<GuardsListResponse.DataBean, BaseViewHolder> {
    public ShouHuBangAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuardsListResponse.DataBean item) {

    }
}
