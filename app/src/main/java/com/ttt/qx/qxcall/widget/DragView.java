package com.ttt.qx.qxcall.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ttt.qx.qxcall.R;

/**
 * 可拖拽控件
 * Created by wyd on 2017/8/4.
 */

public class DragView extends RelativeLayout {
    private WindowManager windowManager;// 用于可拖动的浮动窗口
    private WindowManager.LayoutParams windowParams;// 浮动窗口的参数
    private ImageView fm_iv;
    //增加一个标记，只有当用户按下一定时间之后才响应移动
    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        int i180 = getContext().getResources().getInteger(R.integer._i180);
        LayoutParams params = new LayoutParams(i180, i180);
        fm_iv = new ImageView(getContext());
        fm_iv.setBackgroundResource(R.mipmap.fm);
        fm_iv.setLayoutParams(params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取当前点的xy位置
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (windowManager == null) {
                    setWindowParams(currentX, currentY);
                    windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                    windowManager.addView(fm_iv, windowParams);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                windowParams.x = currentX;
                windowParams.y = currentY;
                windowManager.updateViewLayout(fm_iv, windowParams);
                break;
            case MotionEvent.ACTION_UP:
                // windowManager.removeView(myButton);
                break;
        }
        return false;
    }

    private void setWindowParams(int x, int y) {
        // 建立item的缩略图
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP | Gravity.LEFT;// 这个必须加
        // 得到preview左上角相对于屏幕的坐标
        windowParams.x = x;
        windowParams.y = y;
        // 设置宽和高
        windowParams.width = 200;
        windowParams.height = 200;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
    }
}
