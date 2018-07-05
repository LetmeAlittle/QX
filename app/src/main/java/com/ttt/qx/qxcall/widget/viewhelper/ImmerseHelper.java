package com.ttt.qx.qxcall.widget.viewhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 应用Activity视图沉浸辅助工具
 * Created by wyd on 2017/7/19.
 */

public class ImmerseHelper {
    /**
     * 沉浸设置
     *
     * @param activity
     */
    public static void immerse(Activity activity) {
        /**沉浸式状态栏设置部分**/
        //Android4.4及以上版本才能设置此效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            //Android5.0版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏颜色
                window.setStatusBarColor(Color.TRANSPARENT);
                //设置导航栏颜色
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {//Android4.4以上5.0以下
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                //创建系统栏的管理实例
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                //激活状态栏设置
                tintManager.setStatusBarTintEnabled(true);
                //设置状态栏颜色
                tintManager.setTintResource(Color.TRANSPARENT);
                //激活导航栏设置
//                tintManager.setNavigationBarTintEnabled(true);
                //设置导航栏颜色
//                tintManager.setNavigationBarTintResource(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 有无虚拟键盘的设置
     *
     * @param activity
     */
    public static void immerse2(Activity activity) {
        Window window = activity.getWindow();
        WindowManager windowManager = activity.getWindowManager();
        if (hasSoftKeys(windowManager)) {
            //有虚拟键的取消状态栏渲染防止底部导航栏被虚拟键遮挡
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else {//没有虚拟键盘的正常处理
            //Android4.4及以上版本才能设置此效果
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //Android5.0版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    //设置状态栏颜色
                    window.setStatusBarColor(Color.TRANSPARENT);
                    //设置导航栏颜色
//                window.setNavigationBarColor(Color.TRANSPARENT);
                } else {//Android4.4以上5.0以下
                    //透明状态栏
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //透明导航栏
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    //创建系统栏的管理实例
                    SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                    //激活状态栏设置
                    tintManager.setStatusBarTintEnabled(true);
                    //设置状态栏颜色
                    tintManager.setTintResource(Color.TRANSPARENT);
                    //激活导航栏设置
//                tintManager.setNavigationBarTintEnabled(true);
                    //设置导航栏颜色
//                tintManager.setNavigationBarTintResource(Color.TRANSPARENT);
                }
            }
        }
    }

    /**
     * @param context
     * @return
     */
    public int getNavigationBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }

    /**
     * 判断底部navigator是否已经显示
     *
     * @return
     * @paramwindowManager
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}
