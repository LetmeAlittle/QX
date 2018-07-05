package com.ttt.qx.qxcall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class TabsAdapter2 extends FragmentPagerAdapter {
    private List<String> tabIndicators;
    private List<Fragment> categoryFragments;

    public TabsAdapter2(FragmentManager fragmentManager, List<String> tabIndicators, List<Fragment> categoryFragments) {
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
        return tabIndicators.get(position);
    }
}
