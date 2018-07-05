package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class VIPListAdapter extends RecyclerView.Adapter<VIPListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;

    public VIPListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_vip_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.vip_title.setText("特权一:上线送钻石");
            holder.vip_content.setText("每日上线均可获得2钻石");
        } else {
            holder.vip_title.setText("特权二:专属会员标识");
            holder.vip_content.setText("获得VIP专属标识");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vip_title)
        TextView vip_title;
        @BindView(R.id.vip_content)
        TextView vip_content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
