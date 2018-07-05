package com.ttt.qx.qxcall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ttt.qx.qxcall.function.home.model.entity.CommonTagList;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    private List<CommonTagList.DataBean> tabIndicators;
    private List<Fragment> categoryFragments;

    public TabsAdapter(FragmentManager fragmentManager, List<CommonTagList.DataBean> tabIndicators, List<Fragment> categoryFragments) {
        super(fragmentManager);
        this.tabIndicators = tabIndicators;
        this.categoryFragments = categoryFragments;
    }

    @Override
    public Fragment getItem(int position) {

        return categoryFragments.get(position);
    }

    @Override
    public int getCount() {
        return categoryFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position).getName();
    }
}
