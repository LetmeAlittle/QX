package com.ttt.qx.qxcall.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;

/**
 * Created by wyd on 2016/3/2.
 */
public class DrawableBitmapTransferScaleUtil{
    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        if (drawable != null) {
            int width = drawable.getIntrinsicWidth();   // 取drawable的长宽
            int height = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;         //取drawable的颜色格式
            if (width == 0) {
                width = 64;
            }
            if (height == 0) {
                height = 64;
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应bitmap
            Canvas canvas = new Canvas(bitmap);         // 建立对应bitmap的画布
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);      // 把drawable内容画到画布中
            return bitmap;
        }
        return null;
    }
    /**
     * 修改图片大小
     *
     * @param bm        图片
     * @param newHeight 新高度
     * @param newWidth  新宽度
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
    /**
     * 对Drawable进行相关缩放
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();//获取Drawable内在宽度
        int height = drawable.getIntrinsicHeight();//获取Drawable内在高度
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable转换成bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的Matrix对象
        float scaleWidth = ((float) w / width);   // 计算缩放比例
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        return new BitmapDrawable(newbmp);       // 把bitmap转换成drawable并返回
    }

    /**
     * 修改图片大小
     *
     * @param bm
     * @param newWidth
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bm, int newWidth) {
        Bitmap resizedBitmap = null;
        if (bm != null) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float temp = ((float) height) / ((float) width);
            int newHeight = (int) ((newWidth) * temp);
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);
            // matrix.postRotate(45);
            resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
            bm.recycle();
        }

        return resizedBitmap;
    }

    /**
     * 从手机内存卡中读取图片信息，转化为bitmap对象
     *
     * @param fileName 图片全路径
     * @return bitmap
     */
    public static Bitmap getBitmap(String fileName, int width) {
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        Bitmap newBitmap = null;
        if (bitmap != null) {
            newBitmap = zoomBitmap(bitmap, width);//宽度指定
            //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
            bitmap.recycle();
        }
        return newBitmap;
    }
    /**
     * 从手机内存卡中读取图片信息，转化为bitmap对象
     *
     * @param inputStream 图片文件流
     * @return bitmap
     */
    public static Bitmap getBitmap(InputStream inputStream, int width) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        Bitmap newBitmap = null;
        if (bitmap != null) {
            newBitmap = zoomBitmap(bitmap, width);//宽度指定
            //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
            bitmap.recycle();
        }
        return newBitmap;
    }
}
