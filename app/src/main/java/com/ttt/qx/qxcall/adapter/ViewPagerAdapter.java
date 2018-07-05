package com.ttt.qx.qxcall.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ttt.qx.qxcall.pager.BasePager;

import java.util.List;

/**
 * 主体框架adapter
 * Created by wyd on 2017/7/19.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<BasePager> pagerList = null;

    public ViewPagerAdapter(List<BasePager> pagerList) {
        this.pagerList = pagerList;
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View viewLayer = pagerList.get(position).getView();
        view.addView(viewLayer);
        return viewLayer;
    }
}
