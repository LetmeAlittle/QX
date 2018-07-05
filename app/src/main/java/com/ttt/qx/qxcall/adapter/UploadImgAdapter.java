package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.dialog.PicLookFragment;
import com.ttt.qx.qxcall.eventbus.DelImg;
import com.ttt.qx.qxcall.function.find.view.PublishDynamicsActivity;
import com.ttt.qx.qxcall.utils.DeviceUtil;
import com.ttt.qx.qxcall.utils.UriUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户图片 上传适配器
 * Created by wyd on 2017/7/30.
 */

public class UploadImgAdapter extends RecyclerView.Adapter<UploadImgAdapter.ViewHolder> {
    private final Context context;
    private List<File> files = new ArrayList<>();
    private final LayoutInflater mInflater;
    private final PublishDynamicsActivity mPublishDynamicsActivity;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files.clear();
        this.files.addAll(files);
    }

    public UploadImgAdapter(Context context, List<File> files) {
        this.context = context;
        this.files.addAll(files);
        mInflater = LayoutInflater.from(context);
        mPublishDynamicsActivity = (PublishDynamicsActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.upload_img_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        File file = files.get(position);
//        Glide.with(context).load(file).into(holder.upload_iv);
        holder.upload_iv.setImageBitmap(UriUtil.getSmallBitmap(file.getPath(),(int) DeviceUtil.getScreenWidth(context)/2,(int)DeviceUtil.getScreenHeight(context)/2));
            holder.del_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除当前位置图片
                DelImg img = new DelImg();
                img.position = position;
                EventBus.getDefault().post(img);
            }
        });
        holder.upload_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicLookFragment picLookFragment = new PicLookFragment();
                Bundle bundle = new Bundle();
                ArrayList<String> picList = new ArrayList<String>();
                for (File f : files) {
                    picList.add(f.getAbsolutePath());
                }
                bundle.putStringArrayList("picList", picList);
                bundle.putInt("position", position);
                picLookFragment.setArguments(bundle);
                picLookFragment.show(mPublishDynamicsActivity.getSupportFragmentManager(), "picLookFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.upload_iv)
        ImageView upload_iv;
        @BindView(R.id.del_iv)
        ImageView del_iv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
