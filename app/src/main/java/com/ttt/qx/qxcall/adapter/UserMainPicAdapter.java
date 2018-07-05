package com.ttt.qx.qxcall.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dialog.ImgZoomFragment;
import com.ttt.qx.qxcall.dialog.ShowSelectImgDialog;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.function.login.view.UserMainEditActivity;
import com.ttt.qx.qxcall.widget.ZQImageViewRoundOval;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class UserMainPicAdapter extends RecyclerView.Adapter<UserMainPicAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<String> imgs = new ArrayList<>();
    //图片是否可编辑标记
    private boolean eidt = false;

    public UserMainPicAdapter(Context context, List<String> imgs, Boolean eidt) {
        this.context = context;
        this.imgs.addAll(imgs);
        this.eidt = eidt;
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
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
        return new ViewHolder(inflater.inflate(R.layout.item_user_main_pic, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.divider_view.setVisibility(View.GONE);
        } else {
            holder.divider_view.setVisibility(View.VISIBLE);
        }
        //设置图片类型为圆角矩形
        holder.user_head_icon_iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
        //设置圆角矩形圆弧半径
        holder.user_head_icon_iv.setRoundRadius(i16);
        //设置图片 如果网络图片路径不为空
        String avatar = imgs.get(position);
        if (null != avatar && !avatar.equals("")) {
            Glide.clear(holder.user_head_icon_iv);
            Glide.with(context)
                    .load(avatar)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.user_head_icon_iv);
            if (eidt) {
                holder.del_iv.setVisibility(View.VISIBLE);
                holder.del_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDelDialog("您正在删除个人相册图片，确定要操作吗？", position);
                    }
                });

            }
        } else {
            Glide.clear(holder.user_head_icon_iv);
            if (eidt) {
                holder.user_head_icon_iv.setBackgroundResource(R.mipmap.shen_fen_pic_add_iv);
            }
            holder.del_iv.setVisibility(View.GONE);
        }
        holder.user_head_icon_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eidt) {//当标记为可编辑时 点击响应图片设置功能。
                    UserMainEditActivity userMainEditActivity = (UserMainEditActivity) UserMainPicAdapter.this.context;
                    userMainEditActivity.imgType = userMainEditActivity.USER_ALBUM;
                    userMainEditActivity.position = position;
                    userMainEditActivity.showSelectDialog();
                } else {
                    if (avatar!=null&&!avatar.equals("")) {
                        ImgZoomFragment imgZoomFragment = new ImgZoomFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("imgUrl", avatar);
                        imgZoomFragment.setArguments(bundle);
                        imgZoomFragment.show(((UserMainActivity) context).getSupportFragmentManager(), "imgZoomFragment");
                    }
                }
            }
        });
    }

    /**
     * 给出提示对话框
     */
    private void showDelDialog(String tip, int position) {
        Dialog dialog = new Dialog(context, R.style.dialogStyle88);
        View view = View.inflate(context, R.layout.tip_dialog, null);
        TextView tip_content = (TextView) view.findViewById(R.id.tip_content);
        tip_content.setText(tip);
        TextView cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
        TextView confirm_tv = (TextView) view.findViewById(R.id.confirm_tv);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                UserMainEditActivity userMainEditActivity = (UserMainEditActivity) UserMainPicAdapter.this.context;
                userMainEditActivity.delUserMainPic(position);
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(view, params);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_head_icon_iv)
        ZQImageViewRoundOval user_head_icon_iv;
        @BindView(R.id.divider_view)
        View divider_view;
        @BindView(R.id.del_iv)
        ImageView del_iv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
