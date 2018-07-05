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
import com.ttt.qx.qxcall.eventbus.NotifyUserInfoLabel;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.home.model.entity.MemberTagBean;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.UserTagListResponse;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 王亚东 on 2017/10/1.
 */

public class UserTagAdapter extends RecyclerView.Adapter<UserTagAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<UserTagListResponse.DataBean> userTagListResponseData;
    private final int _89859e;
    private final int _ffffff;
    private String authorization;
    //所有的标签 对于当前用户所选择的状态
    private HashMap<Integer, Boolean> allTagStatusMap = new HashMap<>();
    //当前用户所拥有的标签Map
    private HashMap<Integer, Boolean> currentSelectTagsMap = new HashMap<>();
    private HashMap<Integer, UserTagListResponse.DataBean> tagBeansMap = new HashMap<>();
    private final Drawable mRight_iv_drawable;
    private final Drawable del_white_bg_del;

    public UserTagAdapter(Context context, List<UserTagListResponse.DataBean> userTagListResponseData, List<UserDetailInfo.DataBean.MemberTagBean> member_tag, String authorization) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.userTagListResponseData = userTagListResponseData;
        if (this.userTagListResponseData == null) {
            this.userTagListResponseData = new ArrayList<>();
        }
        this.authorization = authorization;
        _89859e = context.getResources().getColor(R.color._89859e);
        _ffffff = context.getResources().getColor(R.color._ffffff);
        mRight_iv_drawable = context.getResources().getDrawable(R.mipmap.right_iv);
        del_white_bg_del = context.getResources().getDrawable(R.mipmap.del_white_bg_del);
        for (int i = 0; i < this.userTagListResponseData.size(); i++) {//初始化选择状态
            UserTagListResponse.DataBean dataBean = this.userTagListResponseData.get(i);
            Integer id = dataBean.getId();
            //首先假设状态为false
            allTagStatusMap.put(id, false);
            tagBeansMap.put(id, dataBean);
            for (int j = 0; j < member_tag.size(); j++) {
                UserDetailInfo.DataBean.MemberTagBean memberTagBean = member_tag.get(j);
                if (id == memberTagBean.getId()) {
                    allTagStatusMap.put(id, true);
                    currentSelectTagsMap.put(id, true);
                    break;
                }
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
        UserTagListResponse.DataBean dataBean = userTagListResponseData.get(position);
        Integer id = dataBean.getId();
        String tag = dataBean.getText();
        String color = dataBean.getColor();
//        holder.rl.setBackgroundColor(Color.parseColor(color));
        holder.tag_content_tv.setText(tag);
        if (allTagStatusMap.get(id)) {
            holder.current_select_iv.setBackgroundResource(R.mipmap.xuan_yes_iv);
        } else {
            holder.current_select_iv.setBackgroundResource(R.mipmap.xuan_no_iv);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allTagStatusMap.get(id)) {
                    allTagStatusMap.put(id, false);
                    if (currentSelectTagsMap.containsKey(id)) {
                        currentSelectTagsMap.remove(id);
                    }
                } else {
                    allTagStatusMap.put(id, true);
                    currentSelectTagsMap.put(id, true);
                }
                //刷新对象创建
                NotifyUserInfoLabel notifyUserInfoLabel = new NotifyUserInfoLabel();
                //遍历map 组装json数组请求
                JSONArray tagArray = new JSONArray();
                for (Integer key : currentSelectTagsMap.keySet()) {
                    tagArray.put(key);
                    UserTagListResponse.DataBean dataBean1 = tagBeansMap.get(key);
                    MemberTagBean memberTagBean = new MemberTagBean();
                    memberTagBean.setId(key);
                    memberTagBean.setColor(dataBean1.getColor());
                    memberTagBean.setText(dataBean1.getText());
                    notifyUserInfoLabel.tags.add(memberTagBean);
                }
                MineModel.getMineModel().setTag(new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
                    @Override
                    public void onNext(StandardResponse response) {
                        if (response.getStatus_code() == 200) {
                            notifyDataSetChanged();
                            //通知用户信息页 标签刷新
                            EventBus.getDefault().post(notifyUserInfoLabel);
                        }
                    }
                }, context), tagArray.toString(), authorization);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userTagListResponseData.size();
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
