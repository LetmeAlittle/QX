package com.ttt.qx.qxcall.pager;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.adapter.InfiniteLoopViewPagerAdapter;
import com.ttt.qx.qxcall.adapter.LoopBasePagerAdapter;


/**
 * 无限循环ViewPager wyd
 */
public class InfiniteLoopViewPager extends LoopBaseViewPager {

    private QXCallApplication mQXCallApplication;
    private Handler handler;

    public InfiniteLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mQXCallApplication = (QXCallApplication) context.getApplicationContext();
    }

    public InfiniteLoopViewPager(Context context) {
        super(context);
        mQXCallApplication = (QXCallApplication) context.getApplicationContext();
    }

    @Override
    public void setAdapter(LoopBasePagerAdapter adapter) {
        super.setAdapter(adapter);
        //设置当前展示的位置
        setCurrentItem(0);
    }

    public void setInfinateAdapter(Handler handler, LoopBasePagerAdapter adapter) {
        this.handler = handler;
        setAdapter(adapter);
    }

    @Override
    public void setCurrentItem(int item) {
        item = getOffsetAmount() + (item % getAdapter().getCount());
        super.setCurrentItem(item);
    }

    /**
     * 从0开始向右(负向滑动)可以滑动的次数
     *
     * @return
     */
    private int getOffsetAmount() {
        if (getAdapter() instanceof InfiniteLoopViewPagerAdapter) {
            InfiniteLoopViewPagerAdapter infiniteAdapter = (InfiniteLoopViewPagerAdapter) getAdapter();
            // 允许向前滚动400000次
            return infiniteAdapter.getRealCount() * Integer.MAX_VALUE;
        } else {
            return 0;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mQXCallApplication.isRun = false;
            mQXCallApplication.isDown = true;
            handler.removeCallbacksAndMessages(null);
//			System.out.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_DOWN");
        } else if (action == MotionEvent.ACTION_UP) {
            mQXCallApplication.isRun = true;
            mQXCallApplication.isDown = false;
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessage(1);
//			System.out.println("InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_UP");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        super.setOffscreenPageLimit(limit);
    }
}
