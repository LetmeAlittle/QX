package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.IRecyclerView;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class PengYouQuanAdapter extends BaseQuickAdapter<DynamicResponse.DataBean.ListBean, BaseViewHolder> {

    public PengYouQuanAdapter(int layoutResId, List<DynamicResponse.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicResponse.DataBean.ListBean item) {
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_moment_add_photos);
        ninePhotoLayout.setData((ArrayList<String>) item.getImg_list());
        ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
            @Override
            public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
                File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
                BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(mContext)
                        .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
                photoPreviewIntentBuilder
                        .previewPhotos((ArrayList<String>) models).currentPosition(position); // 当前预览图片的索引
                mContext.startActivity(photoPreviewIntentBuilder.build());
            }
        });


        helper.setText(R.id.tv_username,item.getNick_name()+"");
        helper.setText(R.id.tv_time,item.getCreate_at()+"");
        helper.setText(R.id.tv_content,item.getContent()+"");

        helper.setText(R.id.tv_look,item.getClick_num()+"");
        helper.setText(R.id.tv_zan,item.getZan_num()+"");
        helper.setText(R.id.tv_ping,item.getReply_num()+"");
        helper.setText(R.id.tv_liwu,item.getGift_num()+"");

        Glide.with(mContext)
                .load(item.getMember_avatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) helper.getView(R.id.iv_tou));

    }
}
