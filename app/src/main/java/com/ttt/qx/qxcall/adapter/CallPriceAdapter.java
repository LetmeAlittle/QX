package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 王亚东 on 2017/10/1.
 */

public class CallPriceAdapter extends RecyclerView.Adapter<CallPriceAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final String[] mPrices;
    private final int _89859e;
    private final int _ffffff;
    private String currentSelPrice;
    private String authorization;

    public CallPriceAdapter(Context context, String[] prices, String currentSelPrice, String authorization) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mPrices = prices;
        this.currentSelPrice = currentSelPrice;
        this.authorization = authorization;
        _89859e = context.getResources().getColor(R.color._89859e);
        _ffffff = context.getResources().getColor(R.color._ffffff);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_call_price, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String price = mPrices[position];
        holder.price_content_tv.setText(price + "钻石/分钟");
        if (price.equals(currentSelPrice)) {
            holder.current_select_price_iv.setVisibility(View.VISIBLE);
            holder.price_content_tv.setTextColor(_ffffff);
        } else {
            holder.current_select_price_iv.setVisibility(View.GONE);
            holder.price_content_tv.setTextColor(_89859e);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MineModel.getMineModel().setMemberPrice(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                    @Override
                    public void onNext(StandardResponse response) {
                        if (response.getStatus_code() == 200) {
                            //成功刷新列表
                            currentSelPrice = price;
                            notifyDataSetChanged();
                        }
                    }
                }, context), price, authorization);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPrices.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.price_content_tv)
        TextView price_content_tv;
        @BindView(R.id.current_select_price_iv)
        ImageView current_select_price_iv;
        @BindView(R.id.rl)
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
