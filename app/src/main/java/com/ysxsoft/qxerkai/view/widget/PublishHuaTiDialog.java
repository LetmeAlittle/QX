package com.ysxsoft.qxerkai.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.common.util.string.StringUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.function.home.model.HomeModel;
import com.ttt.qx.qxcall.function.home.model.entity.UserDetailInfo;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ysxsoft.qxerkai.net.ResponseSubscriber;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.BaseResponse;
import com.ysxsoft.qxerkai.net.response.GetNoticeListResponse;
import com.ysxsoft.qxerkai.utils.DBUtils;
import com.ysxsoft.qxerkai.utils.StringUtils;
import com.ysxsoft.qxerkai.utils.SystemUtils;
import com.ysxsoft.qxerkai.view.activity.NPaoHaTiActivity;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Subscriber;

/**
 * 抛话题  发布话题dialog
 */
public class PublishHuaTiDialog extends Dialog {
    private Context context;
    private OnHuaTiDialogListener listener;
    private List<String> randomList = new ArrayList<>();
    private List<String> randomNumList = new ArrayList<>();
    private EditText content;

    public PublishHuaTiDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(initView());
        getUserInfo();
        getRandomContent();//获取随机话题
    }

    private void init() {
        for (int i = 7; i <= 99; i++) {
            randomNumList.add(i + "");
        }
        setCanceledOnTouchOutside(true);
        setContentView(initView());
    }

    public void show(OnHuaTiDialogListener listener) {
        this.listener = listener;
        if (!isShowing()) {
            show();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = (int) SystemUtils.getScreenWidth(context) * 6 / 7;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            getWindow().setGravity(Gravity.CENTER);
        }
    }

    StringScrollPicker picker;
    TextView price;

    private View initView() {
        View view = View.inflate(context, R.layout.dialog_publish_hua_ti, null);
        ImageView cancel = (ImageView) view.findViewById(R.id.cancel);//取消按钮
        content = (EditText) view.findViewById(R.id.content);//话题内容
        picker = (StringScrollPicker) view.findViewById(R.id.picker);
        price= (TextView) view.findViewById(R.id.price);
        picker.setData(randomNumList);

        TextView update = (TextView) view.findViewById(R.id.updateTitle);//随机话题
        TextView submit = (TextView) view.findViewById(R.id.submit);//发布话题
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(randomList.size());
                if (randomList.size() > randomIndex) {
                    content.setText(randomList.get(randomIndex));
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(content.getText().toString())) {
                    ToastUtil.showToast(context, "话题不能为空！");
                    return;
                }
                addHuaTi(picker.getSelectedItem().toString(), content.getText().toString());
            }
        });
        return view;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 网络请求
    ///////////////////////////////////////////////////////////////////////////
    private void getRandomContent() {//获取随机话题
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "1");//	0公告 1随机话题 2老司机开车 3闺蜜私房语4两性研究社
        RetrofitTools.getNoticeList(map)
                .subscribe(new ResponseSubscriber<GetNoticeListResponse>() {
                    @Override
                    public void onSuccess(GetNoticeListResponse getNoticeListResponse, int code, String msg) {
                        if (code == 200) {
                            if (getNoticeListResponse != null && getNoticeListResponse.getData() != null && getNoticeListResponse.getData().getData() != null) {
                                List<GetNoticeListResponse.DataBeanX.DataBean> list = getNoticeListResponse.getData().getData();
                                if (list == null) {
                                    return;
                                }
                                randomList.clear();
                                for (int i = 0; i < list.size(); i++) {
                                    randomList.add(StringUtils.convert(list.get(i).getTitle()));
                                }
                            }
                        } else {
                            ToastUtil.showToast(context, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                    }
                });
    }

    /**
     * 发表话题
     *
     * @param num   砰砰豆
     * @param title 标题
     */
    private void addHuaTi(String num, String title) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", DBUtils.getUserId());
        map.put("num", num);//价格
        map.put("title", title);//话题

        RetrofitTools.addHuaTi(map)
                .subscribe(new ResponseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse ruleResponse, int code, String msg) {
                        if (code == 200) {
                            closeDialog();
                            if (listener != null) {
                                listener.publish(title, picker.getSelectedItem().toString());
                            }
                        } else {
                            ToastUtil.showToast(context, msg);
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {
                    }
                });
    }

    private void closeDialog() {
        dismiss();
    }

    public interface OnHuaTiDialogListener {
        /**
         * 发布话题
         *
         * @param title
         */
        void publish(String title, String selectedContent);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 获取个人信息
    ///////////////////////////////////////////////////////////////////////////
    private void getUserInfo() {
        String authorization = "Bearer " + DBUtils.getUserToken();
        //初始化金币数
        HomeModel.getHomeModel().getUserInfo(new Subscriber<UserDetailInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(UserDetailInfo userDetailInfo) {
                if (userDetailInfo.getStatus_code() == 200) {
                    price.setText("抛一次话题收取"+StringUtils.convert(""+userDetailInfo.getData().getPaohuati())+"砰砰豆");
                }
            }
        }, "", authorization);
    }
}
