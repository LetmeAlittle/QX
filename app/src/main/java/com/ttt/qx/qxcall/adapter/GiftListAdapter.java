package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.GiftRankList;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class GiftListAdapter extends RecyclerView.Adapter<GiftListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<GiftRankList.DataBeanX.DataBean> list = new ArrayList<>();
    private final int _2A273C;
    private final RelativeLayout.LayoutParams layoutParams;

    public GiftListAdapter(Context context, List<GiftRankList.DataBeanX.DataBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list.addAll(list);
        i16 = context.getResources().getInteger(R.integer.i16);
        _2A273C = context.getResources().getColor(R.color._2A273C);
        int i200 = context.getResources().getInteger(R.integer.i200);
        layoutParams = new RelativeLayout.LayoutParams(i200, i200);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_gift_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GiftRankList.DataBeanX.DataBean bean = list.get(position);
        holder.send_user_icon_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserMainActivity.class);
                intent.putExtra("id", bean.getMember_id());//用户id
                intent.putExtra("accid", bean.getMember_id());
                context.startActivity(intent);
            }
        });
        holder.send_user_icon_iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserMainActivity.class);
                intent.putExtra("id", bean.getMember_id());//用户id
                intent.putExtra("accid", bean.getMember_id());
                context.startActivity(intent);
            }
        });
        holder.receive_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserMainActivity.class);
                intent.putExtra("id", bean.getTo_member_id());//用户id
                intent.putExtra("accid", bean.getTo_member_id());
                context.startActivity(intent);
            }
        });
        holder.receive_user_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserMainActivity.class);
                intent.putExtra("id", bean.getTo_member_id());//用户id
                intent.putExtra("accid", bean.getTo_member_id());
                context.startActivity(intent);
            }
        });
        if (position == 0) {
            holder.top_view.setVisibility(View.VISIBLE);
            holder.white_view.setVisibility(View.GONE);
            holder.item_ll.setBackgroundColor(_2A273C);
            holder.send_name_tv.setVisibility(View.INVISIBLE);
            holder.receive_name_tv.setVisibility(View.INVISIBLE);
            holder.send_user_icon_iv.setVisibility(View.INVISIBLE);
            holder.send_user_icon_iv2.setVisibility(View.VISIBLE);
            holder.receive_user_icon.setVisibility(View.INVISIBLE);
            holder.receive_user_icon2.setVisibility(View.VISIBLE);
//            holder.send_user_icon_iv.setLayoutParams(layoutParams);
//            holder.receive_user_icon.setLayoutParams(layoutParams);
        } else {
            holder.top_view.setVisibility(View.GONE);
            holder.white_view.setVisibility(View.VISIBLE);
            holder.send_name_tv.setVisibility(View.VISIBLE);
            holder.receive_name_tv.setVisibility(View.VISIBLE);
            holder.send_user_icon_iv2.setVisibility(View.GONE);
            holder.send_user_icon_iv.setVisibility(View.VISIBLE);
            holder.receive_user_icon2.setVisibility(View.GONE);
            holder.receive_user_icon.setVisibility(View.VISIBLE);
            if (position == 1) {
                holder.item_ll.setBackgroundResource(R.drawable.fill_bg_473053_yj);
            } else {
                holder.item_ll.setBackgroundResource(R.drawable.fill_bg_473053_zj);
            }
        }
        holder.present_time_tv.setText(bean.getCreate_at());
        GiftRankList.DataBeanX.DataBean.MemberInfoBean member_info = bean.getMember_info();
        if (member_info != null) {
            Glide.with(context).load(member_info.getAvatar()).into(holder.send_user_icon_iv);
            Glide.with(context).load(member_info.getAvatar()).into(holder.send_user_icon_iv2);
            holder.send_name_tv.setText(member_info.getNick_name());
        }
        GiftRankList.DataBeanX.DataBean.ToMemberInfoBean to_member_info = bean.getTo_member_info();
        if (to_member_info != null) {
            Glide.with(context).load(to_member_info.getAvatar()).into(holder.receive_user_icon);
            Glide.with(context).load(to_member_info.getAvatar()).into(holder.receive_user_icon2);
            holder.receive_name_tv.setText(to_member_info.getNick_name());
        }
        GiftRankList.DataBeanX.DataBean.GiftInfoBean gift_info = bean.getGift_info();
        if (gift_info != null) {
            Glide.with(context).load(gift_info.getGift_img()).into(holder.gift_iv);
            holder.gift_price_tv.setText(gift_info.getGift_price() + "钻石");
        } else {
            Glide.with(context).load("").into(holder.gift_iv);
            holder.gift_price_tv.setText("0钻石");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addGiftList(List<GiftRankList.DataBeanX.DataBean> list) {
        this.list.addAll(list);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_ll)
        LinearLayout item_ll;
        @BindView(R.id.top_view)
        View top_view;
        @BindView(R.id.white_view)
        View white_view;
        @BindView(R.id.present_time_tv)
        TextView present_time_tv;
        @BindView(R.id.send_user_icon_iv)
        CircleImageView send_user_icon_iv;
        @BindView(R.id.send_user_icon_iv2)
        CircleImageView send_user_icon_iv2;
        @BindView(R.id.receive_user_icon)
        CircleImageView receive_user_icon;
        @BindView(R.id.receive_user_icon2)
        CircleImageView receive_user_icon2;
        @BindView(R.id.gift_iv)
        ImageView gift_iv;
        @BindView(R.id.gift_price_tv)
        TextView gift_price_tv;
        @BindView(R.id.send_name_tv)
        TextView send_name_tv;
        @BindView(R.id.receive_name_tv)
        TextView receive_name_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
