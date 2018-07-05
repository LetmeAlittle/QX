package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.login.model.entity.InvitedRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class InvitedRecordAdapter extends RecyclerView.Adapter<InvitedRecordAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<InvitedRecord.DataBeanX.DataBean> beanList;

    public InvitedRecordAdapter(Context context, List<InvitedRecord.DataBeanX.DataBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_invited_record, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InvitedRecord.DataBeanX.DataBean dataBean = beanList.get(position);
        String nick_name = dataBean.getNick_name();
        holder.invited_name_tv.setText((null == nick_name || nick_name.equals("") || nick_name.equals(" ")) ? "未知" : nick_name);
        holder.invited_time_tv.setText(dataBean.getCreate_time());
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.invited_name_tv)
        TextView invited_name_tv;
        @BindView(R.id.invited_time_tv)
        TextView invited_time_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
