package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class DetailReplayAdapter extends RecyclerView.Adapter<DetailReplayAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<DynamicDetail.DataBean.ReplyListBean> reply_list = new ArrayList<>();

    public DetailReplayAdapter(Context context, List<DynamicDetail.DataBean.ReplyListBean> reply_list) {
        this.context = context;
        this.reply_list.addAll(reply_list);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
    }

    public void setReply_list(List<DynamicDetail.DataBean.ReplyListBean> reply_list) {
        this.reply_list.clear();
        this.reply_list.addAll(reply_list);
    }

    public List<DynamicDetail.DataBean.ReplyListBean> getReply_list() {
        return reply_list;
    }

    /**
     * 添加最新的回复数据到
     *
     * @param replyListBean
     */
    public void addReply(DynamicDetail.DataBean.ReplyListBean replyListBean) {
        this.reply_list.add(replyListBean);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_dynamic_replay, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicDetail.DataBean.ReplyListBean listBean = reply_list.get(position);
        DynamicDetail.DataBean.ReplyListBean.ToMemberInfoBean toMemberInfoBean = listBean.getTo_member_info();
        DynamicDetail.DataBean.ReplyListBean.MemberInfoBean memberInfoBean = listBean.getMember_info();
        if (listBean.getTo_member_id() != 0) {//当被回复对象不为null 设置成 谁回复水电费：
            holder.replay_show_content_tv.setText(memberInfoBean.getNick_name() + "回复"+toMemberInfoBean.getNick_name()+"：" + listBean.getContent());
        } else {
            holder.replay_show_content_tv.setText(memberInfoBean.getNick_name() + "：" + listBean.getContent());
        }
        holder.replay_show_content_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickListener.onItemLongLick(view, position);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return reply_list.size();
    }

    public interface OnItemLongClickListener {
        void onItemLongLick(View view, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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
