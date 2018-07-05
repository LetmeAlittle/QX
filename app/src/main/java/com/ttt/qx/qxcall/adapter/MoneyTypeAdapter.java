package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.NotifyMoneyTypeModify;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class MoneyTypeAdapter extends RecyclerView.Adapter<MoneyTypeAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private String[] moneyTyps;
    private Map<String, Boolean> moneyTypsMap = new HashMap<>();
    int _ffffff;
    int _454545;

    public MoneyTypeAdapter(Context context, String[] moneyTyps) {
        this.context = context;
        this.moneyTyps = moneyTyps;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < moneyTyps.length; i++) {
            if (moneyTyps[i].equals("100元")) {
                moneyTypsMap.put(moneyTyps[i], true);
            } else {
                moneyTypsMap.put(moneyTyps[i], false);
            }
        }
        _ffffff = context.getResources().getColor(R.color._ffffff);
        _454545 = context.getResources().getColor(R.color._454545);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_money_type, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String moneyTyp = moneyTyps[position];
        holder.money_type_tv.setText(moneyTyp);
        if (moneyTypsMap.get(moneyTyp)) {
            holder.money_type_tv.setTextColor(_ffffff);
            holder.money_type_tv.setBackgroundResource(R.mipmap.money_type_black_iv);
        } else {
            holder.money_type_tv.setTextColor(_454545);
            holder.money_type_tv.setBackgroundResource(R.mipmap.money_type_gray_iv);
        }
        holder.money_type_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!moneyTypsMap.get(moneyTyp)) {//如果当前点击的条目 没有选择 取消另外一个选择 设置当前为选择
                    for (int i = 0; i < moneyTyps.length; i++) {
                        String key = moneyTyps[i];
                        if (moneyTypsMap.get(key)) {
                            moneyTypsMap.put(key, false);
                            break;
                        }
                    }
                    moneyTypsMap.put(moneyTyp, true);
                    notifyDataSetChanged();
                    //同时 通知金额类别更改
                    NotifyMoneyTypeModify notifyMoneyTypeModify = new NotifyMoneyTypeModify();
                    notifyMoneyTypeModify.money = moneyTyp;
                    EventBus.getDefault().post(notifyMoneyTypeModify);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moneyTyps.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.money_type_tv)
        TextView money_type_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
