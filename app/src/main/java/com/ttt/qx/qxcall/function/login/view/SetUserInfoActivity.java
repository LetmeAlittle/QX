package com.ttt.qx.qxcall.function.login.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ttt.qx.qxcall.eventbus.SetUserInfoSuccess;
import com.ttt.qx.qxcall.function.base.interfacee.SubScribeOnNextListener;
import com.ttt.qx.qxcall.function.base.subscribe.ProgressSubscribe;
import com.ttt.qx.qxcall.function.login.model.MineModel;
import com.ttt.qx.qxcall.function.login.model.entity.User;
import com.ttt.qx.qxcall.function.register.model.entity.StandardResponse;
import com.ttt.qx.qxcall.function.start.BaseActivity;
import com.ttt.qx.qxcall.utils.CommonRegexUtil;
import com.ttt.qx.qxcall.utils.ToastUtil;
import com.ttt.qx.qxcall.widget.CustomDatePicker;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置用户基本信息 昵称、年龄、签名、生日等
 * Created by wyd on 2017/10/16.
 */
public class SetUserInfoActivity extends BaseActivity {
    //当前用户操作类别 根据不同的选择做出设置
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.content_et)
    EditText content_et;
    @BindView(R.id.content_clear_iv)
    ImageView content_clear_iv;
    //记录当前用户要修改的类别。
    private String mMark;
    //类别上次值
    private String content;
    public static final String NICK_NAME = "NICK_NAME";
    public static final String AGE = "AGE";
    public static final String SEX = "SEX";
    public static final String SIGNATURE = "SIGNATURE";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String LABEL = "LABEL";
    private MineModel mMineModel;
    private String setResultContent;
    private String Authorization;
    //日期选择控件
    private CustomDatePicker customDatePicker;
    private String currentDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.top_back_rl, R.id.finish_tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.top_back_rl:
                finish();
                break;
            case R.id.finish_tv:
                setResultContent = content_et.getText().toString().trim();
                //根据当前类别 进行不同正则的判断
                switch (mMark) {
                    case NICK_NAME:
                        if (!setResultContent.equals("")) {
                            mMineModel.setUserNickName(mProgressSubscribe, setResultContent, Authorization);
                        } else {
                            onToast("昵称不能为空！");
                        }
                        break;
                    case SIGNATURE:
                        if (!setResultContent.equals("")) {
                            mMineModel.setMemberSignature(mProgressSubscribe, setResultContent, Authorization);
                        } else {
                            onToast("签名不能为空！");
                        }
                        break;
                    case BIRTHDAY:
                        if (!setResultContent.equals("")) {
                            if (setResultContent.matches(CommonRegexUtil.dateReg)) {
                                mMineModel.setBirthday(mProgressSubscribe, setResultContent, Authorization);
                            } else {
                                onToast("生日格式不正确！");
                            }
                        } else {
                            onToast("生日不能为空！");
                        }
                        break;
                }
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMark = getIntent().getStringExtra("mark");
        content = getIntent().getStringExtra("content");
        mMineModel = MineModel.getMineModel();
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.queryFirstData();
        String token = userBean.getToken();
        Authorization = "Bearer " + token;
    }

    /**
     * 初始化view
     */
    private void initView() {
        //根据当前类别设置title
        switch (mMark) {
            case NICK_NAME:
                title_tv.setText("修改昵称");
                break;
            case AGE:
                title_tv.setText("修改年龄");
                content_et.setHint("请输入年龄");
                break;
            case SIGNATURE:
                title_tv.setText("修改签名");
                content_et.setHint("请输入签名");
                break;
            case BIRTHDAY:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                String now = sdf.format(new Date());
                currentDate = now.split(" ")[0];
                customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) { // 回调接口，获得选中的时间
                        content_et.setText(time.split(" ")[0]);
                    }
                }, "0010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                customDatePicker.showSpecificTime(false); // 不显示时和分
                customDatePicker.setIsLoop(false); // 不允许循环滚动
                title_tv.setText("修改生日");
                content_et.setHint("点击选择生日");
                content_et.setFocusable(false);
                content_et.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDatePicker.show(currentDate);
                    }
                });
                break;
        }
        //设置原始值
        content_et.setText(content);
        content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = content_et.getText().toString().trim();
                if (!mMark.equals(BIRTHDAY)) {
                    if (content.equals("")) {
                        content_clear_iv.setVisibility(View.INVISIBLE);
                    } else {
                        content_clear_iv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * 响应处理
     */
    private ProgressSubscribe mProgressSubscribe = new ProgressSubscribe<>(new SubScribeOnNextListener<StandardResponse>() {
        @Override
        public void onNext(StandardResponse response) {
            if (response.getStatus_code() == 200) {//响应成功通知页面刷新
                SetUserInfoSuccess setUserInfoSuccess = new SetUserInfoSuccess();
                setUserInfoSuccess.content = setResultContent;
                EventBus.getDefault().post(setUserInfoSuccess);
                finish();
            }
        }
    }, this);

    private void errorMessageShow(User user) {
        Object message = user.getErrorMessage();
        if (message != null) {
            onToast(message.toString());
        }
    }

    public void onToast(String message) {
        //消息弹出
        ToastUtil.show(this, message, Toast.LENGTH_SHORT);
    }

    public void onFinish() {
        //销毁当前
        finish();
    }
}
