package com.ttt.qx.qxcall.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.ttt.qx.qxcall.R;

import java.io.File;

/**
 * 录音功能对话框
 * Created by 王亚东 on 2017/10/28.
 */

public class AudioRecordDialog {

    private static boolean started = false;
    private static boolean cancelled = false;
    private static boolean touched = false; // 是否按着
    private static ImageView audio_record_iv;
    private static TextView audio_status_tv;

    public interface OnExtendIAudioRecordCallback {
        //录音 动画定时器
        void onUpdateTimerTip(boolean cancel);

        //录音动画停止
        void onStopAudioRecordAnim();

        //录音动画 开始
        void onPlayAudioRecordAnim();

        void onRecordReady();

        void onRecordStart(File file, RecordType type);

        void onRecordSuccess(File file, long l, RecordType type);

        void onRecordFail();

        void onRecordCancel();

        void onRecordReachedMaxTime(int i);
    }

    private static OnExtendIAudioRecordCallback extendIAudioRecordCallback;
    // 语音
    private static AudioRecorder audioMessageHelper;

    /**
     * 从底部弹出  录音对话框
     *
     * @param activity
     */
    public static void showAudioRecordDialog(Activity activity
            , OnExtendIAudioRecordCallback onExtendIAudioRecordCallback) {
        started = false;
        touched = false;
        cancelled = false;
        extendIAudioRecordCallback = onExtendIAudioRecordCallback;
        Dialog dialog = new Dialog(activity, R.style.dialogStyle2);
        View view = View.inflate(activity, R.layout.audio_record_dialog, null);
        audio_status_tv = (TextView) view.findViewById(R.id.audio_status_tv);
        TextView close_tv = (TextView) view.findViewById(R.id.close_tv);
        close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        audio_record_iv = (ImageView) view.findViewById(R.id.audio_record_iv);
        audio_record_iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touched = true;
                    initAudioRecord(activity);
                    onStartAudioRecord(activity);
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    touched = false;
                    onEndAudioRecord(isCancelled(v, event), activity);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    touched = true;
                    cancelAudioRecord(isCancelled(v, event));
                }

                return false;
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

    /**
     * 取消语音录制
     *
     * @param cancel
     */
    private static void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;
        extendIAudioRecordCallback.onUpdateTimerTip(cancel);
    }

    // 上滑取消录音判断
    private static boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    /**
     * 初始化AudioRecord
     */
    private static void initAudioRecord(Activity activity) {
        if (audioMessageHelper == null) {
            audioMessageHelper = new AudioRecorder(activity, RecordType.AAC, AudioRecorder.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND, new MyIAudioRecordCallback());
        }
    }

    /**
     * 开始语音录制
     */
    private static void onStartAudioRecord(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        audioMessageHelper.startRecord();
        cancelled = false;
    }

    /**
     * 结束语音录制
     *
     * @param cancel
     */
    private static void onEndAudioRecord(boolean cancel, Activity activity) {
        started = false;
        activity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        audioMessageHelper.completeRecord(cancel);
        audio_status_tv.setText(R.string.hold_press_audio_record_text);
        extendIAudioRecordCallback.onStopAudioRecordAnim();
    }

    /**
     * 录音回调
     */
    static class MyIAudioRecordCallback implements IAudioRecordCallback {

        @Override
        public void onRecordReady() {
            extendIAudioRecordCallback.onRecordReady();
        }

        @Override
        public void onRecordStart(File file, RecordType type) {
            started = true;
            if (!touched) {
                return;
            }
            audio_status_tv.setText(R.string.record_audio_end);

            extendIAudioRecordCallback.onUpdateTimerTip(false); // 初始化语音动画状态
            extendIAudioRecordCallback.onPlayAudioRecordAnim();
        }

        @Override
        public void onRecordSuccess(File file, long l, RecordType type) {
            extendIAudioRecordCallback.onRecordSuccess(file, l, type);
        }

        @Override
        public void onRecordFail() {
            if (started) {
                extendIAudioRecordCallback.onRecordFail();
            }
        }

        @Override
        public void onRecordCancel() {
            extendIAudioRecordCallback.onRecordCancel();
        }

        @Override
        public void onRecordReachedMaxTime(int i) {
            extendIAudioRecordCallback.onStopAudioRecordAnim();
            extendIAudioRecordCallback.onRecordReachedMaxTime(i);
        }
    }
}
