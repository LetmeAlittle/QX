package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttt.qx.qxcall.R;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;

import java.util.ArrayList;

public class QingQuTypeDialog extends Dialog {
    private OnTypeSelectListener listener;
    private Context context;
    private ArrayList<Child> list = new ArrayList<>();

    public QingQuTypeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(initView());
    }

    public void init() {
//        类型 6个 1老司机开车 2闺蜜私房话 3两性研究所 4剧本专区 5撩妹区 6撩汉区
        list.clear();
        list.add(new Child("1", "老司机开车"));
        list.add(new Child("2", "闺蜜私房话"));
        list.add(new Child("3", "两性研究所"));
        list.add(new Child("4", "剧本专区"));
        setCanceledOnTouchOutside(true);
        setContentView(initView());
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                Toast.makeText(context, "cancle", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void show(OnTypeSelectListener listener) {
        this.listener = listener;
        if (!isShowing()) {
            show();

            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = (int) SystemUtils.getScreenWidth(context);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    private View initView() {
        View view = View.inflate(context, R.layout.dialog_xiao_qing_qu_select_type, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        CommentAdapter adapter = new CommentAdapter(R.layout.item_qing_qu_list);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Child child = (Child) adapter.getItem(position);
                if (listener != null) {
                    listener.onSelected(child.typeId, child.name);
                }
                dismiss();
            }
        });
        adapter.setNewData(list);
        recyclerView.setAdapter(adapter);
    }

    public interface OnTypeSelectListener {
        void onSelected(String typeId, String name);
    }

    class Child {
        public Child(String typeId, String name) {
            this.typeId = typeId;
            this.name = name;
        }

        private String typeId;
        private String name;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 评论列表
     */
    private class CommentAdapter extends BaseQuickAdapter<Child, BaseViewHolder> {
        public CommentAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Child item) {
            helper.setText(R.id.typeName, StringUtils.convert(item.getName()));
        }
    }
}
