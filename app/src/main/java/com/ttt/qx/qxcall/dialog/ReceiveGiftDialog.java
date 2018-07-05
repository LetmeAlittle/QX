package com.ttt.qx.qxcall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 王亚东 on 2017/10/28.
 */

public class ReceiveGiftDialog {


    private static GiftList.DataBean dataBean;

    public interface OnComponentClickListener {
        void onCancle();

        void onSend(String gift_id);
    }

    private static Timer timer;

    /**
     * 收到礼物动画
     *
     * @param context
     */
    public static void showReceiveGiftDialog(Context context, Map<String, String> receiveMap, OnComponentClickListener onComponentClickListener) {
        Dialog dialog = new Dialog(context, R.style.receiveGiftDialogStyle);
        View view = View.inflate(context, R.layout.receive_gift_dialog, null);
        CircleImageView user_head_icon_iv = (CircleImageView) view.findViewById(R.id.user_head_icon_iv);
        TextView user_nick_name_tv = (TextView) view.findViewById(R.id.user_nick_name_tv);
        ImageView gift_iv = (ImageView) view.findViewById(R.id.gift_iv);
        TextView gift_num = (TextView) view.findViewById(R.id.gift_num);
        //设置控件
        Glide.with(context).load(receiveMap.get("avator"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_head_icon_iv);
        Glide.with(context).load(receiveMap.get("gift_avator"))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(gift_iv);
        user_nick_name_tv.setText(receiveMap.get("nick_name"));
        gift_num.setText(receiveMap.get("gift_num"));
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        dialog.show();//显示对话框
        if (timer == null) {
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //注意可能存在 是非UI线程
                    dialog.dismiss();
                    timer.cancel();
                    timer = null;
                }
            }, 4400);
        }
    }

}
