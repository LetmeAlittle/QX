package com.ttt.qx.qxcall.function.listen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.eventbus.ListenFragmentTransfer;
import com.ttt.qx.qxcall.eventbus.NotifyListenDataRefresh;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/29.
 */

public class ListenParentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_listen_parent, null, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadFragment();
        return view;
    }

    private void loadFragment() {
        ListenTagFragment listenTagFragment = new ListenTagFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", "0");
        listenTagFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.list_frame_layout, listenTagFragment, "listenTagFragment")
                .commitAllowingStateLoss();
    }

    /**
     * 切换分类布局
     *
     * @param listenFragmentTransfer
     */
    @Subscribe
    public void onEventTransferListenFrame(ListenFragmentTransfer listenFragmentTransfer) {
//        ListenTagFragment listenTagFragment = new ListenTagFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", listenFragmentTransfer.id);
//        listenTagFragment.setArguments(bundle);
//        getChildFragmentManager().beginTransaction()
//                .replace(R.id.list_frame_layout, listenTagFragment, "listenTagFragment")
//                .commitAllowingStateLoss();
    }
    @Subscribe
    public void onEventNotifyListenDataRefresh(NotifyListenDataRefresh notifyListenDataRefresh) {
        loadFragment();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
