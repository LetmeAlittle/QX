package com.ysxsoft.qxerkai.view.fragment;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;


/**
 * Created by zhaozhipeng on 17/6/6.
 */

public class BaseFragment extends Fragment {

    public void initStatusBar(View mStatusBar){
        if(SystemUtils.checkDeviceHasNavigationBar(getActivity())){
            mStatusBar.setVisibility(View.GONE);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = SystemUtils.getStatusHeight(getActivity());
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
    }

    public void showToast(String msg){
        ToastUtils.showToast(getActivity(), msg, 0);
    }
}
