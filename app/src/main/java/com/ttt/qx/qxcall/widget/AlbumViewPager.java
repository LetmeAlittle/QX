package com.ttt.qx.qxcall.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;

import java.io.File;
import java.util.ArrayList;



/**
 * @ClassName: AlbumViewPager wyd
 */
public class AlbumViewPager extends ViewPager implements MatrixImageView.OnMovingListener {
    public final static String TAG = "AlbumViewPager";
    //子view是否处于拖拽
    private boolean mChildIsBeingDragged = false;
    //矩阵视图
    private MatrixImageView imageView;
    //图片属性信息
    TextView tv_photo_time;
    private TextView tv_photo_angle;
    TextView tv_image_path;
    TextView tv_image_size;
    TextView tv_image_measurement;
    private LinearLayout ll_image_info;

    public AlbumViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mChildIsBeingDragged)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void startDrag() {
        mChildIsBeingDragged = true;
    }

    @Override
    public void stopDrag() {
        mChildIsBeingDragged = false;
    }

    /**
     * adapter适配
     */
    public class ViewPagerAdapter extends PagerAdapter {
        //显示图片
        private ArrayList<Bitmap> bitmaps;
        //显示图片路径
        private ArrayList<String> imageUrl;

        public ViewPagerAdapter(ArrayList<Bitmap> bitmaps, ArrayList<String> imageUrl) {
            this.bitmaps = bitmaps;
            this.imageUrl = imageUrl;
        }

        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            View imageLayout = inflate(getContext(), R.layout.item_album_pager,
                    null);
            viewGroup.addView(imageLayout);
            ll_image_info = (LinearLayout) imageLayout.findViewById(R.id.ll_image_info);
            tv_photo_time = (TextView) imageLayout.findViewById(R.id.tv_photo_time);
            tv_photo_angle = (TextView) imageLayout.findViewById(R.id.tv_photo_angle);
            tv_image_path = (TextView) imageLayout.findViewById(R.id.tv_image_path);
            tv_image_size = (TextView) imageLayout.findViewById(R.id.tv_image_size);
            tv_image_measurement = (TextView) imageLayout.findViewById(R.id.tv_image_measurement);
            String imgAbsolPath = imageUrl.get(position);//文件绝对路径
            File file = new File(imgAbsolPath);//判断文件是否存在
            if (file.exists()) {
                ll_image_info.setVisibility(INVISIBLE);
            } else {
                ll_image_info.setVisibility(INVISIBLE);
            }

//            tv_photo_angle.setText(getPhotoDirectionString(Float.valueOf(imgAbsolPath.substring((imgAbsolPath.length()-7),(imgAbsolPath.length()-4)))));
            tv_image_path.setText(imgAbsolPath);
            imageView = (MatrixImageView) imageLayout.findViewById(R.id.image);
            imageView.setOnMovingListener(AlbumViewPager.this);
            imageView.setImageBitmap(bitmaps.get(position));
            //设置当前view的背景颜色
            if (imgAbsolPath.contains("签字")) {
                imageView.setBackgroundColor(Color.WHITE);
            }
            return imageLayout;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int arg1, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    /**
     * 当前拍摄角度
     *
     * @return
     */
    public String getPhotoDirectionString(float currentAngle) {
        String currentDirection = "";
        if (currentAngle == 360) {
            currentDirection = "正北";
        } else if (currentAngle == 270) {
            currentDirection = "正西";
        } else if (currentAngle == 180) {
            currentDirection = "正南";
        } else if (currentAngle == 90) {
            currentDirection = "正东";
        } else if (currentAngle > 270 && currentAngle < 360) {
            if (currentAngle < 315) {
                float d_value = currentAngle - 270;
                currentDirection = "西偏北" + d_value + "度";
            } else {
                float d_value = 360 - currentAngle;
                currentDirection = "北偏西" + d_value + "度";
            }
        } else if (currentAngle > 180 && currentAngle < 270) {
            if (currentAngle < 225) {
                float d_value = currentAngle - 180;
                currentDirection = "南偏西" + d_value + "度";
            } else {
                float d_value = 270 - currentAngle;
                currentDirection = "西偏南" + d_value + "度";
            }
        } else if (currentAngle > 90 && currentAngle < 180) {
            if (currentAngle < 135) {
                float d_value = currentAngle - 90;
                currentDirection = "东偏南" + d_value + "度";
            } else {
                float d_value = 180 - currentAngle;
                currentDirection = "南偏东" + d_value + "度";
            }
        } else if (currentAngle > 0 && currentAngle < 90) {
            if (currentAngle < 45) {
                float d_value = currentAngle - 45;
                currentDirection = "北偏东" + d_value + "度";
            } else {
                float d_value = 90 - currentAngle;
                currentDirection = "东偏北" + d_value + "度";
            }
        }
        return currentDirection;
    }

}
