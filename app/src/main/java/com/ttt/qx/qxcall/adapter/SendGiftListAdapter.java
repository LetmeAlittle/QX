package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 王亚东 on 2017/11/3.
 */

public class SendGiftListAdapter extends RecyclerView.Adapter<SendGiftListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int _89859e;
    private final int _ffffff;private String currentSelPrice;
    private
     String authorization;
    private List<GiftList.DataBean> data = new ArrayList<>();
    private Map<Integer, Boolean> giftSelectMap;

    public SendGiftListAdapter(Context context, List<GiftList.DataBean> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        _89859e = context.getResources().getColor(R.color._89859e);
        _ffffff = context.getResources().getColor(R.color._ffffff);
        giftSelectMap = new HashMap<>();
        for (GiftList.DataBean bean : data) {
            giftSelectMap.put(bean.getId(), false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_send_gift_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GiftList.DataBean dataBean = data.get(position);
        int gift_id = dataBean.getId();
        Glide.with(context)
                .load(dataBean.getGift_img())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(holder.gift_iv);
        holder.gift_name_tv.setText(dataBean.getGift_name());
        if (giftSelectMap.get(gift_id)) {
            holder.item_ll.setBackgroundResource(R.drawable.side_bg_9b5ada_yj);
        } else {
            holder.item_ll.setBackgroundResource(R.drawable.side_bg_9b5ada_yj2);
        }
        holder.item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!giftSelectMap.get(gift_id)) {
                    for (Integer integer : giftSelectMap.keySet()) {
                        if (integer == gift_id) {
                            giftSelectMap.put(integer, true);
                        } else {
                            giftSelectMap.put(integer, false);
                        }
                    }
                    mItemClickListener.onItemClick(view, position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gift_name_tv)
        TextView gift_name_tv;
        @BindView(R.id.gift_iv)
        ImageView gift_iv;
        @BindView(R.id.item_ll)
        LinearLayout item_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
