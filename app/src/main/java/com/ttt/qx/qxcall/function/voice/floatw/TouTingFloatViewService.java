package com.ttt.qx.qxcall.function.voice.floatw;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ttt.qx.qxcall.QXCallApplication;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.CustomActivityManager;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoActivity;

/**
 *
 */
public class TouTingFloatViewService extends Service implements View.OnTouchListener {

	//定义浮动窗口布局
	FrameLayout mFloatLayout;
	//创建浮动窗口设置布局参数的对象
	WindowManager.LayoutParams wmParams;
	//窗口管理器对象
	WindowManager mWindowManager;
	private String TAG = "TouTingFloatViewService";
	private float mTouchStartX;
	private float mTouchStartY;
	private float x;
	private float y;
	private Chronometer call_time_tv;
	private float StartX;
	private float StartY;
	//标记当前操作 是移动还是点击 默认为点击
	private boolean move = false;

	public TouTingFloatViewService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		createFloatView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mFloatLayout != null) {
			mWindowManager.removeView(mFloatLayout);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private void createFloatView() {
		wmParams = new WindowManager.LayoutParams();
		mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
		//设置图片格式，效果为背景透明
		wmParams.format = PixelFormat.RGBA_8888;
		//设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.format = PixelFormat.TRANSLUCENT;

		//调整悬浮窗显示的停靠位置为左侧置顶
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;

		// 以屏幕左上角为原点，设置x、y初始值
		wmParams.x = 500;
		wmParams.y = 800;

		//设置悬浮窗口长宽数据
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		LayoutInflater inflater = LayoutInflater.from(getApplication());
		//获取浮动窗口视图所在布局
		mFloatLayout = (FrameLayout) inflater.inflate(R.layout.float_view, null);
		//添加mFloatLayout
		mWindowManager.addView(mFloatLayout, wmParams);
		ImageView float_btn = (ImageView) mFloatLayout.findViewById(R.id.float_btn);
		//通话时间更新
		call_time_tv = (Chronometer) mFloatLayout.findViewById(R.id.call_time_tv);
		call_time_tv.setBase(QXCallApplication.pipeiTime);
		call_time_tv.start();
		//浮动窗口按钮
		mFloatLayout.setOnTouchListener(this);
		mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//获取相对屏幕的坐标，即以屏幕左上角为原点
		x = event.getRawX();
		y = event.getRawY() - 25;   //25是系统状态栏的高度
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				move = false;//每次点击时都 置为false
				//获取相对View的坐标，即以此View左上角为原点
				mTouchStartX = event.getX();
				mTouchStartY = event.getY();
				StartX = x;
				StartY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				if (Math.abs(x - StartX) > 5 && Math.abs(y - StartY) > 5) {
					move = true;
					updateViewPosition();
				}
				break;
			case MotionEvent.ACTION_UP:
				if (move) {
					if (Math.abs(x - StartX) > 5 && Math.abs(y - StartY) > 5) {
						updateViewPosition();
						mTouchStartX = mTouchStartY = 0;
					}
				} else {
					//停止自身
					stopSelf();
					//否则 跳转到语音界面
//                    AVChatProfile.getInstance().setAVChatting(true);
//                    AVChatActivity.launch(getApplication(), QXCallApplication.accid, AVChatType.AUDIO.getValue(), AVChatActivity.FROM_UNKNOWN);
					if (call_time_tv != null) {
						call_time_tv.stop();
					}
					Activity topActivity = CustomActivityManager.getInstance().getTopActivity();
					Intent localIntent = new Intent();
					localIntent.setClass(topActivity, NHuaLiaoActivity.class);
//					localIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					topActivity.startActivity(localIntent);
				}
				break;
		}
		return true;
	}

	private void updateViewPosition() {
		//更新浮动窗口位置参数
		wmParams.x = (int) (x - mTouchStartX);
		wmParams.y = (int) (y - mTouchStartY);
		mWindowManager.updateViewLayout(mFloatLayout, wmParams);
	}
}
