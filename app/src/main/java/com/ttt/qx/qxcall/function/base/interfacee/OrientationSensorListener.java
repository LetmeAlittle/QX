package com.ttt.qx.qxcall.function.base.interfacee;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * 通过重力 及方向传感器判断横竖屏
 * Created by wyd on 2017/8/11.
 */

public class OrientationSensorListener implements SensorEventListener {private static final int _DATA_X = 0;
    private static final int _DATA_Y = 1;
    private static final int _DATA_Z = 2;

    public static final int ORIENTATION_UNKNOWN = -1;
    private boolean sensor_flag = true;

    public static final String TAG = "xujun";

    int mLastAngle=-1;
    OrientationChangleListener mOrientationChangleListener;
    //屏幕当前处于的方向
    OrientationChangleListener.Oritation oritation;
    public OrientationSensorListener(OrientationChangleListener orientationChangleListener){
        mOrientationChangleListener=orientationChangleListener;
        oritation = orientationChangleListener.ORITATION;
    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        int orientation = ORIENTATION_UNKNOWN;
        float X = -values[_DATA_X];
        float Y = -values[_DATA_Y];
        float Z = -values[_DATA_Z];
        /**
         * 这一段据说是 android源码里面拿出来的计算 屏幕旋转的 不懂 先留着 万一以后懂了呢
         */
        float magnitude = X * X + Y * Y;
        // Don't trust the angle if the magnitude is small compared to the y value
        if (magnitude * 4 >= Z * Z) {
            //屏幕旋转时
            float OneEightyOverPi = 57.29577957855f;
            float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
            orientation = 90 - (int) Math.round(angle);
            // normalize to 0 - 359 range
            while (orientation >= 360) {
                orientation -= 360;
            }
            while (orientation < 0) {
                orientation += 360;
            }
        }
        if (orientation > 225 && orientation < 315) { //横屏
            sensor_flag = false;
            oritation = OrientationChangleListener.Oritation.LANDSCAPE;
        } else if ((orientation > 315 && orientation < 360) || (orientation > 0 &&
                orientation < 45)) { //竖屏
            sensor_flag = true;
            oritation = OrientationChangleListener.Oritation.PORTRAIT;
        }
//    Log.i(TAG, "onSensorChanged: orientation=" + orientation+"  mLastAngle="+mLastAngle);
        if(mLastAngle!=orientation && mOrientationChangleListener!=null){
            mOrientationChangleListener.onChange(oritation);
            mLastAngle=orientation;
        }

    }
}
