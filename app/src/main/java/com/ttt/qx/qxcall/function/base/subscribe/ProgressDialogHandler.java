package com.ttt.qx.qxcall.function.base.subscribe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.ttt.qx.qxcall.function.base.interfacee.ProgressCancelListener;
import com.ttt.qx.qxcall.utils.CustomAlertDialogUtil;

/**
 * 控制网络请求对话框显隐类
 * Created by wyd on 2017/7/19.
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    private Dialog mDialog;
    private Context context;
    //对话框是否可取消标记
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    /**
     * @param context
     * @param cancelable              对话框是否可以取消
     * @param mProgressCancelListener 取消监听
     */
    public ProgressDialogHandler(Context context, boolean cancelable, ProgressCancelListener mProgressCancelListener) {
        super();
        this.context = context;
        this.cancelable = cancelable;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                Activity activity = (Activity) this.context;
                if (activity != null && !activity.isFinishing()) {
                    dismissProgressDialog();
                }
                break;
        }
    }

    /**
     * 关闭对话框
     */
    private void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 显示对话框
     */
    private void initProgressDialog() {
        if (mDialog == null) {
            mDialog = CustomAlertDialogUtil.createLoadingDialog(context, "加载中...", true);
            if (mDialog==null) {
                return;
            }
            mDialog.setCancelable(cancelable);
            //如何对话框可取消,设置取消监听
            if (cancelable) {
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            //对话框如果没有显示
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }
}
