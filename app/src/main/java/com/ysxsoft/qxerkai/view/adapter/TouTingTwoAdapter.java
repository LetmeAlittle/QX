package com.ysxsoft.qxerkai.view.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.adapter.ListenCategoryAdapter;
import com.ttt.qx.qxcall.function.find.model.entity.BlurEntity;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetLuYinListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.GlideBlurTransform;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.view.activity.NHuaLiaoTouTingActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by zhaozhipeng on 18/3/19.
 */

public class TouTingTwoAdapter extends BaseQuickAdapter<GetLuYinListResponse.DataBeanX.DataBean, BaseViewHolder> {

    public TouTingTwoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetLuYinListResponse.DataBeanX.DataBean item) {
        ImageView ivTouTing=helper.getView(R.id.iv_touting);
        ivTouTing.setOnClickListener(new ItemClickListener(""+item.getId()));

        CircleImageView circleImageView1=helper.getView(R.id.civ_headImageView1);
        CircleImageView circleImageView2=helper.getView(R.id.civ_headImageView2);
//        //高斯模糊处理
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Bitmap womenBitmap = Glide.with(mContext)
//                            .load(R.mipmap.image5)
//                            .asBitmap() //必须
//                            .centerCrop()
//                            .into(500, 500)
//                            .get();
//                    BlurEntity blurEntity = new BlurEntity();
//                    blurEntity.bitmap = womenBitmap;
//                    blurEntity.imageView = circleImageView1;
//                    Message message = Message.obtain();
//                    message.what = SUCCESS;
//                    message.obj = blurEntity;
//                    handler.sendMessage(message);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Bitmap womenBitmap = Glide.with(mContext)
//                            .load(R.mipmap.image4)
//                            .asBitmap() //必须
//                            .centerCrop()
//                            .into(500, 500)
//                            .get();
//                    BlurEntity blurEntity = new BlurEntity();
//                    blurEntity.bitmap = womenBitmap;
//                    blurEntity.imageView = circleImageView2;
//                    Message message = Message.obtain();
//                    message.what = SUCCESS;
//                    message.obj = blurEntity;
//                    handler.sendMessage(message);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

		// 用户头像
		Glide.with(mContext).load(item.getUicon())
				.crossFade()
				.bitmapTransform(new BlurTransformation(mContext, 15))
				.into(circleImageView1);
		// 接受用户头像
		Glide.with(mContext).load(item.getFicon())
				.crossFade()
				.bitmapTransform(new BlurTransformation(mContext, 15))
				.into(circleImageView2);

		helper.setText(R.id.peopelNum, item.getZanNum() + "人点赞");
		helper.setText(R.id.time, "时长" + StringUtils.msToTime(item.getSc()));
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

	private class ItemClickListener implements View.OnClickListener{
		private String roomName;

		public ItemClickListener(String roomName) {
			this.roomName = roomName;
		}

		@Override
		public void onClick(View v) {
			check(roomName);
		}
	}

	/**
	 *  开始偷听接口
	 */
	private void check(String roomName) {
		Map<String, String> map = new HashMap<>();
		map.put("tid", roomName);
		map.put("user_id", DBUtils.getUserId());
		RetrofitTools.checkTouTing(map)
				.subscribe(new ResponseSubscriber<BaseResponse>() {
					@Override
					public void onSuccess(BaseResponse ruleResponse, int code, String msg) {
						if (code == 200) {
							//可以偷听  跳转至偷听页面
							NHuaLiaoTouTingActivity.start(mContext,roomName);
						} else {
							ToastUtil.showToast(mContext, msg);
						}
					}
					@Override
					public void onFailed(Throwable e) {
					}
				});
	}

    class BlurEntity {
        public Bitmap bitmap;
        public CircleImageView imageView;
    }

}
