package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dialog.ImgZoomFragment;
import com.ttt.qx.qxcall.function.find.view.PostDeatailActivity;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.widget.ZQImageViewRoundOval;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class FindPicAdapter extends RecyclerView.Adapter<FindPicAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<String> imgs = new ArrayList<>();
    private String from = "";
    private int type = 1;

    public FindPicAdapter(Context context, List<String> imgs, String from, int type) {
        this.context = context;
        this.from = from;
        this.imgs.addAll(imgs);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
        this.type = type;
    }

    public void setImgs(List<String> imgs) {
        this.imgs.clear();
        this.imgs.addAll(imgs);
    }

    /**
     * 更新第几个位置的图片路径
     *
     * @param avatar
     * @param position
     */
    public void setImg(String avatar, int position) {
        List<String> tempImgs = new ArrayList<>();
        for (int i = 0; i < this.imgs.size(); i++) {
            if (i == position) {
                tempImgs.add(avatar);
            } else {
                tempImgs.add(this.imgs.get(i));
            }
        }
        this.imgs.clear();
        this.imgs.addAll(tempImgs);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_find_pic, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置图片类型为圆角矩形
        holder.user_head_icon_iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
        //设置圆角矩形圆弧半径
        holder.user_head_icon_iv.setRoundRadius(i16);
        //设置图片类型为圆角矩形
        holder.user_head_icon_iv2.setType(ZQImageViewRoundOval.TYPE_ROUND);
        //设置圆角矩形圆弧半径
        holder.user_head_icon_iv2.setRoundRadius(i16);
        //设置图片类型为圆角矩形
        holder.user_head_icon_iv3.setType(ZQImageViewRoundOval.TYPE_ROUND);
        //设置圆角矩形圆弧半径
        holder.user_head_icon_iv3.setRoundRadius(i16);
        //设置图片 如果网络图片路径不为空
        String avatar = imgs.get(position);
        if (null != avatar && !avatar.equals("")) {
            if (type == 1) {
                holder.rl_1.setVisibility(View.VISIBLE);
                holder.rl_2.setVisibility(View.GONE);
                holder.rl_3.setVisibility(View.GONE);
                Glide.clear(holder.user_head_icon_iv);
                Glide.with(context)
                        .load(avatar)
                        .into(holder.user_head_icon_iv);
                holder.user_head_icon_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImgZoomFragment imgZoomFragment = new ImgZoomFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("imgUrl", avatar);
                        imgZoomFragment.setArguments(bundle);
                        if (from.equals("MainActivity")) {
                            imgZoomFragment.show(((MainActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                        } else if (from.equals("PostDeatailActivity")) {
                            imgZoomFragment.show(((PostDeatailActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                        }
                    }
                });
            } else if (type == 2) {
                holder.rl_1.setVisibility(View.GONE);
                holder.rl_3.setVisibility(View.GONE);
                holder.rl_2.setVisibility(View.VISIBLE);
                Glide.clear(holder.user_head_icon_iv2);
                Glide.with(context)
                        .load(avatar)
                        .into(holder.user_head_icon_iv2);
                holder.user_head_icon_iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImgZoomFragment imgZoomFragment = new ImgZoomFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("imgUrl", avatar);
                        imgZoomFragment.setArguments(bundle);
                        if (from.equals("MainActivity")) {
                            imgZoomFragment.show(((MainActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                        } else if (from.equals("PostDeatailActivity")) {
                            imgZoomFragment.show(((PostDeatailActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                        }
                    }
                });
            } else {
                holder.rl_1.setVisibility(View.GONE);
                holder.rl_2.setVisibility(View.GONE);
                holder.rl_3.setVisibility(View.VISIBLE);
                Glide.clear(holder.user_head_icon_iv3);
                Glide.with(context)
                        .load(avatar)
                        .into(holder.user_head_icon_iv3);
                holder.user_head_icon_iv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImgZoomFragment imgZoomFragment = new ImgZoomFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("imgUrl", avatar);
                        imgZoomFragment.setArguments(bundle);
                        if (from.equals("MainActivity")) {
                            imgZoomFragment.show(((MainActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                        } else if (from.equals("PostDeatailActivity")) {
                            imgZoomFragment.show(((PostDeatailActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                        }
                    }
                });
            }
        } else {
            Glide.clear(holder.user_head_icon_iv);
            Glide.clear(holder.user_head_icon_iv2);
            Glide.clear(holder.user_head_icon_iv3);
            holder.user_head_icon_iv.setBackgroundResource(R.mipmap.shen_fen_pic_add_iv);
            holder.user_head_icon_iv2.setBackgroundResource(R.mipmap.shen_fen_pic_add_iv);
            holder.user_head_icon_iv3.setBackgroundResource(R.mipmap.shen_fen_pic_add_iv);
        }
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_1)
        RelativeLayout rl_1;
        @BindView(R.id.user_head_icon_iv)
        ZQImageViewRoundOval user_head_icon_iv;
        @BindView(R.id.rl_2)
        RelativeLayout rl_2;
        @BindView(R.id.user_head_icon_iv2)
        ZQImageViewRoundOval user_head_icon_iv2;
        @BindView(R.id.rl_3)
        RelativeLayout rl_3;
        @BindView(R.id.user_head_icon_iv3)
        ZQImageViewRoundOval user_head_icon_iv3;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
