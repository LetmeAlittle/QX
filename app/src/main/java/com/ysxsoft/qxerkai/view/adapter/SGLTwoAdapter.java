package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.view.activity.NPersonCenterActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class SGLTwoAdapter extends BaseQuickAdapter<SaGouLiangListResponse.DataBean.ListBean, BaseViewHolder> {
    private ArrayList<SaGouLiangListResponse.DataBean.ListBean> phones = new ArrayList<>();

    public SGLTwoAdapter(int layoutResId, List<SaGouLiangListResponse.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaGouLiangListResponse.DataBean.ListBean item) {
//        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_moment_add_photos);
//        ninePhotoLayout.setVisibility(View.GONE);
//        ninePhotoLayout.setData(phones);
//        ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
//            @Override
//            public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
//                File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
//                BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(mContext)
//                        .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
//                photoPreviewIntentBuilder
//                        .previewPhotos((ArrayList<String>) models).currentPosition(position); // 当前预览图片的索引
//                mContext.startActivity(photoPreviewIntentBuilder.build());
//            }
//        });

        de.hdodenhof.circleimageview.CircleImageView logo = helper.getView(R.id.logo);

        TextView name = helper.getView(R.id.name);
        TextView time = helper.getView(R.id.time);
        TextView likeNum = helper.getView(R.id.likeNum);
        TextView content = helper.getView(R.id.tv_sgl_item_content);

        time.setText(StringUtils.convert(item.getDates()));
        name.setText(StringUtils.convert(item.getUsername()));
        content.setText(StringUtils.convert(item.getContent()));
        likeNum.setText(StringUtils.convert(item.getLikes()));
        Glide.with(mContext).load(item.getIcon()).into(logo);


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suid = "" + item.getUid();//发表人id
                if (suid.equals(DBUtils.getUserId())) {//本人
                    IntentUtil.jumpIntent(mContext, NPersonCenterActivity.class);
                } else {
                    if ("".equals(suid)) {
                        return;
                    }
                    int id = Integer.parseInt(suid);
                    mContext.startActivity(new Intent(mContext, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", suid));//查看好友资料
                }
            }
        });
    }
}
