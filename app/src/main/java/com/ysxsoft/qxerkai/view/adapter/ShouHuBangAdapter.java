package com.ysxsoft.qxerkai.view.adapter;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ysxsoft.qxerkai.net.response.GuardsListResponse;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhaozhipeng on 18/5/24.
 */

public class ShouHuBangAdapter extends BaseQuickAdapter<GuardsListResponse.DataBean, BaseViewHolder> {
    //type:1 用户的守护列表    type:2守护用户的列表
    private String type;
    private String NickName;
    private String Avatar;
    public ShouHuBangAdapter(String type,String nickName,String Avatar, int layoutResId) {
        super(layoutResId);
        this.type = type;
        this.NickName=nickName;
        this.Avatar=Avatar;
    }

    @Override
    protected void convert(BaseViewHolder helper, GuardsListResponse.DataBean item) {
        CircleImageView civHead1=helper.getView(R.id.civ_head1);
        CircleImageView civHead2=helper.getView(R.id.civ_head2);
        TextView tvName1=helper.getView(R.id.tv_name1);
        TextView tvName2=helper.getView(R.id.tv_name2);
        TextView tvTime=helper.getView(R.id.tv_time);
        tvTime.setText("开通"+item.getDates()+"天守护");
        if("1".equals(type)){
            Glide.with(mContext).load(item.getIcon()).into(civHead1);
            tvName1.setText(item.getUsername());
            Glide.with(mContext).load(Avatar).into(civHead2);
            tvName2.setText(NickName);
        }else {
//            Glide.with(mContext).load(item.getIcon()).into(civHead1);
//            tvName1.setText(item.getUsername());
//            Glide.with(mContext).load(Avatar).into(civHead2);
//            tvName2.setText(NickName);
        }

    }
}
