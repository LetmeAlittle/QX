package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.BlacksList;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.widget.ZQImageViewRoundOval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class BlackListAdapter extends RecyclerView.Adapter<BlackListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    private List<BlacksList.DataBeanX.DataBean> dataBeanList = new ArrayList<>();
    private final UserBean mUserBean;
    private final String mAuthorization;

    public BlackListAdapter(Context context, List<BlacksList.DataBeanX.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList.addAll(dataBeanList);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        mAuthorization = "Bearer " + mUserBean.getToken();
    }

    public void addList(List<BlacksList.DataBeanX.DataBean> dataBeanList) {
        this.dataBeanList.addAll(dataBeanList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_black_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BlacksList.DataBeanX.DataBean bean = dataBeanList.get(position);
        if (bean != null) {
            //设置图片类型为圆角矩形
            holder.user_head_icon_iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
            //设置圆角矩形圆弧半径
            holder.user_head_icon_iv.setRoundRadius(i16);
            BlacksList.DataBeanX.DataBean.MemberInfoBean member_info = bean.getMember_info();
            if (null != member_info) {
                Glide.clear(holder.user_head_icon_iv);
                Glide.with(context)
                        .load(member_info.getAvatar())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.user_head_icon_iv);
            } else {
                Glide.clear(holder.user_head_icon_iv);
                holder.user_head_icon_iv.setBackgroundResource(R.mipmap.nim_avatar_default);
            }
            holder.user_nick_name_tv.setText(member_info.getNick_name());
            holder.signature_tv.setText(member_info.getMember_signature());
            holder.cancel_black_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MineModel.getMineModel().postCancelBlack(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                        @Override
                        public void onNext(StandardResponse response) throws IOException {
                            if (response.getStatus_code() == 200) {
                                List<BlacksList.DataBeanX.DataBean> tempt = new ArrayList<BlacksList.DataBeanX.DataBean>();
                                for (int i = 0; i < dataBeanList.size(); i++) {
                                    BlacksList.DataBeanX.DataBean dataBean = dataBeanList.get(i);
                                    if (i != position) {
                                        tempt.add(dataBean);
                                    }
                                }
                                dataBeanList.clear();
                                dataBeanList.addAll(tempt);
                                notifyDataSetChanged();
                            }
                            ToastUtil.show(context, response.getMessage(), Toast.LENGTH_SHORT);
                        }
                    }, context), String.valueOf(bean.getMember_id()), mAuthorization);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_head_icon_iv)
        ZQImageViewRoundOval user_head_icon_iv;
        @BindView(R.id.cancel_black_tv)
        TextView cancel_black_tv;
        @BindView(R.id.user_nick_name_tv)
        TextView user_nick_name_tv;
        @BindView(R.id.signature_tv)
        TextView signature_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
