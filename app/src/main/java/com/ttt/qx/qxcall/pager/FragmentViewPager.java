package com.ttt.qx.qxcall.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 各功能模块pager管理
 * 容器类
 * Created by wyd on 2017/7/19.
 */
public class FragmentViewPager extends LazyViewPager {
    public FragmentViewPager(Context context) {
        super(context);
    }

    public FragmentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return false 不捕捉viewpager事件，传递给子view
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false 不响应触摸事件，传递给子 view
        return false;
    }
}
