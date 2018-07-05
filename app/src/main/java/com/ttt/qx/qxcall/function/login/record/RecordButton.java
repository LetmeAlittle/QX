package com.ttt.qx.qxcall.function.login.record;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;

public class RecordButton extends Button {

    private static final int MIN_RECORD_TIME = 1; // 最短录音时间，单位秒
    private static final int RECORD_OFF = 0; // 不在录音
    private static final int RECORD_ON = 1; // 正在录音

    private Dialog mRecordDialog;
    private RecordStrategy mAudioRecorder;
    private Thread mRecordThread;
    private RecordListener listener;
    private final int maxRecord = 30;//30s
    private int recordState = 0; // 录音状态
    private float recodeTime = 0.0f; // 录音时长，如果录音时间太短则录音失败
    private double voiceValue = 0.0; // 录音的音量值
    private boolean isCanceled = false; // 是否取消录音
    private float downY;

    private Chronometer record_time;
    private TextView dialogTextView;
    private ImageView dialogImg;
    private Context mContext;
    private boolean max = false;

    public RecordButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setText("按住 说话");
    }

    public void setAudioRecord(RecordStrategy record) {
        this.mAudioRecorder = record;
    }

    public void setRecordListener(RecordListener listener) {
        this.listener = listener;
    }

    // 录音时显示Dialog
    private void showVoiceDialog(int flag) {
        if (mRecordDialog == null) {
            mRecordDialog = new Dialog(mContext, R.style.Dialogstyle);
            mRecordDialog.setContentView(R.layout.dialog_record);
            dialogImg = (ImageView) mRecordDialog
                    .findViewById(R.id.record_dialog_img);
            dialogTextView = (TextView) mRecordDialog
                    .findViewById(R.id.record_dialog_txt);
            record_time = (Chronometer) mRecordDialog
                    .findViewById(R.id.record_time);
        }
        switch (flag) {
            case 1:
//			dialogImg.setImageResource(R.drawable.record_cancel);
                dialogTextView.setText("松开手指 取消录音！");
                this.setText("松开手指 取消录音！");
                break;

            default:
                dialogImg.setImageResource(R.drawable.record_animate_01);
                dialogTextView.setText("向上滑动 取消录音！");
                this.setText("松开手指 完成录音！");
                break;
        }
        mRecordDialog.show();
    }

    // 录音时间太短时Toast显示
    private void showWarnToast(String toastText) {
        Toast toast = new Toast(mContext);
        View warnView = LayoutInflater.from(mContext).inflate(
                R.layout.toast_warn, null);
        toast.setView(warnView);
        toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间
        toast.show();
    }

    // 开启录音计时线程
    private void callRecordTimeThread() {
        mRecordThread = new Thread(recordThread);
        mRecordThread.start();
    }

    // 录音Dialog图片随录音音量大小切换
    private void setDialogImage() {
        if (voiceValue < 600.0) {
            dialogImg.setImageResource(R.drawable.record_animate_01);
        } else if (voiceValue > 600.0 && voiceValue < 1000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_02);
        } else if (voiceValue > 1000.0 && voiceValue < 1200.0) {
            dialogImg.setImageResource(R.drawable.record_animate_03);
        } else if (voiceValue > 1200.0 && voiceValue < 1400.0) {
            dialogImg.setImageResource(R.drawable.record_animate_04);
        } else if (voiceValue > 1400.0 && voiceValue < 1600.0) {
            dialogImg.setImageResource(R.drawable.record_animate_05);
        } else if (voiceValue > 1600.0 && voiceValue < 1800.0) {
            dialogImg.setImageResource(R.drawable.record_animate_06);
        } else if (voiceValue > 1800.0 && voiceValue < 2000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_07);
        } else if (voiceValue > 2000.0 && voiceValue < 3000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_08);
        } else if (voiceValue > 3000.0 && voiceValue < 4000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_09);
        } else if (voiceValue > 4000.0 && voiceValue < 6000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_10);
        } else if (voiceValue > 6000.0 && voiceValue < 8000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_11);
        } else if (voiceValue > 8000.0 && voiceValue < 10000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_12);
        } else if (voiceValue > 10000.0 && voiceValue < 12000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_13);
        } else if (voiceValue > 12000.0) {
            dialogImg.setImageResource(R.drawable.record_animate_14);
        }
    }

    // 录音线程
    private Runnable recordThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            while (recordState == RECORD_ON) {
                {
                    try {
                        Thread.sleep(100);
                        recodeTime += 0.1;
                        // 获取音量，更新dialog
                        if (!isCanceled) {
                            Message message = Message.obtain();
                            if (recodeTime >= maxRecord) {//大于最大录音限制 结束录音
                                message.what = 10;
                                max = true;
                            } else {
                                message.what = 11;
                                voiceValue = mAudioRecorder.getAmplitude();
                            }
                            recordHandler.sendMessage(message);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler recordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    setDialogImage();
                    break;
                case 10:
                    //结束录音
                    if (recordState == RECORD_ON) {
                        recordState = RECORD_OFF;
                        if (mRecordDialog.isShowing()) {
                            mRecordDialog.dismiss();
                        }
                        record_time.stop();
                        mAudioRecorder.stop();
                        mRecordThread.interrupt();
                        voiceValue = 0.0;
                        if (isCanceled) {
                            mAudioRecorder.deleteOldFile();
                        } else {
                            if (listener != null) {
                                listener.limitTime(mAudioRecorder.getFilePath(),"30");
                            }
                        }
                        isCanceled = false;
                        RecordButton.this.setText("按住 说话");
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下按钮
                if (recordState != RECORD_ON) {
                    showVoiceDialog(0);
                    downY = event.getY();
                    if (mAudioRecorder != null) {
                        mAudioRecorder.ready();
                        recordState = RECORD_ON;
                        mAudioRecorder.start();
                        callRecordTimeThread();
                        //计时
                        record_time.setBase(SystemClock.elapsedRealtime()-0);
                        record_time.start();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // 滑动手指
                float moveY = event.getY();
                if (downY - moveY > 50) {
                    isCanceled = true;
                    showVoiceDialog(1);
                }
                if (downY - moveY < 20) {
                    isCanceled = false;
                    showVoiceDialog(0);
                }
                break;
            case MotionEvent.ACTION_UP: // 松开手指
                if (!max) {
                    if (recordState == RECORD_ON) {
                        recordState = RECORD_OFF;
                        if (mRecordDialog.isShowing()) {
                            mRecordDialog.dismiss();
                        }
                        record_time.stop();
                        mAudioRecorder.stop();
                        mRecordThread.interrupt();
                        voiceValue = 0.0;
                        if (isCanceled) {
                            mAudioRecorder.deleteOldFile();
                        } else {
                            if (recodeTime < MIN_RECORD_TIME) {
                                showWarnToast("时间太短  录音失败");
                                mAudioRecorder.deleteOldFile();
                            } else {
                                if (listener != null) {
                                    listener.recordEnd(mAudioRecorder.getFilePath(), String.valueOf((int)recodeTime));
                                }
                            }
                        }
                        isCanceled = false;
                        this.setText("按住 说话");
                    }
                } else {
                    if (mRecordDialog.isShowing()) {
                        mRecordDialog.dismiss();
                    }
                }
                max = false;
                break;
        }
        return true;
    }

    public interface RecordListener {
        public void recordEnd(String filePath,String recordtime);

        //
        void limitTime(String filePath,String recordtime);
    }
}
