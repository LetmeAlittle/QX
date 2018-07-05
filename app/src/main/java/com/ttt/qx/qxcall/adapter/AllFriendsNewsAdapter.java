package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.dialog.GiftSendDialog;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.find.model.FindModel;
import com.ttt.qx.qxcall.function.find.model.entity.DynamicResponse;
import com.ttt.qx.qxcall.function.find.model.entity.GiftList;
import com.ttt.qx.qxcall.function.find.view.PostDeatailActivity;
import com.ttt.qx.qxcall.function.home.view.MainActivity;
import com.ttt.qx.qxcall.function.login.view.LoginTransferActivity;
import com.ttt.qx.qxcall.function.login.view.UserMainActivity;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.IntentUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ttt.qx.qxcall.QXCallApplication.login;
import static com.ttt.qx.qxcall.QXCallApplication.onToast;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class AllFriendsNewsAdapter extends RecyclerView.Adapter<AllFriendsNewsAdapter.ViewHolder> {
    private final Context context;
    private List<DynamicResponse.DataBean.ListBean> listBeen = new ArrayList<>();
    private final LayoutInflater inflater;
    private final int i16;
    private Map<Integer, Boolean> zanMap = new HashMap<>();
    private final String authorization;

    public AllFriendsNewsAdapter(Context context, List<DynamicResponse.DataBean.ListBean> listBeen) {
        this.context = context;
        this.listBeen.addAll(listBeen);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        authorization = "Bearer " + userBean.getToken();
        for (DynamicResponse.DataBean.ListBean listBean : listBeen) {
            if (listBean.getIs_zan() == 0) {
                zanMap.put(listBean.getId(), false);
            } else {
                zanMap.put(listBean.getId(), true);
            }
        }
    }

    @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_all_friends_news, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DynamicResponse.DataBean.ListBean listBean = listBeen.get(position);
        int say_id = listBean.getId();
        holder.comment_ll_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCommentDetail(listBean);
            }
        });
        //头像进主页
        holder.user_head_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserMainActivity.class);
                intent.putExtra("id", listBean.getMember_id());//用户id
                intent.putExtra("accid", listBean.getMember_id());
                context.startActivity(intent);
            }
        });
        holder.comment_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCommentDetail(listBean);
            }
        });
        //设置控件
        holder.publish_content_tv.setText(listBean.getContent());
        Glide.with(context)
                .load(listBean.getMember_avatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.user_head_iv);
        holder.user_address_tv.setText(listBean.getMember_area());
        holder.user_nick_name_tv.setText(listBean.getNick_name());
        holder.browse_num_tv.setText(String.valueOf(listBean.getClick_num()) + "次");
        holder.publish_time_tv.setText(listBean.getCreate_at());
        holder.comment_num_tv.setText(String.valueOf(listBean.getReply_num()));
        holder.gift_num_tv.setText(String.valueOf(listBean.getGift_num()));
        holder.zan_num_tv.setText(String.valueOf(listBean.getZan_num()));
        if (listBean.getIs_zan() == 0) {
            holder.zan_iv.setBackgroundResource(R.mipmap.wei_zan_iv);
        } else {
            holder.zan_iv.setBackgroundResource(R.mipmap.yi_zan_iv);
        }
        holder.zan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login) {
                    Boolean aBoolean = zanMap.get(listBean.getId());
                    if (null != aBoolean && aBoolean) {
                        ToastUtil.show(context, "已点过赞！", Toast.LENGTH_SHORT);
                    } else {
                        FindModel.getFindModel().callDianZan(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                            @Override
                            public void onNext(StandardResponse standardResponse) {
                                if (standardResponse.getStatus_code() == 200) {
                                    int zan_num = listBean.getZan_num();
                                    zan_num++;
                                    zanMap.put(listBean.getId(), true);
                                    holder.zan_num_tv.setText(String.valueOf(zan_num));
                                    holder.zan_iv.setBackgroundResource(R.mipmap.yi_zan_iv);
                                }
                                onToast(standardResponse.getMessage());
                            }
                        }, context), String.valueOf(listBean.getId()), authorization);
                    }
                } else {
                    IntentUtil.jumpIntent(context, LoginTransferActivity.class);
                }
            }
        });
        holder.gift_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login) {
                    FindModel.getFindModel().getGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<GiftList>() {
                        @Override
                        public void onNext(GiftList giftList) {
                            if (giftList.getStatus_code() == 200) {
                                GiftSendDialog.showBottomDialog((MainActivity) context, giftList.getData(), new GiftSendDialog.OnComponentClickListener() {
                                    @Override
                                    public void onCancle() {
                                    }

                                    @Override
                                    public void onSend(String gift_id) {
                                        FindModel.getFindModel().sendGiftList(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                                            @Override
                                            public void onNext(StandardResponse response) {
                                                if (response.getStatus_code() == 200) {
                                                    onToast("赠送成功！");
                                                } else {
                                                    onToast(response.getMessage());
                                                }
                                            }
                                        }, context), gift_id, String.valueOf(say_id), authorization);
                                    }
                                });
                            } else {
                                onToast(giftList.getMessage());
                            }
                        }
                    }, context));
                } else {
                    IntentUtil.jumpIntent(context, LoginTransferActivity.class);
                }
            }
        });
//        //设置 说说图片
        List<String> img_list = listBean.getImg_list();
        GridLayoutManager gridLayoutManager;
        if (img_list != null && img_list.size() > 0) {
            FindPicAdapter findPicAdapter;
            if (img_list.size() == 1) {
                gridLayoutManager = new GridLayoutManager(context, 1);
                findPicAdapter = new FindPicAdapter(context, img_list, "MainActivity", 1);
            } else if (img_list.size() == 2) {
                gridLayoutManager = new GridLayoutManager(context, 2);
                findPicAdapter = new FindPicAdapter(context, img_list, "MainActivity", 2);
            } else {
                gridLayoutManager = new GridLayoutManager(context, 3);
                findPicAdapter = new FindPicAdapter(context, img_list, "MainActivity", 3);
            }
            holder.public_pic_rv.setLayoutManager(gridLayoutManager);
            holder.public_pic_rv.setAdapter(findPicAdapter);
        }
//        //回复评论显示
        List<DynamicResponse.DataBean.ListBean.ReplyListBean> reply_list = listBean.getReply_list();
        if (reply_list != null && reply_list.size() > 0) {
            holder.comment_replay_ll.setVisibility(View.VISIBLE);
            holder.comment_replay_rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            DynamicReplayAdapter dynamicReplayAdapter = new DynamicReplayAdapter(context, reply_list);
            holder.comment_replay_rv.setAdapter(dynamicReplayAdapter);
            dynamicReplayAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enterCommentDetail(listBean);
                }
            });
        } else {
            holder.comment_replay_ll.setVisibility(View.GONE);
        }
    }

    /**
     * 点击进入详情页
     *
     * @param listBean
     */
    private void enterCommentDetail(DynamicResponse.DataBean.ListBean listBean) {
        if (login) {
            Intent intent = new Intent(context, PostDeatailActivity.class);
            intent.putExtra("id", listBean.getId());
            context.startActivity(intent);
        } else {
            IntentUtil.jumpIntent(context, LoginTransferActivity.class);
        }
    }

    @Override
    public int getItemCount() {
        return listBeen.size();
    }

    public void addList(List<DynamicResponse.DataBean.ListBean> listBeen) {
        this.listBeen.addAll(listBeen);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.publish_content_tv)
        TextView publish_content_tv;
        @BindView(R.id.user_head_iv)
        CircleImageView user_head_iv;
        @BindView(R.id.user_nick_name_tv)
        TextView user_nick_name_tv;
        @BindView(R.id.publish_time_tv)
        TextView publish_time_tv;
        @BindView(R.id.user_address_tv)
        TextView user_address_tv;
        @BindView(R.id.browse_num_tv)
        TextView browse_num_tv;
        @BindView(R.id.comment_num_tv)
        TextView comment_num_tv;
        @BindView(R.id.gift_num_tv)
        TextView gift_num_tv;
        @BindView(R.id.zan_num_tv)
        TextView zan_num_tv;
        @BindView(R.id.zan_iv)
        ImageView zan_iv;
        @BindView(R.id.comment_replay_ll)
        LinearLayout comment_replay_ll;
        @BindView(R.id.comment_ll_click)
        LinearLayout comment_ll_click;
        @BindView(R.id.zan_ll)
        LinearLayout zan_ll;
        @BindView(R.id.gift_ll)
        LinearLayout gift_ll;
        @BindView(R.id.public_pic_rv)
        RecyclerView public_pic_rv;
        @BindView(R.id.comment_ll)
        LinearLayout comment_ll;
        @BindView(R.id.comment_replay_rv)
        RecyclerView comment_replay_rv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
