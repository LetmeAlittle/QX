package com.ttt.qx.qxcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.function.login.model.entity.MentionRecordList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 王亚东 on 2017/10/1.
 */

public class MentionRecordListAdapter extends RecyclerView.Adapter<MentionRecordListAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final int i16;
    List<MentionRecordList.DataBeanX.DataBean> dataBeanList = new ArrayList<>();
    private final UserBean mUserBean;
    private final String mAuthorization;

    public MentionRecordListAdapter(Context context, List<MentionRecordList.DataBeanX.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList.addAll(dataBeanList);
        inflater = LayoutInflater.from(context);
        i16 = context.getResources().getInteger(R.integer.i16);
        UserDao userDao = new UserDao();
        mUserBean = userDao.queryFirstData();
        mAuthorization = "Bearer " + mUserBean.getToken();
    }

    public void addList(List<MentionRecordList.DataBeanX.DataBean> dataBeanList) {
        this.dataBeanList.addAll(dataBeanList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_mention_record_list, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MentionRecordList.DataBeanX.DataBean bean = dataBeanList.get(position);
        if (bean != null) {
            holder.mention_money_tv.setText("提现额度：" + String.valueOf(bean.getAmount()) + "元");
            holder.create_time_tv.setText(bean.getCreate_at());
            holder.handle_status_tv.setText(bean.getState());
        }
    }


    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mention_money_tv)
        TextView mention_money_tv;
        @BindView(R.id.handle_status_tv)
        TextView handle_status_tv;
        @BindView(R.id.create_time_tv)
        TextView create_time_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
