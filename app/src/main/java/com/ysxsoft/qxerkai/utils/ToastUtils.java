package com.ysxsoft.qxerkai.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;


/**
 * Created by zhaozhipeng on 17/6/27.
 */

public class ToastUtils {
    /**
     *
     * @param context
     * @param msg
     * @param time  0:short  1:long
     */
    public static void showToast(Context context,String msg ,int time){
        Toast toast = Toast.makeText(context, msg, time);
        LinearLayout layout = (LinearLayout)toast.getView();
        layout.removeAllViews();
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setBackground(context.getResources().getDrawable(R.drawable.toast_background));
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
//		加粗
//		tv.setTypeface(null,Typeface.BOLD);
        tv.setPadding(DimenUtils.dp2px(context, 15), DimenUtils.dp2px(context, 8),DimenUtils.dp2px(context, 15),DimenUtils.dp2px(context, 8));
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setText(msg);
        layout.addView(tv);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
//        Toast.makeText(context,msg,time).show();
    }
}
