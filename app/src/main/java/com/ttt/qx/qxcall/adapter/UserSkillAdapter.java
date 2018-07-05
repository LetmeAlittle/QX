package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.UserTypeSkillList;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 王亚东 on 2017/10/1.
 */

public class UserSkillAdapter extends RecyclerView.Adapter<UserSkillAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<UserTypeSkillList.DataBean> userTypeSkillListData;
    private final int _89859e;
    private final int _ffffff;
    private String authorization;
    //所有的标签 对于当前用户所选择的状态
    private HashMap<Integer, Boolean> allTagStatusMap = new HashMap<>();
    private final Drawable mRight_iv_drawable;
    private final Drawable del_white_bg_del;

    public UserSkillAdapter(Context context
            , List<UserTypeSkillList.DataBean> userTypeSkillListData
            , int member_cate_id, String authorization) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.userTypeSkillListData = new ArrayList<>();
        this.userTypeSkillListData.addAll(userTypeSkillListData);
        this.authorization = authorization;
        _89859e = context.getResources().getColor(R.color._89859e);
        _ffffff = context.getResources().getColor(R.color._ffffff);
        mRight_iv_drawable = context.getResources().getDrawable(R.mipmap.right_iv);
        del_white_bg_del = context.getResources().getDrawable(R.mipmap.del_white_bg_del);
        for (int i = 0; i < this.userTypeSkillListData.size(); i++) {//初始化选择状态
            UserTypeSkillList.DataBean dataBean = this.userTypeSkillListData.get(i);
            Integer id = dataBean.getId();
            //首先假设状态为false
            allTagStatusMap.put(id, false);
            if (dataBean.getId() == member_cate_id) {
                allTagStatusMap.put(id, true);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_user_tag, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //首先获取当前配置的tag
        UserTypeSkillList.DataBean dataBean = userTypeSkillListData.get(position);
        Integer id = dataBean.getId();
        String name = dataBean.getName();
        String color = dataBean.getColor();
//        holder.rl.setBackgroundColor(Color.parseColor(color));
        holder.tag_content_tv.setText(name);
        if (allTagStatusMap.get(id)) {
            holder.current_select_iv.setBackgroundResource(R.mipmap.xuan_yes_iv);
        } else {
            holder.current_select_iv.setBackgroundColor(context.getResources().getColor(R.color._0000));
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Integer integer : allTagStatusMap.keySet()) {
                    if (integer == id) {
                        allTagStatusMap.put(id, true);
                    } else {
                        allTagStatusMap.put(integer, false);
                    }
                }
                MineModel.getMineModel().setUserSkill(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse2>() {
                    @Override
                    public void onNext(StandardResponse2 response) {
                        if (response.getStatus_code() == 200) {
                            notifyDataSetChanged();
                        }
                    }
                }, context), String.valueOf(id), authorization);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userTypeSkillListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_content_tv)
        TextView tag_content_tv;
        @BindView(R.id.current_select_iv)
        ImageView current_select_iv;
        @BindView(R.id.rl)
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
