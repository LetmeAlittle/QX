package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.view.activity.NPersonCenterActivity;
import com.ysxsoft.qxerkai.view.activity.NZhiLiaoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class SGLOneAdapter extends BaseQuickAdapter<SaGouLiangListResponse.DataBean.ListBean, BaseViewHolder> {
    private List<SaGouLiangListResponse.DataBean.ListBean> phones = new ArrayList<>();
    private int realPosition;

    public SGLOneAdapter(int layoutResId, List<SaGouLiangListResponse.DataBean.ListBean> data) {
        super(layoutResId, data);
        this.phones.clear();
        this.phones.addAll(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaGouLiangListResponse.DataBean.ListBean item) {

        TextView tvContent = helper.getView(R.id.tv_sgl_item_content);
        tvContent.setVisibility(View.GONE);
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_moment_add_photos);
        de.hdodenhof.circleimageview.CircleImageView logo = helper.getView(R.id.logo);
        LinearLayout gouTouLayout = helper.getView(R.id.likeLayout);
        ImageView goutou = helper.getView(R.id.goutou);
        TextView name = helper.getView(R.id.name);
        TextView time = helper.getView(R.id.time);
        TextView likeNum = helper.getView(R.id.likeNum);

        List<SaGouLiangListResponse.DataBean.ListBean.ImgsBeanX> imgs = item.getImgs();
        if (imgs != null) {
            int size = imgs.size();
            ArrayList<String> img = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                img.add(imgs.get(i).getImg());
            }
            ninePhotoLayout.setData(img);
        }
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
        time.setText(StringUtils.convert(item.getDates()));
        name.setText(StringUtils.convert(item.getUsername()));
        likeNum.setText(StringUtils.convert(item.getLikes()));
        Glide.with(mContext).load(item.getIcon()).into(logo);
        if (item.isLiked()) {
            goutou.setImageResource(R.mipmap.gutou_red);
        } else {
            goutou.setImageResource(R.mipmap.gl_gutou_hui);
        }
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //发表人id
//                if (suid.equals(DBUtils.getUserId())) {//本人
//                    IntentUtil.jumpIntent(mContext, NPersonCenterActivity.class);
//                } else {
//                 }

                int id = item.getUid();//发表人id
                mContext.startActivity(new Intent(mContext, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", id+""));//查看好友资料
            }
        });
        gouTouLayout.setOnClickListener(new OnLikeClickListener(item, item.getSid(), helper.getAdapterPosition()));
    }

    private class OnLikeClickListener implements View.OnClickListener {
        private int sid;
        private int position;
        private SaGouLiangListResponse.DataBean.ListBean item;

        public OnLikeClickListener(SaGouLiangListResponse.DataBean.ListBean item, int sid, int position) {
            this.item = item;
            this.sid = sid;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            realPosition = position - 1;//去除header
            like(sid + "");
        }
    }

    /**
     * 点赞
     *
     * @param sid
     */
    private void like(String sid) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());
        map.put("sid", sid);

        RetrofitTools.likeSaGouLiang(map)
                .subscribe(new ResponseSubscriber<SaGouLiangLikeResponse>() {
                    @Override
                    public void onSuccess(SaGouLiangLikeResponse saGouLiangLikeResponse, int code, String msg) {
                        if (code == 200) {
                            refresh();
                        } else {
                            ToastUtil.showToast(mContext, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void refresh() {
        String like=mData.get(realPosition).getLikes();
        if(like!=null&&!"".equals(like)){
            int likeNum=Integer.parseInt(mData.get(realPosition).getLikes());
            mData.get(realPosition).setLikes(""+(likeNum+1));
        }

       this.notifyDataSetChanged();
    }
}
