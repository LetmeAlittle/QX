package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帖子评论 Adapter
 * Created by 王亚东 on 2017/10/1.
 */

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;


    public PostCommentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_post_comment, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //某个用户对帖子的评论 name+回复：+content
        @BindView(R.id.commnet_tv)
        TextView commnet_tv;
        //帖主对某个用户评论的回复 name+replay_comment_tv
        @BindView(R.id.replay_ll)
        LinearLayout replay_ll;
        //当前帖主用户名 name
        @BindView(R.id.current_user_name_tv)
        TextView current_user_name_tv;
        //被回复用户 回复+name+：+content
        @BindView(R.id.replay_comment_tv)
        TextView replay_comment_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
