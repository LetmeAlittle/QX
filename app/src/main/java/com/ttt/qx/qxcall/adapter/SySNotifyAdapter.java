package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dbbean.NotifyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class SySNotifyAdapter extends RecyclerView.Adapter<SySNotifyAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    List<NotifyBean> notifyBeanList = new ArrayList<>();

    public SySNotifyAdapter(Context context, List<NotifyBean> notifyBeanList) {
        this.context = context;
        this.notifyBeanList.addAll(notifyBeanList);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
    }

    public void addList(List<NotifyBean> notifyBeanList) {
        this.notifyBeanList.addAll(notifyBeanList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_system_notify, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotifyBean notifyBean = notifyBeanList.get(position);
        holder.msg_content_tv.setText(notifyBean.getContent());
        holder.notify_time_tv.setText(notifyBean.getCreatTime());
        holder.notify_type_tv.setText(notifyBean.getTitle());
    }


    @Override
    public int getItemCount() {
        return notifyBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notify_time_tv)
        TextView notify_time_tv;
        @BindView(R.id.msg_content_tv)
        TextView msg_content_tv;
        @BindView(R.id.notify_type_tv)
        TextView notify_type_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
