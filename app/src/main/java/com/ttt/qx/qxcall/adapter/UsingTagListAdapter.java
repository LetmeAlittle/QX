package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.entity.MemberTagBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 王亚东 on 2017/10/1.
 */

public class UsingTagListAdapter extends RecyclerView.Adapter<UsingTagListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private  List<MemberTagBean> tags = new ArrayList<>();

    public UsingTagListAdapter(Context context, List<MemberTagBean> tags) {
        this.context = context;
        this.tags.addAll(tags);
        inflater = LayoutInflater.from(context);
    }

    public void setTags( List<MemberTagBean> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_using_tag_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       MemberTagBean memberTagBean = tags.get(position);
        String text = memberTagBean.getText();
        holder.tag_tv.setText(text);
        //同时根据标签设置不同背景
        if (text.equals("知性御姐")) {
            holder.tag_tv.setBackgroundResource(R.drawable.fill_bg_6c3d96_yj);
        }
    }

    @Override
    public int getItemCount() {
        return tags.size() <= 3 ? tags.size() : 3;//只显示三条以内的标签
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_tv)
        TextView tag_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
