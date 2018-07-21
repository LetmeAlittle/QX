package com.ttt.qx.qxcall.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ysxsoft.qxerkai.utils.SystemUtils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sincerly on 2018/7/20.
 */

public class ReceiveTextDialog {
	/**
	 * 收到弹幕动画
	 *
	 * @param activity
	 */
	public static void showReceiveTextDialog(Activity activity, Map<String, String> receiveMap, OnComponentClickListener onComponentClickListener) {
		View view = View.inflate(activity, R.layout.receive_text_dialog, null);
		TextView content = (TextView) view.findViewById(R.id.content);
		content.setText(receiveMap.get("content"));
		FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView();
		view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) view.getLayoutParams();
		layoutParams.gravity=Gravity.BOTTOM;
		layoutParams.bottomMargin=SystemUtils.getStatusHeight(activity)*1/3;
		view.setLayoutParams(layoutParams);

		frameLayout.addView(view);
		ValueAnimator valueAnimator=ValueAnimator.ofFloat(0f,1f);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Log.e("tag","距离："+((float)animation.getAnimatedValue()*getScreenWidth(activity)));
				view.setTranslationX((float)animation.getAnimatedValue()* getScreenWidth(activity));
			}
		});
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.setDuration(3000);
		valueAnimator.setRepeatCount(0);
		valueAnimator.setRepeatMode(ValueAnimator.RESTART);
		valueAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				frameLayout.removeView(view);
			}
		});
		valueAnimator.start();
	}

	public static float getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;// 获取分辨率宽度
	}

	public interface OnComponentClickListener {
		void onCancle();

		void onSend(String gift_id);
	}
}
