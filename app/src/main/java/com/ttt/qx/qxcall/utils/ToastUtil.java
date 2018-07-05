package com.ttt.qx.qxcall.utils;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.constant.CommonConstant;
import com.ysxsoft.qxerkai.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 吐司显示工具
 * Created by wyd on 2017/7/19.
 */
public class ToastUtil {
    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间 显示在屏幕底部 显示时间500ms
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            ToastUtils.showToast(context, message.toString(), 0);
//            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.BOTTOM, 0, 0);
//            toast.show();
//            controlToastTime(toast, CommonConstant.TOAST_SHOW_TIME);
        }
    }

    /**
     * 自定义显示Toast时间 显示在屏幕中间 显示时间500ms
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show4(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            controlToastTime(toast, CommonConstant.TOAST_SHOW_TIME);
        }
    }

    /**
     * 自定义显示Toast时间 显示在屏幕左侧中间 显示时间duration
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show3(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.LEFT | Gravity.CENTER, 0, 0);
            toast.show();
            controlToastTime(toast, duration);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show2(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
//            ToastUtils.showToast(context, message.toString(), 0);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     * @param xOffset  向左偏移
     * @param yOffset  向下偏移
     */
    public static void showInLeftCenter(Context context, CharSequence message, int duration, int xOffset, int yOffset) {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.LEFT | Gravity.CENTER, xOffset, yOffset);
            toast.show();
            controlToastTime(toast, duration);
        }
    }


    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        public void run() {
            mToast.cancel();
            //toast隐藏后，将其置为null
            mToast = null;
        }
    };

    public static void showToast(Context context, String message) {
        ToastUtils.showToast(context,message,0);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        //自定义布局
//        View view = inflater.inflate(R.layout.custom_toast, null);
//        TextView text = (TextView) view.findViewById(R.id.toast_message);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) DeviceUtil.getScreenWidth(context), ViewGroup.LayoutParams.WRAP_CONTENT);
//        text.setLayoutParams(params);
//        //显示的提示文字
//        text.setText(message);
//        mHandler.removeCallbacks(runnable);
//        //只有mToast==null时才重新创建，否则只需更改提示文字
//        if (mToast == null) {
//            mToast = new Toast(context);
//            mToast.setDuration(Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.TOP, 0, 0);
//        }
//        mToast.setView(view);
//        mToast.show();
    }


    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration 毫秒值
     */
    public static void showCenterControlTime(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            controlToastTime(toast, duration);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration 毫秒值
     * @param color    #2319DC 十六进制字符串
     */
    public static void showCenterControlTime(Context context, CharSequence message, int duration, String color) {
        if (isShow) {
            String ToastStr = "<font color='" + color + "'>" + message + "</font>";
            Toast toast = Toast.makeText(context, Html.fromHtml(ToastStr), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            controlToastTime(toast, duration);
        }
    }

    /**
     * 控制Toast显示时间
     *
     * @param toast
     * @param duration 显示时间
     */
    private static void controlToastTime(Toast toast, int duration) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }

    /**
     * 在屏幕中间显示吐司
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void showScreenCenter(Context context, String message, int duration) {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
