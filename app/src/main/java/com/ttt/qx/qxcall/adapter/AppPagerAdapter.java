package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.start.GuideActivity;
import com.ttt.qx.qxcall.utils.ImageUtil;


/**
 * 引导页面adapter
 * Created by wyd on 2017/7/19.
 */
public class AppPagerAdapter extends PagerAdapter {
    private int[] images = null;
    private Context ctx = null;
    private LayoutInflater layoutInflater = null;

    public AppPagerAdapter(Context ctx, int[]  images) {
        this.images = images;
        this.ctx = ctx;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        ImageView iv = (ImageView) layoutInflater.inflate(R.layout.guide_view_pager_item, view, false);
//        ShowImage.showImage();//获取服务器图片资源，并显示
//        iv.setBackgroundResource(images[position]);
        ImageUtil.scaleImage((GuideActivity)ctx, iv, images[position]);
        view.addView(iv);
        return iv;
    }


}
