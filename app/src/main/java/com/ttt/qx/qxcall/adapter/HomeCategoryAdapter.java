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
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.model.entity.UserListInfo;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.widget.ZQImageViewRoundOval;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ttt.qx.qxcall.QXCallApplication.login;


/**
 * Created by 王亚东 on 2017/10/1.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<UserListInfo.DataBean.ListBean> listBeanList = new ArrayList<>();

    public HomeCategoryAdapter(Context context, List<UserListInfo.DataBean.ListBean> listBeanList) {
        this.context = context;
        this.listBeanList.addAll(listBeanList);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_home_category, null, false));
    }

    public void setListBeanList(List<UserListInfo.DataBean.ListBean> listBeanList) {
        this.listBeanList.clear();
        this.listBeanList.addAll(listBeanList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserListInfo.DataBean.ListBean userBean = listBeanList.get(position);
        Glide.clear(holder.user_head_icon_iv);
        Glide.with(context).load(userBean.getMember_avatar())
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.user_head_icon_iv);
        //设置图片类型为圆角矩形
        holder.user_head_icon_iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
        //设置圆角矩形圆弧半径
        holder.user_head_icon_iv.setRoundRadius(i16);
        holder.user_head_icon_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login) {
                    //将当前用户id传递过去  启动UserMainActivity
                    Intent intent = new Intent(context, UserMainActivity.class);
                    intent.putExtra("id", userBean.getId());//用户id
                    intent.putExtra("accid", userBean.getWy_acid());//网易accid
                    context.startActivity(intent);
                } else {
                    IntentUtil.jumpIntent(context, LoginTransferActivity.class);
                }
            }
        });
        if (userBean.getMember_price() == 0) {
            holder.need_gold_tv.setVisibility(View.INVISIBLE);
        } else {
            holder.need_gold_tv.setVisibility(View.VISIBLE);
            holder.need_gold_tv.setText(String.valueOf(userBean.getMember_price()) + "钻石/分钟");
        }
        holder.user_nick_name_tv.setText(userBean.getNick_name());
        if (userBean.getLevel() == 0) {
//            holder.member_level_tv.setVisibility(View.INVISIBLE);
            holder.vip_flag_iv.setVisibility(View.GONE);
        } else {
//            holder.member_level_tv.setText("V" + String.valueOf(userBean.getLevel()));
            holder.vip_flag_iv.setVisibility(View.VISIBLE);
        }
        if (userBean.getMember_sex().equals("1")) {
            holder.sex_ll.setBackgroundResource(R.drawable.fill_bg_5d9cfb_yj);
            holder.sex_iv.setBackgroundResource(R.mipmap.boy_iv);
        } else {
            holder.sex_ll.setBackgroundResource(R.drawable.fill_bg_ff79a7_yj);
            holder.sex_iv.setBackgroundResource(R.mipmap.girl_iv);
        }
        String member_age = userBean.getMember_age();
        if (null==member_age||member_age.equals("") || userBean.getMember_age().equals("0")) {
            holder.age_tv.setText("未知");
        } else {
            holder.age_tv.setText(member_age);
        }
    }

    @Override
    public int getItemCount() {
        return listBeanList.size();
    }

    /**
     * 刷新添加
     *
     * @param listBeanList
     */
    public void addList(List<UserListInfo.DataBean.ListBean> listBeanList) {
        this.listBeanList.addAll(listBeanList);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_head_icon_iv)
        ZQImageViewRoundOval user_head_icon_iv;
        @BindView(R.id.need_gold_tv)
        TextView need_gold_tv;
        @BindView(R.id.user_nick_name_tv)
        TextView user_nick_name_tv;
        @BindView(R.id.member_level_tv)
        TextView member_level_tv;
        @BindView(R.id.vip_flag_iv)
        ImageView vip_flag_iv;
        @BindView(R.id.sex_iv)
        ImageView sex_iv;
        @BindView(R.id.sex_ll)
        LinearLayout sex_ll;
        @BindView(R.id.age_tv)
        TextView age_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
