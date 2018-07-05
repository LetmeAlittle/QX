package com.ysxsoft.qxerkai.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zhaozhipeng on 17/6/6.
 * 首页viewpager的adapter
 */

public class MainPageAdapter extends PagerAdapter{

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;

    public MainPageAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments){
        this.fragmentManager=fragmentManager;
        this.fragments=fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //每次滑动的时候生成的组件  重点在这！！！
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);
        if (!fragment.isAdded()) {  //判断当前的Fragment是否存在
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView());  //把Fragment加到ViewPager里面
        }

        return fragment.getView();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
