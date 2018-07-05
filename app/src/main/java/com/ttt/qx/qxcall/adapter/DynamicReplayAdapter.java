package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class DynamicReplayAdapter extends RecyclerView.Adapter<DynamicReplayAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<DynamicResponse.DataBean.ListBean.ReplyListBean> reply_list = new ArrayList<>();

    public DynamicReplayAdapter(Context context, List<DynamicResponse.DataBean.ListBean.ReplyListBean> reply_list) {
        this.context = context;
        this.reply_list.addAll(reply_list);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
    }

    public void setReply_list(List<DynamicResponse.DataBean.ListBean.ReplyListBean> reply_list) {
        this.reply_list.clear();
        this.reply_list.addAll(reply_list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_dynamic_replay, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicResponse.DataBean.ListBean.ReplyListBean listBean = reply_list.get(position);
        DynamicResponse.DataBean.ListBean.ReplyListBean.ToMemberInfoBean toMemberInfoBean = listBean.getTo_member_info();
        DynamicResponse.DataBean.ListBean.ReplyListBean.MemberInfoBean memberInfoBean = listBean.getMember_info();
        if (listBean.getTo_member_id() != 0) {//当被回复对象不为null 设置成 谁回复水电费：
            holder.replay_show_content_tv.setText(memberInfoBean.getNick_name() + "回复" + toMemberInfoBean.getNick_name() + "：" + listBean.getContent());
        } else {
            holder.replay_show_content_tv.setText(memberInfoBean.getNick_name() + "：" + listBean.getContent());
        }
        holder.replay_show_content_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });
    }


    @Override
    public int getItemCount() {
        return reply_list.size() <= 3 ? reply_list.size() : 3;
    }

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.replay_show_content_tv)
        TextView replay_show_content_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
