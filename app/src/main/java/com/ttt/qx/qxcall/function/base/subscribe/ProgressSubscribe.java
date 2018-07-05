package com.ttt.qx.qxcall.function.base.subscribe;

import android.content.Context;
import android.widget.Toast;

import com.ttt.qx.qxcall.function.base.interfacee.ProgressCancelListener;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ysxsoft.qxerkai.utils.ToastUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * todo 当网络请求需要使用显示进度对话框的
 * todo 时候可以使用当前Subscribe，否则使用
 * todo 原有的Subscribe即可。
 * Subscribe自定义子类网络请求过程
 * 包含处理进度对话框显隐的Subscribe
 *  Created by wyd on 2017/7/19.
 */

public class ProgressSubscribe<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubScribeOnNextListener subScribeOnNextListener;
    private Context context;
    //使用handler来控制对话框的显隐
    private ProgressDialogHandler mHandler;

    public ProgressSubscribe(SubScribeOnNextListener<T> subScribeOnNextListener, Context context) {
        this.subScribeOnNextListener = subScribeOnNextListener;
        this.context = context;
        mHandler = new ProgressDialogHandler(context, true, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    /**
     * 显示对话框
     */
    private void showProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        //异常错误处理，可以自定义处理异常类
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showToast(context,"网络中断，请检查您的网络状态",0);
        } else if (e instanceof ConnectException) {
            ToastUtils.showToast(context,"网络中断，请检查您的网络状态",0);
        } else {
//            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //同时有进度对话框的需要关闭对话框
        dismissProgressDialog();
    }

    /**
     * 关闭对话框
     */
    private void dismissProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
    }

    @Override
    public void onNext(T t) {
        if (subScribeOnNextListener != null) {
            try {
                subScribeOnNextListener.onNext(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCancelProgress() {
        //对话框关闭之后取消请求
        if (!this.isUnsubscribed()) {//如果未取消请求
            this.unsubscribe();
        }
    }
}
