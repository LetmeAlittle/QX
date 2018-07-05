package com.ttt.qx.qxcall.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.DeviceUtil;
import com.ttt.qx.qxcall.utils.DialogUtil;
import com.ttt.qx.qxcall.utils.DrawableBitmapTransferScaleUtil;
import com.ttt.qx.qxcall.widget.AlbumViewPager;

import java.io.File;
import java.util.ArrayList;


/**
 * 图片查看 对话框
 * Created by wyd on 2017/7/22.
 */

public class PicLookFragment extends DialogFragment implements View.OnClickListener {
    //显示图片 有可能是文件路径 也可能是URL 注意保存的是绝对路径
    private ArrayList<String> imageUrl;
    //当前点击位置，默认为0，在这里都是0
    private int position;
    //自定义viewPager
    private AlbumViewPager mViewPager;
    //返回按钮
    private ImageView mBackView;
    //当前第几张
    private TextView mCountView;

    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(context, R.style.dialogStyle);
        View view = View.inflate(context, R.layout.pic_look_frame, null);
        Bundle bundle = getArguments();
        imageUrl = bundle.getStringArrayList("picList");
        position = bundle.getInt("position", 0);
        // 获取图片资源
        mViewPager = (AlbumViewPager) view.findViewById(R.id.albumviewpager);
        mBackView = (ImageView) view.findViewById(R.id.header_bar_photo_back);
        mCountView = (TextView) view.findViewById(R.id.header_bar_photo_count);
        mBackView.setOnClickListener(this);
        mCountView.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(pageChangeListener);
        if (imageUrl.size() > 0) {
            int currentItem = position;
            //图片路径信息转化为bitmap // TODO: 2017/8/1 注意这只是图片是本地文件的情况下
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (String img : imageUrl) {
                File file = new File(img);
                if (file != null && file.exists()) {
                    bitmaps.add(DrawableBitmapTransferScaleUtil.getBitmap(img, getResources().getInteger(R.integer.i1080)));
                }else{
                    Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_stub);
                    bitmaps.add(bmp);
                }
            }
            mViewPager.setAdapter(mViewPager.new ViewPagerAdapter(bitmaps,imageUrl));
            mViewPager.setCurrentItem(position);
            if (currentItem == 0) {
                mCountView.setText((currentItem + 1) + "/" + imageUrl.size());
            } else {
//                mCountView.setText((currentItem) + "/" + imageUrl.size());
                mCountView.setText((currentItem + 1) + "/" + imageUrl.size());
            }
        } else {
            mCountView.setText("0/0");
        }
        dialog.setContentView(view);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        DialogUtil.setDialogSize(getDialog(), (int) DeviceUtil.getScreenWidth(context)
                , (int) DeviceUtil.getScreenHeight(context));
    }
    /**
     * 滑动监听
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (mViewPager.getAdapter() != null) {
                String text = (position + 1) + "/"
                        + mViewPager.getAdapter().getCount();
                mCountView.setText(text);
            } else {
                mCountView.setText("0/0");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_bar_photo_back:
                dismiss();
                break;

            default:
                break;
        }
    }
}
