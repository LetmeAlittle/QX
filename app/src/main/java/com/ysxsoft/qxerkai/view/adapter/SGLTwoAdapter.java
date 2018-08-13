package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangLikeResponse;
import com.ysxsoft.qxerkai.net.response.SaGouLiangListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.ToastUtils;
import com.ysxsoft.qxerkai.view.activity.NPersonCenterActivity;
import com.ysxsoft.qxerkai.view.activity.NQingQuDetailActivity;
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

public class SGLTwoAdapter extends BaseQuickAdapter<SaGouLiangListResponse.DataBean.ListBean, BaseViewHolder> {
    private ArrayList<SaGouLiangListResponse.DataBean.ListBean> phones = new ArrayList<>();
    private int realPosition;

    public SGLTwoAdapter(int layoutResId, List<SaGouLiangListResponse.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaGouLiangListResponse.DataBean.ListBean item) {
//        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_moment_add_photos);
////        ninePhotoLayout.setVisibility(View.GONE);
////        ninePhotoLayout.setData(phones);
////        ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
////            @Override
////            public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
////                File downloadDir = new File(Environment.getExternalStorageDirectory(), "QX");
////                BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(mContext)
////                        .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
////                photoPreviewIntentBuilder
////                        .previewPhotos((ArrayList<String>) models).currentPosition(position); // 当前预览图片的索引
////                mContext.startActivity(photoPreviewIntentBuilder.build());
////            }
////        });

        de.hdodenhof.circleimageview.CircleImageView logo = helper.getView(R.id.logo);

        TextView name = helper.getView(R.id.name);
        TextView time = helper.getView(R.id.time);
        TextView likeNum = helper.getView(R.id.likeNum);
        TextView content = helper.getView(R.id.tv_sgl_item_content);
        LinearLayout gouTouLayout = helper.getView(R.id.likeLayout);
        ImageView goutou = helper.getView(R.id.goutou);

        time.setText(StringUtils.convert(item.getDates()));
        name.setText(StringUtils.convert(item.getUsername()));
        content.setText(StringUtils.convert(item.getContent()));
        likeNum.setText(StringUtils.convert(item.getLikes()));
        Glide.with(mContext).load(item.getIcon()).into(logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = item.getUid();//发表人id
                mContext.startActivity(new Intent(mContext, NZhiLiaoActivity.class).putExtra("id", id).putExtra("accid", id + ""));//查看好友资料
            }
        });
        gouTouLayout.setOnClickListener(new OnLikeClickListener(item, item.getSid(), helper.getAdapterPosition()));

        //删除
        TextView delete=helper.getView(R.id.delete);
        if(item.getUid()==DBUtils.getIntUserId()){
            delete.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.GONE);
        }
        delete.setOnClickListener(new OnDeleteClickListener(item,item.getSid(),helper.getAdapterPosition()));
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
                            ToastUtils.showToast(mContext,"赠送成功！",1);
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

    /**
     * 刷新数据源
     */
    private void refresh() {
        String like = mData.get(realPosition).getLikes();
        if (like != null && !"".equals(like)) {
            int likeNum = Integer.parseInt(mData.get(realPosition).getLikes());
            mData.get(realPosition).setLikes("" + (likeNum + 1));
        }

        this.notifyDataSetChanged();
    }

    //删除
    private class OnDeleteClickListener implements View.OnClickListener {
        private int sid;
        private int position;
        private SaGouLiangListResponse.DataBean.ListBean item;

        public OnDeleteClickListener(SaGouLiangListResponse.DataBean.ListBean item, int sid, int position) {
            this.item = item;
            this.sid = sid;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            realPosition = position - 1;//去除header
			new MaterialDialog.Builder(mContext)
					.title("温馨提示")
					.content("是否删除？")
					.positiveText("确定")
					.negativeText("取消")
					.onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
							delete(sid + "");
						}
					})
					.onNegative(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
							dialog.dismiss();
						}
					})
					.show();
        }
    }

    private void delete(String cid){
        Map<String, String> map = new HashMap<>();
        map.put("cid", cid);
        RetrofitTools.deleteSaGouLiang(map)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        if (code == 200) {
                            refreshItem();
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

    private void refreshItem(){
        if(mData.size()>realPosition){
            mData.remove(realPosition);
        }
        this.notifyDataSetChanged();
    }

}
