package com.ttt.qx.qxcall.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 对话框工具类
 * Created by wyd on 2017/7/22.
 */

public class DialogUtil {
    /**
     * 设置对话框的大小以及对话框的位置
     *
     * @param dialog
     * @param width  宽度
     * @param height 高度
     * @param x      横坐标起始
     * @param y      纵坐标起始
     */
    public static void setDialogSize(Dialog dialog, int width, int height, int x, int y) {
        /*随意定义个Dialog*/
        Window dialogWindow = dialog.getWindow();
        /*实例化Window*/
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /*实例化Window操作者*/
        lp.x = x; // 新位置X坐标
        lp.y = y; // 新位置Y坐标
        lp.width = width;//对话框宽度
        lp.height = height;//对话框高度
        dialogWindow.setAttributes(lp);
    }

    /**
     * 设置对话框的大小
     *
     * @param dialog
     * @param width  宽度
     * @param height 高度
     */
    public static void setDialogSize(Dialog dialog, int width, int height) {
        /*随意定义个Dialog*/
        Window dialogWindow = dialog.getWindow();
        /*实例化Window*/
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /*实例化Window操作者*/
        lp.width = width;//对话框宽度
        lp.height = height;//对话框高度
        dialogWindow.setAttributes(lp);
    }

    /**
     * 设置对话框的宽高所占屏幕的比例大小
     *
     * @param context
     * @param dialog
     * @param widthScale
     * @param heightScale
     */
    public static void setDialogParams(Context context, Dialog dialog, float widthScale, float heightScale) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * heightScale); // 高度设置为屏幕的0.5
        p.width = (int) (d.getWidth() * widthScale); // 宽度设置为屏幕的0.85
        dialogWindow.setAttributes(p);
    }

    /**
     * 设置对话框的宽高所占屏幕的比例大小
     *
     * @param context
     * @param dialog
     * @param widthScale
     * @param heightScale
     * @param gravity     显示位置
     */
    public static void setDialogParams(Context context, Dialog dialog, float widthScale, float heightScale, int gravity) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.gravity = gravity;
        p.height = (int) (d.getHeight() * heightScale); // 高度设置为屏幕的倍数
        p.width = (int) (d.getWidth() * widthScale); // 宽度设置为屏幕的倍数
        dialogWindow.setAttributes(p);
    }
    /**
     * 设置对话框
     *
     * @param context
     * @param dialog
     * @param gravity     显示位置
     */
    public static void setDialogParams(Context context, Dialog dialog, int gravity) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(gravity);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.gravity = gravity;
        p.y = -10;
        dialogWindow.setAttributes(p);
    }
}
