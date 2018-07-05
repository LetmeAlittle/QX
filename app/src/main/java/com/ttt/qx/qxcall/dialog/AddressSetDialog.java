package com.ttt.qx.qxcall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kankan.wheel.widget.OnWheelChangedListener;
import com.kankan.wheel.widget.WheelView;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.ProvinceWheelAdapter;

import static com.ttt.qx.qxcall.QXCallApplication.mAddressEntity;

/**
 * Created by 王亚东 on 2017/10/28.
 */

public class AddressSetDialog {

    public interface OnComponentClickListener {
        void onCancle();

        void onConfirm(String province, String city);
    }

    private static String province = "";
    private static String city = "";

    /**
     * 从底部弹出  可选的 按钮的对话框
     *
     * @param activity
     */
    public static void showAddressDialog(Activity activity, OnComponentClickListener onComponentClickListener) {
        Dialog dialog = new Dialog(activity, R.style.dialogStyle2);
        View view = View.inflate(activity, R.layout.address_set_dialog, null);
        TextView close_tv = (TextView) view.findViewById(R.id.close_tv);
        TextView confirm_tv = (TextView) view.findViewById(R.id.confirm_tv);
        WheelView province_wheel_view = (WheelView) view.findViewById(R.id.province_wheel_view);
//        WheelView city_wheel_view = (WheelView) view.findViewById(R.id.city_wheel_view);

        province_wheel_view.setVisibleItems(5);
//        city_wheel_view.setVisibleItems(5);

        province_wheel_view.setViewAdapter(new ProvinceWheelAdapter(activity));

        province_wheel_view.setCurrentItem(4);
        province = mAddressEntity.get(4).getName();


        province_wheel_view.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(com.kankan.wheel.widget.WheelView wheel, int oldValue, int newValue) {

            }
        });
        close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onComponentClickListener.onCancle();
            }
        });
        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onComponentClickListener.onConfirm(province, city);
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        dialog.show();//显示对话框
    }
}
