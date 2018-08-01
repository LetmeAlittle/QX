package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.IRecyclerView;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import rx.Subscriber;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class PengYouQuanAdapter extends BaseQuickAdapter<DynamicResponse.DataBean.ListBean, BaseViewHolder> {

    private OnIconClickListener onIconClickListener;

    public void setOnIconClickListener(OnIconClickListener onIconClickListener){
        this.onIconClickListener = onIconClickListener;
    }

    public PengYouQuanAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicResponse.DataBean.ListBean item) {
        int position = helper.getLayoutPosition();

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

        TextView tvZan = helper.getView(R.id.tv_zan);
        boolean zan ;
        if (item.getIs_zan() == 0) {
            zan = false;
        } else {
            zan = true;
        }
        tvZan.setSelected(zan);

        helper.setText(R.id.tv_username,item.getNick_name()+"");
        helper.setText(R.id.tv_time,item.getCreate_at()+"");
        helper.setText(R.id.tv_content,item.getContent()+"");

        helper.setText(R.id.tv_look,item.getClick_num()+"");
        tvZan.setText(item.getZan_num()+"");
        helper.setText(R.id.tv_ping,item.getReply_num()+"");
        helper.setText(R.id.tv_liwu,item.getGift_num()+"");

        Glide.with(mContext)
                .load(item.getMember_avatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into((ImageView) helper.getView(R.id.iv_tou));

        tvZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIconClickListener.onZanClick(item);
            }
        });

        helper.getView(R.id.tv_liwu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIconClickListener.onGiftClick(item);
            }
        });

        //删除朋友圈
        TextView delete = helper.getView(R.id.delete);
        if(item.getMember_id()== DBUtils.getIntUserId()){
            delete.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.GONE);
        }
        delete.setOnClickListener(new OnDeleteClick(item.getId(),position));
    }


    public interface OnIconClickListener{
        void onZanClick(DynamicResponse.DataBean.ListBean item);
        void onGiftClick(DynamicResponse.DataBean.ListBean item);
    }


    private class OnDeleteClick implements View.OnClickListener{
        private int cid;
        private int p;
        public OnDeleteClick(int cid,int position) {
            this.cid = cid;
            this.p=position;
        }

        @Override
        public void onClick(View v) {
            selectedItem=p-1;
            new MaterialDialog.Builder(mContext)
                    .title("温馨提示")
                    .content("是否删除该动态")
                    .positiveText("确定")
                    .negativeText("取消")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            delete(cid);
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

    private void delete(int cid){
        Map<String, String> map2 = new HashMap<>();
        map2.put("cid", ""+cid);
        RetrofitTools.deletePengYouQuan(map2)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse, int code, String msg) {
                        if (code == 200) {
                            refreshAdapter();
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        Log.e("tag", "删除朋友圈失败");
                    }
                });
    }
    private int selectedItem=-1;
    private void refreshAdapter(){
        if(mData.size()>selectedItem&&selectedItem!=-1){
            mData.remove(selectedItem);
            notifyDataSetChanged();
        }
    }
}
