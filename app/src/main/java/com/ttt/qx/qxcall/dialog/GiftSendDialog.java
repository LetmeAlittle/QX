package com.ttt.qx.qxcall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.SendGiftListAdapter;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 王亚东 on 2017/10/28.
 */

public class GiftSendDialog {


    private static GiftList.DataBean dataBean;

    public interface OnComponentClickListener {
        void onCancle();

        void onSend(String gift_id);
    }

    /**
     * 从底部弹出  可选的 按钮的对话框
     *
     * @param activity
     */
    public static void showBottomDialog(Activity activity, List<GiftList.DataBean> data, OnComponentClickListener onComponentClickListener) {
        Dialog dialog = new Dialog(activity, R.style.dialogStyle2);
        View view = View.inflate(activity, R.layout.gift_list_dialog, null);
        TextView send_tv = (TextView) view.findViewById(R.id.send_tv);
        TextView gift_price_tv = (TextView) view.findViewById(R.id.gift_price_tv);
        send_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBean == null) {
                    ToastUtil.show(activity, "请选择礼物！", Toast.LENGTH_SHORT);
                } else {
                    dialog.dismiss();
                    onComponentClickListener.onSend(String.valueOf(dataBean.getId()));
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gift_list_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        SendGiftListAdapter sendGiftListAdapter = new SendGiftListAdapter(activity, data);
        recyclerView.setAdapter(sendGiftListAdapter);
        dataBean = null;
        sendGiftListAdapter.setItemClickListener(new SendGiftListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                dataBean = data.get(position);
                gift_price_tv.setText(String.valueOf(dataBean.getGift_price()));
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
