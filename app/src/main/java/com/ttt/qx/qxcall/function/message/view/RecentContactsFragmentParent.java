package com.ttt.qx.qxcall.function.message.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.recent.RecentContactsFragment;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.NotifyRecentContactRefresh;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by wyd on 2017/11/28.
 */

public class RecentContactsFragmentParent extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_recent_contacts_parent, null, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        addFragment();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventNotifyRecentContactRefresh(NotifyRecentContactRefresh notifyRecentContactRefresh) {
        addFragment();
    }

    private void addFragment() {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.recent_frame, new RecentContactsFragment())
                .commitAllowingStateLoss();
    }
}
