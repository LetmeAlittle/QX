package com.ysxsoft.qxerkai.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.ttt.qx.qxcall.R;
import com.ttt.qx.qxcall.database.UserDao;
import com.ttt.qx.qxcall.dbbean.UserBean;
import com.ysxsoft.qxerkai.net.RetrofitTools;
import com.ysxsoft.qxerkai.net.response.GetQuestionRespose;
import com.ysxsoft.qxerkai.utils.LogUtils;
import com.ysxsoft.qxerkai.view.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * 专属匹配
 */
public class NZhuanShuPiPeiActivity extends NBaseActivity {

    public static final int UPDATE_QUESTION = 0;

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_public_titlebar_left_1)
    ImageView ivPublicTitlebarLeft1;
    @BindView(R.id.ll_public_titlebar_left)
    LinearLayout llPublicTitlebarLeft;
    @BindView(R.id.tv_public_titlebar_center)
    TextView tvPublicTitlebarCenter;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.iv_pic)
    ImageView ivTou;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.btn_l)
    TextView btnL;
    @BindView(R.id.btn_r)
    TextView btnR;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.tv_question_num)
    TextView tvQuestionNum;
    @BindView(R.id.rb11)
    RadioButton rb0;
    @BindView(R.id.rb12)
    RadioButton rb1;
    @BindView(R.id.rb13)
    RadioButton rb2;
    @BindView(R.id.rb14)
    RadioButton rb3;

    private int curPosition = 0;
    private List<GetQuestionRespose.DataBean> questionList;
    private GetQuestionRespose.DataBean dataBean;

    private RadioButton[] radioButtons;

    private int[] answers;//答案的id
    private int[] answersSelectedPos;//答案的id
    private int size;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_QUESTION:
                    if (size >= 0) {
                        dataBean = questionList.get(curPosition);
                        tvQuestionNum.setText("请回答以下问题（" + (curPosition + 1) + "/4）");
                        tvQuestion.setText(dataBean.getName());

                        answerList = dataBean.getList();
                        rb0.setText(answerList.get(0).getTitle());
                        rb1.setText(answerList.get(1).getTitle());
                        rb2.setText(answerList.get(2).getTitle());
                        rb3.setText(answerList.get(3).getTitle());

                        int answersSelectedPo = answersSelectedPos[curPosition];
                        radioButtons[answersSelectedPo].setChecked(true);

                        if (curPosition == 0) {//第一题
                            btnL.setVisibility(View.GONE);
                            btnR.setVisibility(View.VISIBLE);
                        } else if (curPosition > 0 && curPosition < (size - 1)) {
                            btnL.setVisibility(View.VISIBLE);
                            btnR.setVisibility(View.VISIBLE);
                        } else if (curPosition == size - 1) {//最后一题
                            btnL.setVisibility(View.VISIBLE);
                            btnR.setVisibility(View.VISIBLE);
                            btnR.setText("点击匹配");
                        }
                    }
                    break;
            }
        }
    };
    private List<GetQuestionRespose.DataBean.ListBean> answerList;
    private UserBean userBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nzhuan_shu_pi_pei);
        ButterKnife.bind(this);
        initStatusBar();
        initStatusBar(statusBar);
        initTitleBar();
        initView();
        initData();
        setListener();
    }


    private void initTitleBar() {
        ivPublicTitlebarLeft1.setVisibility(View.VISIBLE);
        ivPublicTitlebarLeft1.setImageResource(R.mipmap.back_left_white);
        llPublicTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublicTitlebarCenter.setText("专属匹配");
    }

    private void initView() {
        radioButtons = new RadioButton[]{rb0,rb1,rb2,rb3};

        UserDao userDao = new UserDao();
        userBean = userDao.queryFirstData();
        if (userBean != null) {
            tvNickname.setText(userBean.getNick_name());
            Glide.with(this)
                    .load(userBean.getMember_avatar())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into((ImageView) ivTou);
        }
    }

    private void initData() {
        questionList = new ArrayList<>();
        answers = new int[0];
        getData();
    }

    private void getData() {
        multipleStatusView.showLoading();
        RetrofitTools.getQuestionList().subscribe(new Observer<GetQuestionRespose>() {
            @Override
            public void onCompleted() {
                multipleStatusView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                multipleStatusView.hideLoading();
                LogUtils.e(e.toString());
            }

            @Override
            public void onNext(GetQuestionRespose getQuestionRespose) {
                if (getQuestionRespose.status_code == 200) {
                    questionList = getQuestionRespose.getData();
                    size = questionList.size();

                    answers = new int[size];
                    answersSelectedPos = new int[size];

                    for (int i = 0; i < size; i++) {
                        answers[i] = Integer.parseInt(questionList.get(i).getList().get(0).getId());
                        answersSelectedPos[i] = 0;
                    }

                    handler.sendEmptyMessage(UPDATE_QUESTION);
                }
            }
        });

    }

    private void setListener() {
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb11:
                        answers[curPosition] = Integer.parseInt(answerList.get(0).getId());
                        answersSelectedPos[curPosition] = 0;
                        break;
                    case R.id.rb12:
                        answers[curPosition] = Integer.parseInt(answerList.get(1).getId());
                        answersSelectedPos[curPosition] = 1;
                        break;
                    case R.id.rb13:
                        answers[curPosition] = Integer.parseInt(answerList.get(2).getId());
                        answersSelectedPos[curPosition] = 2;
                        break;
                    case R.id.rb14:
                        answers[curPosition] = Integer.parseInt(answerList.get(3).getId());
                        answersSelectedPos[curPosition] = 3;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_l, R.id.btn_r})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_l:
                if (curPosition > 0) {
                    curPosition--;
                    handler.sendEmptyMessage(UPDATE_QUESTION);
                }
                break;
            case R.id.btn_r:
                if (curPosition < (questionList.size() - 1)) {
                    curPosition++;
                    handler.sendEmptyMessage(UPDATE_QUESTION);
                } else {
                    showToast("开始匹配");
                    LogUtils.e(""+new Gson().toJson(answers));//  answers:（array数组问题id 的）
                    //TODO 获取问题的人
                    //接口：api/getwent  传值：user_id（用户信息）  sex（性别的） wentiids（array数组问题id 的）==answers

                }
                break;
        }
    }
}
