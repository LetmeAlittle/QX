package com.ttt.qx.qxcall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;

/**
 * Created by 王亚东 on 2017/10/28.
 */

public class TipDialog {

    public interface OnComponentClickListener {
        void onCancle();

        void onConfirm();
    }

    public interface OnBottomDialogClickListener {
        void onTopClick();

        void onBottomClick();

        void onCancle();

        void onConfirm();
    }

    /**
     * 在 中部显示对话框 普通文本提示对话框
     *
     * @param context
     * @param tip
     * @param OnComponentClickListener
     */
    public static void showCenterTipDialog(Context context, String tip, OnComponentClickListener OnComponentClickListener, boolean enableCancel) {
        Dialog dialog = new Dialog(context, R.style.dialogStyle88);
        dialog.setCancelable(enableCancel);
        View view = View.inflate(context, R.layout.tip_dialog, null);
        TextView tip_content = (TextView) view.findViewById(R.id.tip_content);
        tip_content.setText(tip);
        TextView cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
        TextView confirm_tv = (TextView) view.findViewById(R.id.confirm_tv);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                OnComponentClickListener.onCancle();
            }
        });
        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                OnComponentClickListener.onConfirm();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(view, params);
        dialog.show();
    }

    /**
     * 从底部弹出  可选的 按钮的对话框
     *
     * @param activity
     * @param title                       对话框title 如果为空""则不显示 title
     * @param topText
     * @param bottomText
     * @param onBottomDialogClickListener
     */
    public static void showBottomDialog(Activity activity, String title, String topText, String bottomText, OnBottomDialogClickListener onBottomDialogClickListener) {
        Dialog dialog = new Dialog(activity, R.style.dialogStyle2);
        View view = View.inflate(activity, R.layout.header_set_select_pattern, null);
        LinearLayout title_ll = (LinearLayout) view.findViewById(R.id.title_ll);
        TextView title_tv = (TextView) view.findViewById(R.id.title_tv);
        if (!title.equals("")) {
            title_ll.setVisibility(View.VISIBLE);
            title_tv.setText(title);
        } else {
            title_ll.setVisibility(View.GONE);
        }
        TextView botttom_select_tv = (TextView) view.findViewById(R.id.botttom_select_tv);
        botttom_select_tv.setText(bottomText);
        TextView top_select_tv = (TextView) view.findViewById(R.id.top_select_tv);
        top_select_tv.setText(topText);
        LinearLayout cancel_ll = (LinearLayout) view.findViewById(R.id.cancel_ll);
        botttom_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomDialogClickListener.onBottomClick();
                dialog.dismiss();
            }
        });
        top_select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomDialogClickListener.onTopClick();
                dialog.dismiss();

            }
        });
        cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomDialogClickListener.onCancle();
                dialog.dismiss();
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
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.getWindow().getDecorView().setPadding(10, 0, 10, 0);
        dialog.show();//显示对话框
    }
}
