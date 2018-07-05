package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.TipDialog;
import com.ttt.qx.qxcall.eventbus.CoinNoEnough;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.listen.model.StealListenModel;
import com.ttt.qx.qxcall.function.listen.model.entity.StealListenList;
import com.ttt.qx.qxcall.function.listen.view.StealListenDetailActivity;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.RechargeActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.ImageUtil;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.widget.FlowLayout;
import com.ttt.qx.qxcall.widget.RadiusBackgroundSpan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ttt.qx.qxcall.QXCallApplication.login;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class ListenCategoryAdapter extends RecyclerView.Adapter<ListenCategoryAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    //是不是第一个条目标记
    private final String category;
    private List<StealListenList.DataBean.ListBean> listBeen = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    private int _ffffff;
    private float tagSize;

    public ListenCategoryAdapter(Context context, String category, List<StealListenList.DataBean.ListBean> listBeen) {
        this.context = context;
        this.category = category;
        this.listBeen.addAll(listBeen);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
        _ffffff = context.getResources().getColor(R.color._ffffff);
        tagSize = context.getResources().getInteger(R.integer.fm_anim_i12);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void setListBeen(List<StealListenList.DataBean.ListBean> listBeen) {
        this.listBeen.clear();
        this.listBeen.addAll(listBeen);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_listen_category, null, false));
    }

    @Subscribe
    public void onEventCoinNoEnough(CoinNoEnough coinNoEnough) {
        //偷听费用不足 弹出是否充值对话框
        TipDialog.showCenterTipDialog(context, "当前剩余钻石" + coinNoEnough.detainCoin + ",偷听钻石不足,是否前去充值？", new TipDialog.OnComponentClickListener() {
            @Override
            public void onCancle() {
                //用户取消操作
            }

            @Override
            public void onConfirm() {
                //用户点击确定执行 相关逻辑
                IntentUtil.jumpIntent(context, RechargeActivity.class);
            }
        }, true);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StealListenList.DataBean.ListBean listBean = listBeen.get(position);
        //获取拨打方对象
        StealListenList.DataBean.ListBean.CallMemberBean call_member = listBean.getCall_member();
        //获取回复方
        StealListenList.DataBean.ListBean.ReplyMemberBean reply_member = listBean.getReply_member();
//        holder.member_type1_tv.setText(category);
        holder.item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login) {
                    final int id = listBean.getId();
                    UserDao userDao = new UserDao();
                    UserBean userBean = userDao.queryFirstData();
                    //首先判断用户是否可以偷听 偷听费用是否满足
                    StealListenModel.getStealListenModel().stealListenDeduction(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse standardResponse) {
                            if (standardResponse.getStatus_code() == 200) {
                                StandardResponse.DataBean data = standardResponse.getData();
                                if (data.getIs_allow() == 1) {
                                    Intent intent = new Intent(context, StealListenDetailActivity.class);
                                    //偷听id
                                    intent.putExtra("id", id);
                                    intent.putExtra("memberAccount", data.getMember_account());
                                    context.startActivity(intent);
                                } else {
                                    //偷听费用不足 弹出是否充值对话框
                                    TipDialog.showCenterTipDialog(context, "当前剩余钻石" + data.getMember_account() + ",偷听钻石不足,是否前去充值？", new TipDialog.OnComponentClickListener() {
                                        @Override
                                        public void onCancle() {
                                            //用户取消操作
                                        }

                                        @Override
                                        public void onConfirm() {
                                            //用户点击确定执行 相关逻辑
                                            IntentUtil.jumpIntent(context, RechargeActivity.class);
                                        }
                                    }, true);
                                }

                            } else {

                            }
                        }
                    }, context), String.valueOf(id), "Bearer " + userBean.getToken());
                } else {
                    IntentUtil.jumpIntent(context, LoginTransferActivity.class);
                }
            }
        });
        //高斯模糊处理
        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap manBitmap = Glide.with(context)
                            .load(call_member.getMember_avatar())
                            .asBitmap() //必须
                            .centerCrop()
                            .into(500, 500)
                            .get();
                    BlurEntity blurEntity = new BlurEntity();
                    blurEntity.bitmap = manBitmap;
                    blurEntity.imageView = holder.man_call_iv;
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
                    Bitmap womenBitmap = Glide.with(context)
                            .load(reply_member.getMember_avatar())
                            .asBitmap() //必须
                            .centerCrop()
                            .into(500, 500)
                            .get();
                    BlurEntity blurEntity = new BlurEntity();
                    blurEntity.bitmap = womenBitmap;
                    blurEntity.imageView = holder.women_call_iv;
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
        holder.zan_num_tv.setText(String.valueOf(listBean.getZan_num()));
        holder.steal_listen_time_tv.setText(simpleDateFormat.format(new Date(1000 * Integer.parseInt(listBean.getDuration()))));
        //偷听标签处理
        List<StealListenList.DataBean.ListBean.TagListBean> tag_list = listBean.getTag_list();
        if (tag_list != null && tag_list.size() > 0) {
            holder.flow_tag_layout.removeAllViews();
            for (StealListenList.DataBean.ListBean.TagListBean tagListBean : tag_list) {
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 35;
                TextView textView = new TextView(context);
                textView.setTextColor(_ffffff);
                textView.setTextSize(tagSize);
//                textView.setText();
                textView.setLayoutParams(layoutParams);
                String text = tagListBean.getText();
                SpannableString spanString = new SpannableString(text);
                spanString.setSpan(new RadiusBackgroundSpan(
                                Color.parseColor(tagListBean.getColor()), 10)
                        , 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                textView.append(spanString);
                holder.flow_tag_layout.addView(textView);
            }
        } else {
            holder.flow_tag_layout.removeAllViews();
        }
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
                        blurEntity.imageView.setImageBitmap(ImageUtil.blurBitmap(context
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

    @Override
    public int getItemCount() {
        return listBeen.size();
    }

    /**
     * @param listBeen
     */
    public void addList(List<StealListenList.DataBean.ListBean> listBeen) {
        this.listBeen.addAll(listBeen);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_ll)
        LinearLayout item_ll;
        @BindView(R.id.show_view)
        View show_view;
        @BindView(R.id.flow_tag_layout)
        FlowLayout flow_tag_layout;
        @BindView(R.id.man_call_iv)
        CircleImageView man_call_iv;
        @BindView(R.id.women_call_iv)
        CircleImageView women_call_iv;

        @BindView(R.id.zan_num_tv)
        TextView zan_num_tv;
        @BindView(R.id.zan_iv)
        ImageView zan_iv;

        @BindView(R.id.steal_listen_time_tv)
        TextView steal_listen_time_tv;
        @BindView(R.id.steal_listen_iv)
        ImageView steal_listen_iv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
