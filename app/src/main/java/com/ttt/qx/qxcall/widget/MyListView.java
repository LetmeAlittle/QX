package com.ttt.qx.qxcall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * todo 该Widget包是项目共享工具包
 * 解决与ScrollView嵌套时高度以及滑动异常问题
 * Created by wyd on 2016/9/30.
 */
public class MyListView extends ListView {
    private Context context;

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyListView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置为Integer.MAX_VALUE>>2 是listview全部展开
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        //设置为400是设置listview的高度只能有400 不全部展开  实现可以滑动的效果，根据需求自己设置
        int measureSpec1 = MeasureSpec.makeMeasureSpec(400, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, measureSpec1);
    }
}