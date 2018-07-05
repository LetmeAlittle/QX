package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kankan.wheel.widget.adapters.WheelViewAdapter;
import com.ttt.qx.qxcall.R;

import static com.ttt.qx.qxcall.QXCallApplication.mAddressEntity;

/**
 * Created by 王亚东 on 2017/11/22.
 */

public class ProvinceWheelAdapter implements WheelViewAdapter {
    private Context context;

    public ProvinceWheelAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemsCount() {
        return mAddressEntity.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        View inflate = View.inflate(context, R.layout.wheel_item_view, null);
        TextView content = (TextView) inflate.findViewById(R.id.content);
        content.setText(mAddressEntity.get(index).getName());
        return inflate;
    }

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

}
