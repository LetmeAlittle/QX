package com.ysxsoft.qxerkai.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;

public class GlideBlurTransform extends BitmapTransformation {
	private Context mContext;

	public GlideBlurTransform(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected Bitmap transform(BitmapPool mBitmapPool, Bitmap toTransform, int outWidth, int outHeight) {

		Bitmap bitmap = mBitmapPool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		canvas.scale(1, 1);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bitmap, 0, 0, paint);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			try {
				bitmap = RSBlur.blur(mContext, bitmap, 100);
			} catch (RSRuntimeException e) {
				bitmap = FastBlur.blur(bitmap, 100, true);
			}
		} else {
			bitmap = FastBlur.blur(bitmap, 100, true);
		}

		return bitmap;
	}

	@Override
	public String getId() {
		return getClass().getName();
	}
}
