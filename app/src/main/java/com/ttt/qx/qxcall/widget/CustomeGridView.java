package com.ttt.qx.qxcall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 自定义GridView 禁止上下滑动
 * Created by wyd on 2017/7/22.
 */

public class CustomeGridView extends GridView {
    public CustomeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomeGridView(Context context) {
        super(context);
    }

    /**
     * 设置上下不滚动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//true:禁止滚动
        }

        return super.dispatchTouchEvent(ev);
    }
}
