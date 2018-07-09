package com.ysxsoft.qxerkai.view.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.DimenUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;

import java.util.ArrayList;

public class QingQuTypePopupWindow {
    private PopupWindow popupWindow;
    private OnTypeSelectListener listener;
    private Context mContext;
    private ArrayList<Child> list = new ArrayList<>();

    public void init() {
//        类型 6个 1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        list.clear();
        list.add(new Child("1", "老司机开车"));
        list.add(new Child("2", "闺蜜私房话"));
        list.add(new Child("3", "两性研究所"));
        list.add(new Child("4", "剧本专区"));
    }

    public void init(Activity activity) {
        this.mContext = activity;
        popupWindow = new PopupWindow();
        popupWindow.setWidth((int) SystemUtils.getScreenWidth(activity));
        popupWindow.setContentView(initView());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.anim.pop_bottom_out);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.pop_xiao_qing_qu_select_type, null);
        return view;
    }

    public interface OnTypeSelectListener {
        void onSelected(int position);
    }

    class Child {
        public Child(String typeId, String name) {
            this.typeId = typeId;
            this.name = name;
        }

        private String typeId;
        private String name;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
