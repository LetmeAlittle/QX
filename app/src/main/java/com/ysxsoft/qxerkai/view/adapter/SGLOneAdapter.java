package com.ysxsoft.qxerkai.view.adapter;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class SGLOneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private ArrayList<String> phones = new ArrayList<>();

    public SGLOneAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
        phones.clear();
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
        phones.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521440728160&di=460a1693c657c4d40c73caf76688a26d&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004212538_SXjWV.jpeg");
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvContent=helper.getView(R.id.tv_sgl_item_content);
        tvContent.setVisibility(View.GONE);
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_moment_add_photos);
        ninePhotoLayout.setData(phones);
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
    }
}
