package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoTouTingActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class TouTingOneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TouTingOneAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivTouTing=helper.getView(R.id.iv_touting);
        ivTouTing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext,NHuaLiaoTouTingActivity.class));
            }
        });

        CircleImageView circleImageView1 = helper.getView(R.id.civ_headImageView1);
        CircleImageView circleImageView2 = helper.getView(R.id.civ_headImageView2);
        //高斯模糊处理
        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap womenBitmap = Glide.with(mContext)
                            .load(R.mipmap.image5)
                            .asBitmap() //必须
                            .centerCrop()
                            .into(500, 500)
                            .get();
                    BlurEntity blurEntity = new BlurEntity();
                    blurEntity.bitmap = womenBitmap;
                    blurEntity.imageView = circleImageView1;
                    Message message = Message.obtain();
                    message.what = SUCCESS;
                    message.obj = blurEntity;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap womenBitmap = Glide.with(mContext)
                            .load(R.mipmap.image4)
                            .asBitmap() //必须
                            .centerCrop()
                            .into(500, 500)
                            .get();
                    BlurEntity blurEntity = new BlurEntity();
                    blurEntity.bitmap = womenBitmap;
                    blurEntity.imageView = circleImageView2;
                    Message message = Message.obtain();
                    message.what = SUCCESS;
                    message.obj = blurEntity;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private final int SUCCESS = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    BlurEntity blurEntity = (BlurEntity) msg.obj;
//                    blurEntity.imageView.setImageBitmap(blurEntity.bitmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        blurEntity.imageView.setImageBitmap(ImageUtil.blurBitmap(mContext
                                , blurEntity.bitmap, 25f));
                    }
//                    ImageUtil.blur(blurEntity.bitmap, blurEntity.imageView);
                    break;
            }
        }
    };

    class BlurEntity {
        public Bitmap bitmap;
        public CircleImageView imageView;
    }
}
