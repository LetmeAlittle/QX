package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.entity.FlowVisitorInfoList;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.widget.ZQImageViewRoundOval;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class FollowVisitorInfoAdapter extends RecyclerView.Adapter<FollowVisitorInfoAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    List<FlowVisitorInfoList.DataBeanX.DataBean> dataBeanList = new ArrayList<>();

    public FollowVisitorInfoAdapter(Context context, List<FlowVisitorInfoList.DataBeanX.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList.addAll(dataBeanList);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
    }

    public void addList(List<FlowVisitorInfoList.DataBeanX.DataBean> dataBeanList) {
        this.dataBeanList.addAll(dataBeanList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_follow_visitor_info, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlowVisitorInfoList.DataBeanX.DataBean bean = dataBeanList.get(position);
        if (bean != null) {
            //设置图片类型为圆角矩形
            holder.user_head_icon_iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
            //设置圆角矩形圆弧半径
            holder.user_head_icon_iv.setRoundRadius(i16);
            FlowVisitorInfoList.DataBeanX.DataBean.MemberInfoBean member_info = bean.getMember_info();
            if (null != member_info) {
                Glide.clear(holder.user_head_icon_iv);
                Glide.with(context)
                        .load(member_info.getAvatar())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.user_head_icon_iv);
            } else {
                Glide.clear(holder.user_head_icon_iv);
                holder.user_head_icon_iv.setBackgroundResource(R.mipmap.nim_avatar_default);
            }
            holder.user_nick_name_tv.setText(member_info.getNick_name());
            holder.signature_tv.setText(member_info.getMember_signature());
            holder.create_time_tv.setText(bean.getCreate_at());
            if (member_info.getSex().equals("1")) {
                holder.sex_ll.setBackgroundResource(R.drawable.fill_bg_5d9cfb_yj);
                holder.sex_iv.setBackgroundResource(R.mipmap.boy_iv);
            } else {
                holder.sex_ll.setBackgroundResource(R.drawable.fill_bg_ff79a7_yj);
                holder.sex_iv.setBackgroundResource(R.mipmap.girl_iv);
            }
            String member_age = member_info.getAge();
            if (null==member_age||member_age.equals("") ||member_age.equals(" ") || member_age.equals("0")) {
                holder.age_tv.setText("未知");
            } else {
                holder.age_tv.setText(member_age);
            }
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, UserMainActivity.class);
                    Intent intent = new Intent(context, NZhiLiaoActivity.class);
                    intent.putExtra("id", bean.getMember_id());//用户id
                    intent.putExtra("accid", ""+bean.getMember_id());
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_head_icon_iv)
        ZQImageViewRoundOval user_head_icon_iv;
        @BindView(R.id.create_time_tv)
        TextView create_time_tv;
        @BindView(R.id.user_nick_name_tv)
        TextView user_nick_name_tv;
        @BindView(R.id.sex_ll)
        LinearLayout sex_ll;
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.sex_iv)
        ImageView sex_iv;
        @BindView(R.id.age_tv)
        TextView age_tv;
        @BindView(R.id.signature_tv)
        TextView signature_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
